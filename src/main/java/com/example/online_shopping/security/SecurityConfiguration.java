package com.example.online_shopping.security;

import com.example.online_shopping.database.repository.UsersRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.example.online_shopping.util.Mappings.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserPrincipalDetailsService userPrincipalDetailsService;
    private UsersRepository usersRepository;

    public SecurityConfiguration(UserPrincipalDetailsService userPrincipalDetailsService, UsersRepository usersRepository) {
        this.userPrincipalDetailsService = userPrincipalDetailsService;
        this.usersRepository = usersRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), this.usersRepository))
                .authorizeRequests()
                /* P e r m i t   a l l */
                .antMatchers(HttpMethod.GET,
                        CATEGORY,
                        CATEGORY + ASC,
                        CATEGORY + "/*",
                        PRODUCT + "/**",
                        USER + PROJECT_EXPERT).permitAll()
                .antMatchers(HttpMethod.POST,
                        "/login",
                        USER + SIGN_UP).permitAll()
                /* A u t h e n t i c a t e d */
                .antMatchers(HttpMethod.GET,
                        USER + ME,
                        USER + NAVBAR,
                        TODOLIST,
                        TODOLIST + TRUE + "/*",
                        TODOLIST + FALSE + "/*",
                        SCHEDULE,
                        ORDER + ACCEPTED).authenticated()
                .antMatchers(HttpMethod.POST,
                        TODOLIST).authenticated()
                .antMatchers(HttpMethod.PUT,
                        "/update-password").authenticated()
                .antMatchers(HttpMethod.DELETE,
                        TODOLIST + "/*").authenticated()
                /* H a s   R o l e   U S E R */
                .antMatchers(HttpMethod.GET,
                        ORDER,
                        ORDER + QUANTITY,
                        ORDER + PLUS + "/*",
                        ORDER + MINUS + "/*",
                        COMMENT + USER,
                        ORDER + STATUS + RECEIVE + "/*").hasRole("USER")
                .antMatchers(HttpMethod.POST,
                        COMMENT + USER,
                        USER + ADD + ADDRESS,
                        USER + ADD + CONTACT,
                        USER + ADD + CARD,
                        VAUCHER + VERIFY).hasRole("USER")
                .antMatchers(HttpMethod.PUT,
                        COMMENT + USER + "/*",
                        USER + EDIT + ADDRESS + "/*",
                        USER + EDIT + CONTACT + "/*").hasRole("USER")
                .antMatchers(HttpMethod.DELETE,
                        USER + DELETE + ADDRESS + "/*",
                        USER + DELETE + CONTACT + "/*",
                        USER + DELETE + CARD).hasRole("USER")
                /* H a s   R o l e   A D M I N */
                .antMatchers(HttpMethod.GET,
                        USER,
                        COMMENT + ADMIN,
                        USER + COMMENT,
                        CART,
                        CART + RECEIVE + "/*",
                        CART + NOTRECEIVE + "/*",
                        ORDER + DELIVERY,
                        ORDER + RECEIVE,
                        ORDER + STATUS + DELIVERY + "/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,
                        PRODUCT,
                        CATEGORY,
                        COMMENT + ADMIN + "/*",
                        SCHEDULE + "/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT,
                        CATEGORY + "/*",
                        PRODUCT,
                        USER + STATUS_ACTIVE + "/*",
                        USER + STATUS_DEACTIVE + "/*",
                        SCHEDULE + "/*",
                        "/update-password-user").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,
                        CATEGORY + "/*",
                        PRODUCT,
                        SCHEDULE + "/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,
                        USER + SIGN_UP + ADMIN).hasRole("EXPERT")
                .antMatchers(HttpMethod.PUT,
                        "/update-password-admin").hasRole("EXPERT")

                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/123")
                .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    System.out.println("LOgOUT SUCCESS");
                });
    }


    @Bean
    DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userPrincipalDetailsService);

        return daoAuthenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

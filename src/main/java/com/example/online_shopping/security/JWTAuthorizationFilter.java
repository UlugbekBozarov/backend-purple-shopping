package com.example.online_shopping.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.online_shopping.database.domain.Users;
import com.example.online_shopping.database.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.online_shopping.security.JWTProperties.*;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {


    private UsersRepository usersRepository;

    @Autowired
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, UsersRepository usersRepository) {
        super(authenticationManager);
        this.usersRepository = usersRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_STRING);
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        Authentication authentication = getUsernamePasswordAuthentification(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }

    private Authentication getUsernamePasswordAuthentification(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);

        if (token != null) {
            String userName = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();

            if (userName != null) {
                Users user = usersRepository.findByUsername(userName);
                UserPrincipal userPrincipal = new UserPrincipal(user);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userName, null, userPrincipal.getAuthorities());
                return auth;
            }
            return null;
        }
        return null;
    }
}

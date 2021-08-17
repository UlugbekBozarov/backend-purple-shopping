package com.example.online_shopping.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.online_shopping.services.UserServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.example.online_shopping.security.JWTProperties.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private UserServices userServices;


    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public JWTAuthenticationFilter(UserServices userServices) {
        this.userServices = userServices;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            LoginViewModel authentication = new ObjectMapper().readValue(req.getInputStream(), LoginViewModel.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authentication.getUsername(),
                            authentication.getPassword(),
                            new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        String token = JWT.create()
                .withSubject(userPrincipal.getUsername())
                .withClaim("authorities", String.valueOf(userPrincipal.getAuthorities()))
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        res.addHeader(USER_ROLE, ROLE + userPrincipal.getRole());
    }
}

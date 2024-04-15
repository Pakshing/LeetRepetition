package com.example.leetCodeRepetition.Security;


import com.example.leetCodeRepetition.Service.CustomUserDetailsService;
import com.example.leetCodeRepetition.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Cookie;

import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");

        String email = null;
        String jwtToken = null;

        logger.info("Request Token Header: " + requestTokenHeader);
        logger.info("token: " + jwtToken);

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                logger.info("jwtToken: " + jwtToken);
                email = jwtTokenUtil.getEmailFromToken(jwtToken);
                logger.info("email: " + email);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            }
        }else{
            logger.info("Token is not valid");
            if (request.getCookies() != null) {
                logger.info("Cookies: " + Arrays.toString(request.getCookies()));
                Cookie tokenCookie = Arrays.stream(request.getCookies())
                        .filter(cookie -> "token".equals(cookie.getName()))
                        .findFirst()
                        .orElse(null);
                logger.info("tokenCookie: " + tokenCookie);
                if (tokenCookie != null) {
                    logger.info("tokenCookie: " + tokenCookie.getValue());
                    jwtToken = tokenCookie.getValue();
                    logger.info("jwtToken: from http cookie " + jwtToken);
                    email = jwtTokenUtil.getEmailFromToken(jwtToken);
                    logger.info("email: from http cookie " + email);
                }
            }
        }




        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(email);
            logger.info("userDetails: " + userDetails.toString());
            if (jwtTokenUtil.validateToken(jwtToken, email)) {
                logger.info("Token is valid");
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}
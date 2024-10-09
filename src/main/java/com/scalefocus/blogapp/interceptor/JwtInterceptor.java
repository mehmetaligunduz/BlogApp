package com.scalefocus.blogapp.interceptor;

import com.scalefocus.blogapp.handler.SessionHandler;
import com.scalefocus.blogapp.service.JwtServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.text.ParseException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtServiceImpl jwtService;

    private final SessionHandler sessionHandler;

    private static final String TOKEN_PREFIX = "Bearer ";

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws ParseException {

        final String authorization = request.getHeader("Authorization");

        if (authorization.isBlank() || Boolean.FALSE.equals(authorization.startsWith(TOKEN_PREFIX))) {

            return unauthorized(response);

        }

        final String jwt = authorization.substring(TOKEN_PREFIX.length());
        final String username = jwtService.extractUsername(jwt);
        final String userId = jwtService.extractUserId(jwt);

        if (Boolean.FALSE.equals(jwtService.validateToken(jwt, username))) {

            return unauthorized(response);

        }

        sessionHandler.setUsername(username);
        sessionHandler.setId(Long.valueOf(userId));
        
        return true;

    }

    private boolean unauthorized(HttpServletResponse response) {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;

    }

}

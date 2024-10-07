package com.scalefocus.blogapp.interceptor;

import com.scalefocus.blogapp.handler.SessionHandler;
import com.scalefocus.blogapp.service.JwtServiceImpl;
import com.scalefocus.blogapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.text.ParseException;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtServiceImpl jwtService;

    private final SessionHandler sessionHandler;

    private final UserService userService;

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

        if (Boolean.FALSE.equals(jwtService.validateToken(jwt, username))) {

            return unauthorized(response);
            
        }

        userService.findByUsername(username).ifPresent(u -> {
            sessionHandler.setUsername(u.getUsername());
            sessionHandler.setId(u.getId());
        });

        return true;

    }

    private boolean unauthorized(HttpServletResponse response) {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;

    }

}

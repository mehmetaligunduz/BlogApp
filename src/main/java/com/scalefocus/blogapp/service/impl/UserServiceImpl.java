package com.scalefocus.blogapp.service.impl;

import com.scalefocus.blogapp.entity.UserEntity;
import com.scalefocus.blogapp.model.LoginRequest;
import com.scalefocus.blogapp.model.LoginResponse;
import com.scalefocus.blogapp.model.RegisterRequest;
import com.scalefocus.blogapp.model.RegisterResponse;
import com.scalefocus.blogapp.repository.UserRepository;
import com.scalefocus.blogapp.service.JwtService;
import com.scalefocus.blogapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {


        UserEntity userEntity = new UserEntity(
                registerRequest.getUsername(),
                new BCryptPasswordEncoder().encode(registerRequest.getPassword()),
                registerRequest.getDisplayName()
        );

        UserEntity savedUser = userRepository.save(userEntity);

        log.info("User registered: {}", savedUser.getDisplayName());

        return new RegisterResponse(jwtService.generateToken(savedUser));

    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        Authentication authenticate = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsername(),
                                loginRequest.getPassword()));

        log.info("Authenticated user: {}", authenticate.getPrincipal());

        if (!authenticate.isAuthenticated()) {

            throw new UsernameNotFoundException("User not found");

        }

        final Optional<UserEntity> userEntity = userRepository
                .findByUsername(loginRequest.getUsername());

        if (userEntity.isEmpty()) {

            throw new UsernameNotFoundException("User not found");

        }

        final String token = jwtService.generateToken(userEntity.get());

        log.info("Generated token: {}", token);

        return new LoginResponse(token);
    }

}

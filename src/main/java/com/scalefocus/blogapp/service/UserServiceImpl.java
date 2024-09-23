package com.scalefocus.blogapp.service;

import com.scalefocus.blogapp.entity.UserEntity;
import com.scalefocus.blogapp.handler.AuthenticationHandler;
import com.scalefocus.blogapp.model.LoginRequest;
import com.scalefocus.blogapp.model.LoginResponse;
import com.scalefocus.blogapp.model.RegisterResponse;
import com.scalefocus.blogapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Override
    public RegisterResponse register(UserEntity userEntity) {

        UserEntity save = userRepository.save(userEntity);

        AuthenticationHandler.setUser(save);

        return new RegisterResponse(jwtService.generateToken(save));

    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        Authentication authenticate = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsername(),
                                loginRequest.getPassword()));

        if (!authenticate.isAuthenticated()) {
            throw new UsernameNotFoundException("User not found");
        }

        final Optional<UserEntity> userEntity = userRepository
                .findByUsername(loginRequest.getUsername());

        if (userEntity.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        final String token = jwtService.generateToken(userEntity.get());

        return new LoginResponse(token);
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


}

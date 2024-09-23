package com.scalefocus.blogapp.service;

import com.scalefocus.blogapp.entity.UserEntity;
import com.scalefocus.blogapp.handler.AuthenticationHandler;
import com.scalefocus.blogapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final Optional<UserEntity> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found.");
        }

        final UserEntity userEntity = user.get();

        AuthenticationHandler.setUser(userEntity);

        return User
                .withUsername(userEntity.getUsername())
                .password(userEntity.getPassword())
                .build();

    }
}

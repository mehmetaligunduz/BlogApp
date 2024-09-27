package com.scalefocus.blogapp.service;

import com.scalefocus.blogapp.entity.UserEntity;
import com.scalefocus.blogapp.model.LoginRequest;
import com.scalefocus.blogapp.model.LoginResponse;
import com.scalefocus.blogapp.model.RegisterResponse;

import java.util.Optional;

public interface UserService {

    RegisterResponse register(UserEntity userEntity);

    LoginResponse login(LoginRequest loginRequest);

    Optional<UserEntity> findByUsername(String username);

}

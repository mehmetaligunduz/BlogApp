package com.scalefocus.blogapp.service;

import com.scalefocus.blogapp.entity.UserEntity;
import com.scalefocus.blogapp.model.LoginRequest;
import com.scalefocus.blogapp.model.LoginResponse;
import com.scalefocus.blogapp.model.RegisterResponse;

public interface UserService {

    RegisterResponse register(UserEntity userEntity);

    LoginResponse login(LoginRequest loginRequest);

}

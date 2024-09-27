package com.scalefocus.blogapp.service;

import com.scalefocus.blogapp.entity.UserEntity;

public interface JwtService {

    String generateToken(UserEntity user);
    
}

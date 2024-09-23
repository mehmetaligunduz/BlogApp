package com.scalefocus.blogapp.handler;

import com.scalefocus.blogapp.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AuthenticationHandler {

    @Setter
    @Getter
    private static UserEntity user;

}

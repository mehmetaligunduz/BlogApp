package com.scalefocus.blogapp.handler;

import com.scalefocus.blogapp.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.UtilityClass;

@UtilityClass
@NoArgsConstructor
public class AuthenticationHandler {

    @Setter
    @Getter
    private static UserEntity user;

}

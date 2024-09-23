package com.scalefocus.blogapp.model;

import com.scalefocus.blogapp.model.base.AuthenticationBaseResponseModel;

public class LoginResponse extends AuthenticationBaseResponseModel {

    public LoginResponse(String token) {

        super(token);

    }

}

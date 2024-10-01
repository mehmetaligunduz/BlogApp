package com.scalefocus.blogapp.model;

import com.scalefocus.blogapp.model.base.AuthenticationBaseRequestModel;

public class LoginRequest extends AuthenticationBaseRequestModel {

    public LoginRequest(String username, String password) {

        super(username, password);

    }
}

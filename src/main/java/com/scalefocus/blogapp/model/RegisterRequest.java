package com.scalefocus.blogapp.model;

import com.scalefocus.blogapp.model.base.AuthenticationBaseRequestModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest extends AuthenticationBaseRequestModel {

    @NotNull
    private String displayName;
    
}

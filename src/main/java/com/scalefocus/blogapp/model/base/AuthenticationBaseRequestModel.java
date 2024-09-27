package com.scalefocus.blogapp.model.base;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationBaseRequestModel {

    @NotNull
    private String username;

    @NotNull
    private String password;

}

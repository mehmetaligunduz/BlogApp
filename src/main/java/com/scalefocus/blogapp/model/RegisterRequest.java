package com.scalefocus.blogapp.model;

import com.scalefocus.blogapp.entity.UserEntity;
import com.scalefocus.blogapp.model.base.AuthenticationBaseRequestModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest extends AuthenticationBaseRequestModel {

    @NotNull
    private String displayName;

    public UserEntity toEntity() {
        return new UserEntity(
                super.getUsername(),
                new BCryptPasswordEncoder().encode(super.getPassword()),
                displayName);
    }

}

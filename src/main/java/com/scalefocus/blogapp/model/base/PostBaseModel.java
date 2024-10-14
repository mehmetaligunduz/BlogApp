package com.scalefocus.blogapp.model.base;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostBaseModel {

    @NotNull
    private String title;

    @NotNull
    private String text;

}

package com.scalefocus.blogapp.model;

import com.scalefocus.blogapp.entity.PostEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePostRequest {

    @NotNull
    private String title;

    @NotNull
    private String text;

    public PostEntity toEntity(){
        return new PostEntity(title, text);
    }

}

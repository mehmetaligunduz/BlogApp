package com.scalefocus.blogapp.model;

import com.scalefocus.blogapp.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdatePostRequest {

    private String title;

    private String text;

    public PostEntity toEntity() {
        return new PostEntity(title, text);
    }

}

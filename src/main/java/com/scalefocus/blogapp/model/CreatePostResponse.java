package com.scalefocus.blogapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostResponse {

    private Long id;

    public CreatePostResponse toCreatePostResponse(Long id) {
        this.setId(id);
        return this;
    }

}

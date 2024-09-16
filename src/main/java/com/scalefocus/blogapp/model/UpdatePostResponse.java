package com.scalefocus.blogapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdatePostResponse {

    private String title;

    private String text;

}

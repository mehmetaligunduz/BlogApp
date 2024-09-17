package com.scalefocus.blogapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class AddTagResponse {

    private String title;

    private String text;

    private Set<String> tags;

}

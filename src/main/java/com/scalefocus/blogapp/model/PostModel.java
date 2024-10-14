package com.scalefocus.blogapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostModel {

    private Long id;

    private String title;

    private String text;

    private boolean isDeleted;

    private Set<TagModel> tags;

    private int version;

    public PostModel(String title, String text) {
        this.title = title;
        this.text = text;
    }
}

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

    private boolean isDeleted = Boolean.FALSE;

    private Set<TagModel> tags;

    private int version;

}

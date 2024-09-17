package com.scalefocus.blogapp.model.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagBaseModel {

    private String title;

    private String text;

    private Set<String> tags;

}

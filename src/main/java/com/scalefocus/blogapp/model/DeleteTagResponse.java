package com.scalefocus.blogapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteTagResponse {

    private String title;

    private String text;

    private Set<String> tags;

}

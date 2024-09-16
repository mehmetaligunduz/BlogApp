package com.scalefocus.blogapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetPostsResponse {

    private String title;

    private String summaryText;

}

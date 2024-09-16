package com.scalefocus.blogapp.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostSummaryProjection {

    private String title;

    private String summaryText;

}

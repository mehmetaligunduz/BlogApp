package com.scalefocus.blogapp.model;

import com.scalefocus.blogapp.model.base.PostBaseModel;

public class PostWithSummaryTextResponse extends PostBaseModel {

    public PostWithSummaryTextResponse(String title, String text) {
        super(
                title,
                text
                        .substring(0, text.length() / 2)
                        .trim()
                        .concat("...")
        );
    }
}

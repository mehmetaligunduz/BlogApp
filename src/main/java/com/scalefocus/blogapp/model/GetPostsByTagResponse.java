package com.scalefocus.blogapp.model;

import com.scalefocus.blogapp.model.base.TagBaseModel;

import java.util.Set;

public class GetPostsByTagResponse extends TagBaseModel {

    public GetPostsByTagResponse(String title, String text, Set<TagsResponse> tags) {

        super(title, text, tags);

    }
}

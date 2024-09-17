package com.scalefocus.blogapp.model;

import com.scalefocus.blogapp.model.base.TagBaseModel;

import java.util.Set;

public class AddTagResponse extends TagBaseModel {

    public AddTagResponse(String title, String text, Set<String> tags) {
        super(title, text, tags);
    }
}

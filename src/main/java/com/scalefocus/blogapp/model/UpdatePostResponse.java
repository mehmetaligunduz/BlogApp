package com.scalefocus.blogapp.model;

import com.scalefocus.blogapp.model.base.PostBaseModel;

public class UpdatePostResponse extends PostBaseModel {

    public UpdatePostResponse(String title, String text) {
        super(title, text);
    }
}

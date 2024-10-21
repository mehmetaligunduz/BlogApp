package com.scalefocus.blogapp.model;

import com.scalefocus.blogapp.model.base.PostBaseModel;


public class UpdatePostRequest extends PostBaseModel {
    public UpdatePostRequest(String title, String text) {
        super(title, text);
    }
}

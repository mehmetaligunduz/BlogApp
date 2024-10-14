package com.scalefocus.blogapp.model;

import com.scalefocus.blogapp.model.base.TagBaseModel;

import java.util.Set;

public class DeleteTagResponse extends TagBaseModel {

    public DeleteTagResponse(String title, String text, Set<TagModel> tags) {

        super(title, text, tags);

    }

}

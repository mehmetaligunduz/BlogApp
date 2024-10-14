package com.scalefocus.blogapp.service;

import com.scalefocus.blogapp.model.AddTagRequest;
import com.scalefocus.blogapp.model.AddTagResponse;
import com.scalefocus.blogapp.model.DeleteTagRequest;
import com.scalefocus.blogapp.model.DeleteTagResponse;

import java.util.Optional;

public interface TagService {

    Optional<AddTagResponse> addTag(AddTagRequest addTagRequest, Long postId);

    Optional<DeleteTagResponse> deleteTag(DeleteTagRequest deleteTagRequest, Long postId);

}

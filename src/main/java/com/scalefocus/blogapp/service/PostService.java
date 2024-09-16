package com.scalefocus.blogapp.service;

import com.scalefocus.blogapp.entity.PostEntity;
import com.scalefocus.blogapp.model.CreatePostResponse;

public interface PostService {

    CreatePostResponse create(PostEntity postEntity);

}

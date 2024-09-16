package com.scalefocus.blogapp.service;

import com.scalefocus.blogapp.entity.PostEntity;
import com.scalefocus.blogapp.model.CreatePostResponse;
import com.scalefocus.blogapp.model.GetPostsResponse;

import java.util.List;

public interface PostService {

    CreatePostResponse create(PostEntity postEntity);

    List<GetPostsResponse> getAll();

}

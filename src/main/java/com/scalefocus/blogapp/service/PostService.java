package com.scalefocus.blogapp.service;

import com.scalefocus.blogapp.entity.PostEntity;
import com.scalefocus.blogapp.model.GetPostsByTagResponse;
import com.scalefocus.blogapp.model.GetPostsResponse;
import com.scalefocus.blogapp.model.UpdatePostResponse;

import java.util.List;
import java.util.Optional;

public interface PostService {

    List<GetPostsResponse> getAll();

    Optional<PostEntity> findById(Long id);

    Optional<PostEntity> create(PostEntity postEntity);

    List<GetPostsByTagResponse> getAllByTag(String tag);

    Optional<UpdatePostResponse> update(PostEntity postEntity, Long id);
}

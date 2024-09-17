package com.scalefocus.blogapp.service;

import com.scalefocus.blogapp.entity.PostEntity;
import com.scalefocus.blogapp.model.CreatePostResponse;
import com.scalefocus.blogapp.model.GetPostsByTagResponse;
import com.scalefocus.blogapp.model.GetPostsResponse;
import com.scalefocus.blogapp.model.UpdatePostResponse;

import java.util.List;
import java.util.Optional;

public interface PostService {

    CreatePostResponse create(PostEntity postEntity);

    List<GetPostsResponse> getAll();

    UpdatePostResponse update(PostEntity postEntity, Long id);

    PostEntity save(PostEntity postEntity);

    Optional<PostEntity> findById(Long id);

    List<GetPostsByTagResponse> findAllByTag(String tag);

    List<PostEntity> saveAll(List<PostEntity> postEntities);

}

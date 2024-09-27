package com.scalefocus.blogapp.service;

import com.scalefocus.blogapp.entity.PostEntity;
import com.scalefocus.blogapp.model.GetPostsByTagResponse;
import com.scalefocus.blogapp.model.PostWithSummaryTextResponse;
import com.scalefocus.blogapp.model.UpdatePostResponse;

import java.util.List;
import java.util.Optional;

public interface PostService {

    List<PostWithSummaryTextResponse> getAll();

    Optional<PostEntity> findById(Long id);

    Optional<PostEntity> create(PostEntity postEntity);

    List<GetPostsByTagResponse> getAllByTag(String tag);

    Optional<UpdatePostResponse> update(PostEntity postEntity, Long id);

    List<PostWithSummaryTextResponse> getAllByUser();

    void deleteById(Long id);

    boolean isOwner(Long postId);
}


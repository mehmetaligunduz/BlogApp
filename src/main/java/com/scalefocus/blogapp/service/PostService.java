package com.scalefocus.blogapp.service;

import com.scalefocus.blogapp.model.*;

import java.util.List;
import java.util.Optional;

public interface PostService {

    List<PostWithSummaryTextResponse> getAll();

    Optional<PostModel> findById(Long id);

    Optional<PostModel> create(PostModel postModel);

    List<GetPostsByTagResponse> getAllByTag(String tag);

    Optional<UpdatePostResponse> update(UpdatePostRequest updatePostRequest, Long id);

    List<PostWithSummaryTextResponse> getAllByUser();

    void deleteById(Long id);

    boolean isOwner(Long postId);
}


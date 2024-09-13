package com.scalefocus.blogapp.service;

import com.scalefocus.blogapp.entity.PostEntity;
import com.scalefocus.blogapp.model.CreatePostResponse;
import com.scalefocus.blogapp.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public CreatePostResponse create(PostEntity postEntity) {
        final PostEntity save = postRepository.save(postEntity);
        return new CreatePostResponse(save.getId());
    }
}

package com.scalefocus.blogapp.service.impl;

import com.scalefocus.blogapp.entity.PostEntity;
import com.scalefocus.blogapp.entity.UserEntity;
import com.scalefocus.blogapp.handler.SessionHandler;
import com.scalefocus.blogapp.mapper.PostMapper;
import com.scalefocus.blogapp.model.*;
import com.scalefocus.blogapp.repository.PostRepository;
import com.scalefocus.blogapp.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final SessionHandler sessionHandler;

    @Override
    public Optional<PostModel> create(PostModel postModel) {

        PostEntity postEntity = PostMapper.INSTANCE.postModelToEntity(postModel);

        postEntity.setUser(new UserEntity(sessionHandler.getId()));

        Optional<PostEntity> savedPost = Optional
                .of(postRepository
                        .save(postEntity));

        log.info("Post created: {}", savedPost.get().getId());

        return Optional.ofNullable(PostMapper.INSTANCE.postEntityToModel(savedPost.get()));
    }

    @Override
    public List<PostWithSummaryTextResponse> getAll() {

        final List<PostEntity> allPosts = postRepository.findAll();

        return PostMapper
                .INSTANCE
                .allPostsEntityToModel(allPosts);

    }

    @Override
    public Optional<UpdatePostResponse> update(UpdatePostRequest updatePostRequest, Long id) {

        return postRepository.findById(id)
                .map(post -> {
                    post.setTitle(updatePostRequest.getTitle());
                    post.setText(updatePostRequest.getText());

                    final PostEntity updatedPost = postRepository.save(post);

                    return PostMapper
                            .INSTANCE
                            .updatePostEntityToModel(updatedPost);
                });

    }

    @Override
    public List<PostWithSummaryTextResponse> getAllByUser() {

        final List<PostEntity> allPostsByUser = postRepository
                .findAllByUser_Username(sessionHandler.getUsername());

        return PostMapper
                .INSTANCE
                .allPostEntityToModel(allPostsByUser);

    }

    @Override
    public void deleteById(Long id) {

        Optional<PostEntity> postEntity = postRepository.findById(id);


        if (postEntity.isEmpty()) {
            return;
        }

        postEntity.get().setDeleted(true);

        postRepository.save(postEntity.get());

        log.info("Post deleted: {}", postEntity.get().getId());

    }

    @Override
    public List<GetPostsByTagResponse> getAllByTag(String tag) {

        final List<PostEntity> allByTag = postRepository.findAllByTags_Tag(tag);

        return PostMapper
                .INSTANCE
                .postEntityToModelWithTags(allByTag);

    }

    @Override
    public Optional<PostModel> findById(Long id) {

        final Optional<PostEntity> post = postRepository.findById(id);

        return post.map(PostMapper
                .INSTANCE::postEntityToModel);

    }

    @Override
    public boolean isOwner(Long postId) {

        Optional<PostEntity> post = postRepository.findById(postId);

        return post.isPresent()
                && post
                .get()
                .getUser()
                .getId()
                .equals(sessionHandler
                        .getId());

    }

}

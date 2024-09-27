package com.scalefocus.blogapp.service;

import com.scalefocus.blogapp.entity.PostEntity;
import com.scalefocus.blogapp.entity.TagEntity;
import com.scalefocus.blogapp.entity.UserEntity;
import com.scalefocus.blogapp.handler.AuthenticationHandler;
import com.scalefocus.blogapp.mapper.PostMapper;
import com.scalefocus.blogapp.model.GetPostsByTagResponse;
import com.scalefocus.blogapp.model.PostWithSummaryTextResponse;
import com.scalefocus.blogapp.model.UpdatePostResponse;
import com.scalefocus.blogapp.repository.PostRepository;
import com.scalefocus.blogapp.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final TagRepository tagRepository;

    private final UserService userService;

    @Override
    public Optional<PostEntity> create(PostEntity postEntity) {

        postEntity.setUser(AuthenticationHandler.getUser());

        Optional<PostEntity> savedPost = Optional
                .of(postRepository
                        .save(postEntity));

        log.info("Post created: {}", savedPost.get().getId());

        return savedPost;
    }

    @Override
    public List<PostWithSummaryTextResponse> getAll() {

        final List<PostEntity> allPosts = postRepository.findAll();

        return PostMapper.INSTANCE.allPostsEntityToModel(allPosts);
    }

    @Override
    public Optional<UpdatePostResponse> update(PostEntity postEntity, Long id) {


        return postRepository.findById(id)
                .map(post -> {
                    post.setTitle(postEntity.getTitle());
                    post.setText(postEntity.getText());

                    final PostEntity updatedPost = postRepository.save(post);

                    return PostMapper.INSTANCE.updatePostEntityToModel(updatedPost);
                });

    }

    @Override
    public List<PostWithSummaryTextResponse> getAllByUser() {

        final String username = SecurityContextHolder.getContext().getAuthentication().getName();

        final Optional<UserEntity> user = userService.findByUsername(username);

        if (user.isEmpty()) {
            return List.of();
        }

        final List<PostEntity> allPostsByUser = postRepository.findAllByUser(user.get());

        return PostMapper.INSTANCE.allPostEntityToModel(allPostsByUser);

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

        Optional<TagEntity> tagEntity = tagRepository.findByTag(tag);

        if (tagEntity.isEmpty()) {
            return List.of();
        }

        final List<PostEntity> allByTag = postRepository.findAllByTags(Set.of(tagEntity.get()));

        return PostMapper.INSTANCE.postEntityToModelWithTags(allByTag);

    }

    @Override
    public Optional<PostEntity> findById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public boolean isOwner(Long postId) {

        Optional<PostEntity> byId = postRepository.findById(postId);

        return byId.isPresent()
                && byId
                .get()
                .getUser()
                .getId()
                .equals(AuthenticationHandler
                        .getUser()
                        .getId());

    }

}

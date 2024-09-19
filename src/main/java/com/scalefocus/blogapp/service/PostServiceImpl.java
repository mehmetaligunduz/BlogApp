package com.scalefocus.blogapp.service;

import com.scalefocus.blogapp.entity.PostEntity;
import com.scalefocus.blogapp.entity.TagEntity;
import com.scalefocus.blogapp.model.GetPostsByTagResponse;
import com.scalefocus.blogapp.model.GetPostsResponse;
import com.scalefocus.blogapp.model.UpdatePostResponse;
import com.scalefocus.blogapp.repository.PostRepository;
import com.scalefocus.blogapp.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    public static final String ELLIPSIS = "...";

    private final PostRepository postRepository;

    private final TagRepository tagRepository;

    @Override
    public Optional<PostEntity> create(PostEntity postEntity) {

        return Optional
                .of(postRepository
                        .save(postEntity));
    }

    @Override
    public List<GetPostsResponse> getAll() {

        final List<PostEntity> allPosts = postRepository.findAll();

        return allPosts.stream()
                .map(post -> new GetPostsResponse(
                        post.getTitle(),
                        post.getText()
                                .substring(0, post.getText().length() / 2)
                                .trim()
                                .concat(ELLIPSIS)))
                .toList();
    }

    @Override
    public Optional<UpdatePostResponse> update(PostEntity postEntity, Long id) {


        return postRepository.findById(id)
                .map(post -> {
                    post.setTitle(postEntity.getTitle());
                    post.setText(postEntity.getText());

                    final PostEntity updatedPost = postRepository.save(post);

                    return new UpdatePostResponse(updatedPost.getTitle(), updatedPost.getText());

                });
    }

    @Override
    public List<GetPostsByTagResponse> getAllByTag(String tag) {

        Optional<TagEntity> tagEntity = tagRepository.findByTag(tag);

        if (tagEntity.isEmpty()) {
            return List.of();
        }

        List<PostEntity> allByTag = postRepository.findAllByTags(Set.of(tagEntity.get()));

        return allByTag.stream().map(post ->
                new GetPostsByTagResponse(
                        post.getTitle(),
                        post.getText()
                                .substring(0, post.getText().length() / 2)
                                .trim()
                                .concat(ELLIPSIS),
                        post.getTags()
                                .stream()
                                .map(TagEntity::getTag)
                                .collect(Collectors.toSet())
                )).toList();

    }

    @Override
    public Optional<PostEntity> findById(Long id) {
        return postRepository.findById(id);
    }


}

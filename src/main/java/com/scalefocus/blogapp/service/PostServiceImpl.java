package com.scalefocus.blogapp.service;

import com.scalefocus.blogapp.entity.PostEntity;
import com.scalefocus.blogapp.model.CreatePostResponse;
import com.scalefocus.blogapp.model.GetPostsResponse;
import com.scalefocus.blogapp.model.UpdatePostResponse;
import com.scalefocus.blogapp.projection.PostSummaryProjection;
import com.scalefocus.blogapp.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public CreatePostResponse create(PostEntity postEntity) {
        final PostEntity save = postRepository.save(postEntity);
        return new CreatePostResponse(save.getId());
    }

    @Override
    public List<GetPostsResponse> getAll() {
        final List<PostSummaryProjection> allPosts = postRepository.findAllWithSummaryText();
        return allPosts.stream()
                .map(post -> new GetPostsResponse(
                        post.getTitle(),
                        post.getSummaryText())).collect(Collectors.toList());
    }

    @Override
    public UpdatePostResponse update(PostEntity postEntity, Long id) {
        return postRepository.findById(id)
                .map(post -> {
                    post.setTitle(postEntity.getTitle());
                    post.setText(postEntity.getText());

                    final PostEntity updatedPost = postRepository.save(post);

                    return new UpdatePostResponse(updatedPost.getTitle(), updatedPost.getText());
                    
                }).orElse(null);
    }

}

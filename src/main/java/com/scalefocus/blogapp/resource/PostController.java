package com.scalefocus.blogapp.resource;

import com.scalefocus.blogapp.model.*;
import com.scalefocus.blogapp.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Optional<CreatePostResponse>> createPost(@Valid @RequestBody CreatePostRequest createPostRequest) {
        return ResponseEntity
                .ok(postService
                        .create(createPostRequest.toEntity()));
    }

    @GetMapping
    public Optional<List<GetPostsResponse>> getPosts() {
        return postService.getAll();
    }

    @PutMapping("/{postId}")
    public ResponseEntity<UpdatePostResponse> updatePost(@RequestBody UpdatePostRequest updatePostRequest, @PathVariable Long postId) {
        return ResponseEntity
                .ok(postService
                        .update(updatePostRequest.toEntity(), postId));
    }

    @GetMapping("/{tag}")
    public List<GetPostsByTagResponse> getPostsByTag(@PathVariable String tag) {

        return postService.findAllByTag(tag);

    }

}


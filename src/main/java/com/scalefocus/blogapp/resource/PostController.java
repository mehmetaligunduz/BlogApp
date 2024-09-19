package com.scalefocus.blogapp.resource;

import com.scalefocus.blogapp.model.*;
import com.scalefocus.blogapp.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<CreatePostResponse> createPost(@Valid @RequestBody CreatePostRequest createPostRequest) {
        return postService
                .create(createPostRequest.toEntity())
                .map(pe -> ResponseEntity
                        .ok(new CreatePostResponse(pe.getId())))
                .orElse(ResponseEntity
                        .badRequest()
                        .build());

    }

    @GetMapping
    public List<GetPostsResponse> getPosts() {
        return postService.getAll();
    }

    @PutMapping("/{postId}")
    public ResponseEntity<UpdatePostResponse> updatePost(@RequestBody UpdatePostRequest updatePostRequest, @PathVariable Long postId) {

        return postService
                .update(updatePostRequest.toEntity(), postId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/{tag}")
    public List<GetPostsByTagResponse> getPostsByTag(@PathVariable String tag) {

        return postService.getAllByTag(tag);

    }

}


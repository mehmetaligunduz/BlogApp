package com.scalefocus.blogapp.resource;

import com.scalefocus.blogapp.model.*;
import com.scalefocus.blogapp.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<CreatePostResponse> createPost(@Valid @RequestBody CreatePostRequest createPostRequest) {
        final CreatePostResponse createPostResponse = postService.create(createPostRequest.toEntity());
        return ResponseEntity.ok(createPostResponse);
    }

    @GetMapping
    public List<GetPostsResponse> getAllPosts() {
        return postService.getAll();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UpdatePostResponse> updatePost(@RequestBody UpdatePostRequest updatePostRequest, @PathVariable Long id) {
        return ResponseEntity.ok(postService.update(updatePostRequest.toEntity(), id));
    }

}


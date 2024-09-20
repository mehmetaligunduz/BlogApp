package com.scalefocus.blogapp.resource;

import com.scalefocus.blogapp.model.*;
import com.scalefocus.blogapp.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Create a new blog post",
            description = "This endpoint allows you to create a new blog post. You must provide the title, text.")
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
    @Operation(summary = "Retrieve all blog posts",
            description = "Returns a list of all blog posts, including summaries of each post. Optional query parameters allow filtering by category, tag")
    public List<GetPostsResponse> getPosts() {
        return postService.getAll();
    }

    @PutMapping("/{postId}")
    @Operation(summary = "Update an existing blog post",
            description = " Allows you to update an existing blog post by its ID. You can modify fields like the title, text.")
    public ResponseEntity<UpdatePostResponse> updatePost(@RequestBody UpdatePostRequest updatePostRequest, @PathVariable Long postId) {

        return postService
                .update(updatePostRequest.toEntity(), postId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/{tag}")
    @Operation(summary = "Retrieve all blog posts by tag",
            description = "Returns a list of all blog posts by tag, including summaries of each post.")
    public List<GetPostsByTagResponse> getPostsByTag(@PathVariable String tag) {

        return postService.getAllByTag(tag);

    }

}


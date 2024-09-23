package com.scalefocus.blogapp.resource;

import com.scalefocus.blogapp.model.AddTagRequest;
import com.scalefocus.blogapp.model.AddTagResponse;
import com.scalefocus.blogapp.model.DeleteTagRequest;
import com.scalefocus.blogapp.model.DeleteTagResponse;
import com.scalefocus.blogapp.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/tag-management")
public class TagController {

    private final TagService tagService;

    @PostMapping("/posts/{postId}")
    @Operation(
            summary = "Add tag(s) to a post",
            description = "This endpoint allows you to add new tag(s) to a specific post by providing the post ID and tag details. The tag details should be included in the request body, and the post ID is passed as a path variable. Returns a success response if the tag is added, or a bad request if the operation fails."
    )
    public ResponseEntity<AddTagResponse> addTagToPost(@RequestBody AddTagRequest addTagRequest,
                                                       @PathVariable Long postId) {

        return tagService.addTag(addTagRequest.toTagEntity(), postId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity
                        .badRequest()
                        .build());
    }

    @DeleteMapping("/posts/{postId}")
    @Operation(
            summary = "Remove a tag from a post",
            description = "This endpoint allows you to remove a tag from a specific post by providing the post ID and tag details. The tag details should be included in the request body, and the post ID is passed as a path variable. Returns a success response if the tag is removed, or a bad request if the operation fails."
    )
    public ResponseEntity<DeleteTagResponse> deleteTagFromPost(@RequestBody @Valid DeleteTagRequest deleteTagRequest,
                                                               @PathVariable Long postId) {

        return tagService.deleteTag(deleteTagRequest.toTagEntity(), postId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity
                        .badRequest()
                        .build());
    }


}

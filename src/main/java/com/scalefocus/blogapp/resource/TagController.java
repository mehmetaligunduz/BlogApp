package com.scalefocus.blogapp.resource;

import com.scalefocus.blogapp.model.AddTagRequest;
import com.scalefocus.blogapp.model.AddTagResponse;
import com.scalefocus.blogapp.model.DeleteTagRequest;
import com.scalefocus.blogapp.model.DeleteTagResponse;
import com.scalefocus.blogapp.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/tag-management")
public class TagController {

    private final TagService tagService;

    @PostMapping("/add-tag/posts/{postId}")
    public ResponseEntity<AddTagResponse> addTagToPost(@RequestBody AddTagRequest addTagRequest,
                                                       @PathVariable Long postId) {

        return tagService.addTag(addTagRequest.toTagEntity(), postId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity
                        .badRequest()
                        .build());
    }

    @DeleteMapping("delete-tag/posts/{postId}")
    public ResponseEntity<DeleteTagResponse> deleteTagFromPost(@RequestBody @Valid DeleteTagRequest deleteTagRequest,
                                                               @PathVariable Long postId) {

        return tagService.deleteTag(deleteTagRequest.toTagEntity(), postId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity
                        .badRequest()
                        .build());
    }


}

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
@RequestMapping(value = "/tag")
public class TagController {

    private final TagService tagService;

    @PostMapping("/{postId}")
    public ResponseEntity<AddTagResponse> addTagToPost(@RequestBody AddTagRequest addTagRequest, @PathVariable Long postId) {

        return ResponseEntity.ok(tagService.addTag(addTagRequest.toTagEntity(), postId));

    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<DeleteTagResponse> deleteTagFromPost(@RequestBody @Valid DeleteTagRequest deleteTagRequest, @PathVariable Long postId) {

        return ResponseEntity.ok(tagService.deleteTag(deleteTagRequest.toTagEntity(), postId));

    }


}

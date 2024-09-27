package com.scalefocus.blogapp.service;

import com.scalefocus.blogapp.entity.TagEntity;
import com.scalefocus.blogapp.model.AddTagResponse;
import com.scalefocus.blogapp.model.DeleteTagResponse;

import java.util.Optional;
import java.util.Set;

public interface TagService {

    Optional<AddTagResponse> addTag(Set<TagEntity> tags, Long postId);

    Optional<DeleteTagResponse> deleteTag(TagEntity tagEntity, Long postId);

}

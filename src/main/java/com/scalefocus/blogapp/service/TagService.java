package com.scalefocus.blogapp.service;

import com.scalefocus.blogapp.entity.TagEntity;
import com.scalefocus.blogapp.model.AddTagResponse;
import com.scalefocus.blogapp.model.DeleteTagResponse;

import java.util.Set;

public interface TagService {

    AddTagResponse addTag(Set<TagEntity> tags, Long postId);

    DeleteTagResponse deleteTag(TagEntity tagEntity, Long postId);
    
}

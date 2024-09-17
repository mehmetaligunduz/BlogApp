package com.scalefocus.blogapp.model;

import com.scalefocus.blogapp.entity.TagEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class AddTagRequest {

    List<String> tags;

    public Set<TagEntity> toTagEntity() {

        return tags
                .stream()
                .map(TagEntity::new)
                .collect(Collectors.toSet());

    }

}

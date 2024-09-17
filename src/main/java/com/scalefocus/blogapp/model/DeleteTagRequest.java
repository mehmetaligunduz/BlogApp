package com.scalefocus.blogapp.model;

import com.scalefocus.blogapp.entity.TagEntity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteTagRequest {

    @NotNull
    private String tag;

    public TagEntity toTagEntity() {
        return new TagEntity(tag);
    }

}

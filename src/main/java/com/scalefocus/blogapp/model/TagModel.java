package com.scalefocus.blogapp.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"tag"})
public class TagModel {

    private Long id;

    private String tag;

    public TagModel(String tag) {
        this.tag = tag;
    }

}

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

    private int version;

    public TagModel(String tag) {
        this.tag = tag;
        this.version = 0;
    }

    public TagModel(Long id, String tag) {
        this.tag = tag;
        this.version = 0;
    }

}


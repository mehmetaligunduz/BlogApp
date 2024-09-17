package com.scalefocus.blogapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "posts")
public class PostEntity extends BaseEntity {
    
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String text;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<TagEntity> tags;


    public PostEntity(String title, String text) {
        this.title = title;
        this.text = text;
    }

}

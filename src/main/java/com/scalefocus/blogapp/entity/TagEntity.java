package com.scalefocus.blogapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "tags", uniqueConstraints = @UniqueConstraint(columnNames = {"tag"}))
public class TagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String tag;

    @JsonIgnore
    @ManyToMany(mappedBy = "tags")
    private Set<PostEntity> posts;

    public TagEntity(String tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagEntity tagEntity = (TagEntity) o;
        return Objects.equals(tag, tagEntity.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(tag);
    }
}

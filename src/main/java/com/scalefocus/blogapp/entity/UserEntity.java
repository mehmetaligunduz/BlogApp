package com.scalefocus.blogapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = {"username"}))
public class UserEntity extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String displayName;

    @OneToMany(mappedBy = "user")
    private List<PostEntity> posts;

    public UserEntity(String username, String password, String displayName) {

        this.username = username;

        this.password = password;

        this.displayName = displayName;

    }

    public UserEntity(Long id) {
        super(id);
    }

}

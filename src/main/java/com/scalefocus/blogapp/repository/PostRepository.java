package com.scalefocus.blogapp.repository;

import com.scalefocus.blogapp.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    List<PostEntity> findAllByTags_Tag(String tag);

    List<PostEntity> findAllByUser_Username(String username);

}

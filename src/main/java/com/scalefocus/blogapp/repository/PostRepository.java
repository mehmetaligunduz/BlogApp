package com.scalefocus.blogapp.repository;

import com.scalefocus.blogapp.entity.PostEntity;
import com.scalefocus.blogapp.projection.PostSummaryProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    @Query("select new com.scalefocus.blogapp.projection.PostSummaryProjection(pe.title, concat(substring(pe.text, 1, 100), '...')) from PostEntity pe")
    List<PostSummaryProjection> findAllWithSummaryText();

    @Query("select pe from PostEntity pe join pe.tags t where t.tag = :tag")
    List<PostEntity> findAllByTag(@Param("tag") String tag);


}

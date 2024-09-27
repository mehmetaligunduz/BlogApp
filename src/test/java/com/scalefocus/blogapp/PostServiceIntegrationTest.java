package com.scalefocus.blogapp;

import com.scalefocus.blogapp.entity.PostEntity;
import com.scalefocus.blogapp.entity.TagEntity;
import com.scalefocus.blogapp.repository.PostRepository;
import com.scalefocus.blogapp.service.PostService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class PostServiceIntegrationTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Test
    void testSavePost() {

        // ARRANGE
        PostEntity postEntity =
                new PostEntity(
                        "Test Post",
                        "Test Text",
                        0);

        // ACT
        PostEntity savedPost = postRepository.save(postEntity);

        // ASSERT
        assertNotNull(savedPost.getId());
        assertEquals("Test Post", savedPost.getTitle());

        // OPTIONAL
        postRepository.findById(savedPost.getId()).ifPresent(post -> {
            assertEquals("Test Text", post.getText());
        });

    }

    @Test
    void testGetAllPosts() {

        // ARRANGE
        PostEntity postEntity =
                new PostEntity(
                        "Test Post",
                        "Test Text",
                        0);

        PostEntity postEntity1 =
                new PostEntity(
                        "Test Post1",
                        "Test Text1",
                        0);

        // ACT
        postService.create(postEntity);
        postService.create(postEntity1);

        List<PostEntity> allPosts = postRepository.findAll();

        // ASSERT
        assertNotNull(allPosts);
        assertEquals(12, allPosts.size()); //I have 10 initial post so we expected 12
    }

    @Test
    void testUpdatePost() {

        // ARRANGE
        PostEntity postEntity =
                new PostEntity(
                        "Test Post",
                        "Test Text",
                        0);
        postEntity.setId(11L);

        // ACT
        Optional<PostEntity> createdPost = postService.create(postEntity);

        if (createdPost.isEmpty()) {
            return;
        }

        // ACT
        postEntity.setTitle("Updated Test Post");
        postService.update(postEntity, createdPost.get().getId());

        // ASSERT
        assertNotNull(createdPost.get().getId());
        postRepository.findById(postEntity.getId()).ifPresent(post -> {
            assertEquals(11L, post.getId());
            assertEquals("Updated Test Post", post.getTitle());
        });

    }

    @Test
    void testFindAllPostsByTag() {

        // ARRANGE
        String tag = "IT";
        TagEntity tagEntity = new TagEntity(tag);

        PostEntity postEntity =
                new PostEntity(
                        "Test Post",
                        "Test Text",
                        0);

        postEntity.setTags(Set.of(tagEntity));

        // ACT

        postService.create(postEntity);

        List<PostEntity> allPostsByTag = postRepository.findAllByTags(Set.of(tagEntity));

        // ASSERT
        assertNotNull(allPostsByTag);
        assertEquals(1, allPostsByTag.size());

    }

    @Test
    void testFindPostById() {

        // ARRANGE
        PostEntity postEntity =
                new PostEntity(
                        "Test Post",
                        "Test Text",
                        0);
        postEntity.setId(1L);

        // ACT
        postService.create(postEntity);

        postService.findById(postEntity.getId());

        // ASSERT
        postRepository.findById(postEntity.getId()).ifPresent(post -> {
            assertEquals("Test Post", post.getTitle());
        });

    }

    @Test
    void testCreatePost() {

        // ARRANGE
        PostEntity postEntity =
                new PostEntity(
                        "Test Post",
                        "Test Text",
                        0);
        postEntity.setId(1L);

        // ACT
        postService.create(postEntity);

        // ASSERT
        postRepository.findById(postEntity.getId()).ifPresent(post -> {
            assertEquals("Test Text", post.getText());
        });

    }

}

package com.scalefocus.blogapp;

import com.scalefocus.blogapp.entity.PostEntity;
import com.scalefocus.blogapp.entity.TagEntity;
import com.scalefocus.blogapp.model.CreatePostResponse;
import com.scalefocus.blogapp.repository.PostRepository;
import com.scalefocus.blogapp.service.PostService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
public class PostServiceIntegrationTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Test
    void testSavePost() {

        PostEntity postEntity =
                new PostEntity(
                        "Test Post",
                        "Test Text",
                        0);

        PostEntity savedPost = postRepository.save(postEntity);

        assertNotNull(savedPost.getId());
        assertEquals("Test Post", savedPost.getTitle());

        postRepository.findById(savedPost.getId()).ifPresent(post -> {
            assertEquals("Test Text", post.getText());
        });

    }

    @Test
    void testGetAllPosts() {

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

        postService.save(postEntity);
        postService.save(postEntity1);

        List<PostEntity> allPosts = postRepository.findAll();

        assertNotNull(allPosts);
        assertEquals(12, allPosts.size()); //I have 10 initial post so we expected 12
    }

    @Test
    void testUpdatePost() {

        PostEntity postEntity =
                new PostEntity(
                        "Test Post",
                        "Test Text",
                        0);
        postEntity.setId(11L);

        CreatePostResponse createPostResponse = postService.create(postEntity);
        assertNotNull(createPostResponse.getId());

        postEntity.setTitle("Updated Test Post");

        postService.update(postEntity, createPostResponse.getId());

        postRepository.findById(postEntity.getId()).ifPresent(post -> {
            assertEquals("Updated Test Post", post.getTitle());
        });

    }

    @Test
    void testFindAllPostsByTag() {

        String tag = "IT";
        TagEntity tagEntity = new TagEntity(tag);

        PostEntity postEntity =
                new PostEntity(
                        "Test Post",
                        "Test Text",
                        0);

        postEntity.setTags(Set.of(tagEntity));

        postService.save(postEntity);

        List<PostEntity> allPostsByTag = postRepository.findAllByTags(Set.of(tagEntity));

        assertNotNull(allPostsByTag);
        assertEquals(1, allPostsByTag.size());

    }

    @Test
    void testFindPostById() {

        PostEntity postEntity =
                new PostEntity(
                        "Test Post",
                        "Test Text",
                        0);
        postEntity.setId(1L);

        postService.save(postEntity);

        postService.findById(postEntity.getId());

        postRepository.findById(postEntity.getId()).ifPresent(post -> {
            assertEquals("Test Post", post.getTitle());
        });

    }

    @Test
    void testCreatePost() {
        PostEntity postEntity =
                new PostEntity(
                        "Test Post",
                        "Test Text",
                        0);
        postEntity.setId(1L);

        postService.create(postEntity);

        postRepository.findById(postEntity.getId()).ifPresent(post -> {
            assertEquals("Test Text", post.getText());
        });
        
    }

}

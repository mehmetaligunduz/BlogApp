package com.scalefocus.blogapp;

import com.scalefocus.blogapp.entity.PostEntity;
import com.scalefocus.blogapp.model.PostModel;
import com.scalefocus.blogapp.model.TagModel;
import com.scalefocus.blogapp.model.UpdatePostRequest;
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
                        "Test Text");

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
        PostModel postModel =
                new PostModel(
                        "Test Post",
                        "Test Text");

        PostModel postModel1 =
                new PostModel(
                        "Test Post1",
                        "Test Text1");

        // ACT
        postService.create(postModel);
        postService.create(postModel1);

        List<PostEntity> allPosts = postRepository.findAll();

        // ASSERT
        assertNotNull(allPosts);
        assertEquals(12, allPosts.size()); //I have 10 initial post so we expected 12
    }

    @Test
    void testUpdatePost() {

        // ARRANGE
        PostModel postModel =
                new PostModel(
                        "Test Post",
                        "Test Text");
        postModel.setId(11L);

        // ACT
        Optional<PostModel> createdPost = postService.create(postModel);

        if (createdPost.isEmpty()) {
            return;
        }

        UpdatePostRequest updatePostRequest = new UpdatePostRequest("new Title", "new Text");

        // ACT
        postService.update(updatePostRequest, createdPost.get().getId());

        // ASSERT
        assertNotNull(createdPost.get().getId());
        postRepository.findById(postModel.getId()).ifPresent(post -> {
            assertEquals(11L, post.getId());
            assertEquals("Updated Test Post", post.getTitle());
        });

    }

    @Test
    void testFindAllPostsByTag() {

        // ARRANGE
        String tag = "IT";
        TagModel tagModel = new TagModel(tag);

        PostModel postModel =
                new PostModel(
                        "Test Post",
                        "Test Text");

        postModel.setTags(Set.of(tagModel));

        // ACT

        postService.create(postModel);

        List<PostEntity> allPostsByTag = postRepository.findAllByTags_Tag(tag);

        // ASSERT
        assertNotNull(allPostsByTag);
        assertEquals(1, allPostsByTag.size());

    }

    @Test
    void testFindPostById() {

        // ARRANGE
        PostModel postModel =
                new PostModel(
                        "Test Post",
                        "Test Text");
        postModel.setId(1L);

        // ACT
        postService.create(postModel);

        postService.findById(postModel.getId());

        // ASSERT
        postRepository.findById(postModel.getId()).ifPresent(post -> {
            assertEquals("Test Post", post.getTitle());
        });

    }

    @Test
    void testCreatePost() {

        // ARRANGE
        PostModel postModel =
                new PostModel(
                        "Test Post",
                        "Test Text");
        postModel.setId(1L);

        // ACT
        postService.create(postModel);

        // ASSERT
        postRepository.findById(postModel.getId()).ifPresent(post -> {
            assertEquals("Test Text", post.getText());
        });

    }

}

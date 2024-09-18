package com.scalefocus.blogapp;

import com.scalefocus.blogapp.entity.PostEntity;
import com.scalefocus.blogapp.entity.TagEntity;
import com.scalefocus.blogapp.model.CreatePostResponse;
import com.scalefocus.blogapp.model.GetPostsByTagResponse;
import com.scalefocus.blogapp.model.GetPostsResponse;
import com.scalefocus.blogapp.model.UpdatePostResponse;
import com.scalefocus.blogapp.repository.PostRepository;
import com.scalefocus.blogapp.repository.TagRepository;
import com.scalefocus.blogapp.service.PostServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PostServiceUnitTest {

    @Mock
    PostRepository postRepository;

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    PostServiceImpl postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSavePost() {

        //GIVEN
        PostEntity postEntity =
                new PostEntity(
                        "Test Post",
                        "Test Text",
                        0);

        when(postRepository.save(any(PostEntity.class))).thenReturn(postEntity);

        //WHEN
        PostEntity savedPost = postService.save(postEntity);

        //THEN
        assertNotNull(savedPost);
        assertEquals("Test Post", savedPost.getTitle());
        verify(postRepository, times(1)).save(postEntity);

    }

    @Test
    void testGetAllPosts() {

        //GIVEN
        PostEntity postEntity =
                new PostEntity(
                        "Test Post",
                        "Test Text",
                        0);

        List<PostEntity> postEntityList = List.of(postEntity);

        when(postRepository.findAll()).thenReturn(postEntityList);

        //WHEN
        List<GetPostsResponse> allPosts = postService.getAll();

        //THEN
        assertNotNull(allPosts);
        assertEquals(postEntityList.size(), allPosts.size());
        verify(postRepository, times(1)).findAll();
    }

    @Test
    void testUpdatePost() {

        //GIVEN
        PostEntity postEntity =
                new PostEntity(
                        "Test Post",
                        "Test Text",
                        0);


        when(postRepository.findById(any(Long.class))).thenReturn(Optional.of(postEntity));
        when(postRepository.save(any(PostEntity.class))).thenReturn(postEntity);

        //WHEN
        postEntity.setTitle("Test Post Updated");
        UpdatePostResponse updatedPost = postService.update(postEntity, 0L);

        //THEN
        assertNotNull(updatedPost);
        assertNotEquals("Test Post", updatedPost.getTitle());
        assertEquals("Test Post Updated", updatedPost.getTitle());
        verify(postRepository, times(1)).findById(any(Long.class));
        verify(postRepository, times(1)).save(postEntity);

    }

    @Test
    void testFindAllPostsByTag() {

        //GIVEN
        String tag = "IT";
        TagEntity tagEntity = new TagEntity(tag);

        PostEntity postEntity =
                new PostEntity(
                        "Test Post",
                        "Test Text",
                        0);
        postEntity.setTags(Set.of(tagEntity));

        when(tagRepository.findByTag(tag)).thenReturn(Optional.of(tagEntity));
        when(postRepository.findAllByTags(Set.of(tagEntity))).thenReturn(List.of(postEntity));

        //WHEN
        List<GetPostsByTagResponse> foundedPosts = postService.findAllByTag("IT");

        //THEN
        assertNotNull(foundedPosts);
        assertEquals(1, foundedPosts.size());

        GetPostsByTagResponse getPostsByTagResponse = foundedPosts.getFirst();
        assertNotNull(getPostsByTagResponse);
        assertEquals("Test Post", getPostsByTagResponse.getTitle());

        verify(tagRepository, times(1)).findByTag(tag);
        verify(postRepository, times(1)).findAllByTags(Set.of(tagEntity));
    }

    @Test
    void testFindById() {

        //GIVEN
        PostEntity postEntity =
                new PostEntity(
                        "Test Post",
                        "Test Text",
                        0);

        when(postRepository.findById(any(Long.class))).thenReturn(Optional.of(postEntity));

        //WHEN
        Optional<PostEntity> foundedPost = postService.findById(0L);

        //THEN
        foundedPost.ifPresent(pe -> {
            assertNotNull(foundedPost);
            assertEquals("Test Post", foundedPost.get().getTitle());
            verify(postRepository, times(1)).findById(any(Long.class));
        });
    }

    @Test
    void testCreatePost() {

        //GIVEN
        PostEntity postEntity =
                new PostEntity(
                        "Test Post",
                        "Test Text",
                        0);
        postEntity.setId(1L);

        when(postRepository.save(any(PostEntity.class))).thenReturn(postEntity);

        //WHEN
        CreatePostResponse createPostResponse = postService.create(postEntity);

        //THEN
        assertNotNull(createPostResponse);
        assertEquals(1L, createPostResponse.getId());
        verify(postRepository, times(1)).save(any(PostEntity.class));
    }

}

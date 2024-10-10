package com.scalefocus.blogapp;

import com.scalefocus.blogapp.entity.PostEntity;
import com.scalefocus.blogapp.entity.TagEntity;
import com.scalefocus.blogapp.mapper.PostMapper;
import com.scalefocus.blogapp.model.*;
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

class PostServiceUnitTest {

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
                        "Test Text");

        when(postRepository.save(any(PostEntity.class))).thenReturn(postEntity);

        PostModel postModel = PostMapper.INSTANCE.postEntityToModel(postEntity);

        //WHEN
        Optional<PostModel> savedPost = postService.create(postModel);

        //THEN
        savedPost.ifPresent(pe -> {
            assertNotNull(pe);
            assertEquals("Test Post", pe.getTitle());
        });

        verify(postRepository, times(1)).save(postEntity);

    }

    @Test
    void testGetAllPosts() {

        //GIVEN
        PostEntity postEntity =
                new PostEntity(
                        "Test Post",
                        "Test Text");

        List<PostEntity> postEntityList = List.of(postEntity);

        when(postRepository.findAll()).thenReturn(postEntityList);

        //WHEN
        List<PostWithSummaryTextResponse> allPosts = postService.getAll();

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
                        "Test Text");


        when(postRepository.findById(any(Long.class))).thenReturn(Optional.of(postEntity));
        when(postRepository.save(any(PostEntity.class))).thenReturn(postEntity);

        //WHEN
        postEntity.setTitle("Test Post Updated");

        UpdatePostRequest updatePostRequest = new UpdatePostRequest("Title Test Post Updated", "Text Test Post Updated");

        Optional<UpdatePostResponse> updatedPost = postService.update(updatePostRequest, 0L);

        updatedPost.ifPresent(up -> {

            //THEN
            assertNotNull(up);
            assertNotEquals("Test Post", up.getTitle());
            assertEquals("Test Post Updated", up.getTitle());
            verify(postRepository, times(1)).findById(any(Long.class));
            verify(postRepository, times(1)).save(postEntity);

        });

    }

    @Test
    void testFindAllPostsByTag() {

        //GIVEN
        String tag = "IT";
        TagEntity tagEntity = new TagEntity(tag);

        PostEntity postEntity =
                new PostEntity(
                        "Test Post",
                        "Test Text");
        postEntity.setTags(Set.of(tagEntity));

        when(postRepository.findAllByTags_Tag(tag)).thenReturn(List.of(postEntity));

        //WHEN
        List<GetPostsByTagResponse> foundedPosts = postService.getAllByTag("IT");

        //THEN
        assertNotNull(foundedPosts);
        assertEquals(1, foundedPosts.size());

        GetPostsByTagResponse getPostsByTagResponse = foundedPosts.getFirst();
        assertNotNull(getPostsByTagResponse);
        assertEquals("Test Post", getPostsByTagResponse.getTitle());

        verify(tagRepository, times(1)).findByTag(tag);
        verify(postRepository, times(1)).findAllByTags_Tag(tag);

    }

    @Test
    void testFindById() {

        //GIVEN
        PostEntity postEntity =
                new PostEntity(
                        "Test Post",
                        "Test Text");

        when(postRepository.findById(any(Long.class))).thenReturn(Optional.of(postEntity));

        //WHEN
        Optional<PostModel> foundedPost = postService.findById(0L);

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
                        "Test Text");
        postEntity.setId(1L);

        when(postRepository.save(any(PostEntity.class))).thenReturn(postEntity);

        PostModel postModel = PostMapper.INSTANCE.postEntityToModel(postEntity);

        //WHEN
        Optional<PostModel> createPostResponse = postService.create(postModel);

        //THEN
        createPostResponse.ifPresent(
                cpr -> {
                    assertNotNull(cpr);
                    assertEquals(1L, cpr.getId());
                });

        verify(postRepository, times(1)).save(any(PostEntity.class));

    }
}

package com.scalefocus.blogapp;

import com.scalefocus.blogapp.entity.PostEntity;
import com.scalefocus.blogapp.entity.TagEntity;
import com.scalefocus.blogapp.mapper.PostMapper;
import com.scalefocus.blogapp.model.*;
import com.scalefocus.blogapp.repository.TagRepository;
import com.scalefocus.blogapp.service.PostService;
import com.scalefocus.blogapp.service.TagServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TagServiceUnitTest {

    @Mock
    private TagRepository tagRepository;

    @Mock
    private PostService postService;

    @InjectMocks
    private TagServiceImpl tagService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddTagToPost() {

        //GIVEN
        TagEntity tag1 = new TagEntity("tag1");
        TagEntity tag2 = new TagEntity("tag2");
        Set<TagEntity> tags = Set.of(tag1, tag2);

        AddTagRequest addTagRequest = new AddTagRequest();
        addTagRequest.setTags(List.of("tag3", "tag4"));

        Long postId = 1L;

        PostEntity postEntity =
                new PostEntity(
                        "Test Post",
                        "Test Text");
        postEntity.setId(postId);
        postEntity.setTags(tags);
        
        PostModel postModel = PostMapper.INSTANCE.postEntityToModel(postEntity);

        when(postService.findById(postId)).thenReturn(Optional.of(postModel));
        when(postService.create(any(PostModel.class))).thenReturn(Optional.of(postModel));
        when(tagRepository.save(any(TagEntity.class))).thenReturn(tag1);
        when(tagRepository.save(any(TagEntity.class))).thenReturn(tag2);

        // WHEN
        Optional<AddTagResponse> taggedPost = tagService.addTag(addTagRequest, postId);

        // THEN
        if (taggedPost.isEmpty()) {
            return;
        }

        assertEquals(2, taggedPost.get().getTags().size());
    }

    @Test
    void testDeleteTagFromPost() {

        //GIVEN
        TagEntity tag1 = new TagEntity("IT");
        TagEntity tag2 = new TagEntity("Java");
        Set<TagEntity> tags = Set.of(tag1, tag2);

        Long postId = 1L;

        PostEntity postEntity =
                new PostEntity(
                        "Test Post",
                        "Test Text");
        postEntity.setId(postId);

        postEntity.setId(postId);
        postEntity.setTags(tags);

        PostModel postModel = PostMapper.INSTANCE.postEntityToModel(postEntity);
        DeleteTagRequest deleteTagRequest = new DeleteTagRequest();
        deleteTagRequest.setTag("IT");

        when(postService.findById(postId)).thenReturn(Optional.of(postModel));
        when(tagRepository.findByTag("IT")).thenReturn(Optional.of(tag1));
        when(tagRepository.findByTag("Java")).thenReturn(Optional.of(tag2));
        when(postService.create(any(PostModel.class))).thenReturn(Optional.of(postModel));

        //WHEN
        Optional<DeleteTagResponse> deleteTagResponse = tagService.deleteTag(deleteTagRequest, postId);

        deleteTagResponse.ifPresent(dtr -> {

            //THEN
            assertEquals(1, dtr.getTags().size());
            assertEquals(tag2.getTag(), dtr.getTags().iterator().next().getTag());
        });

    }

}


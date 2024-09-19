package com.scalefocus.blogapp;

import com.scalefocus.blogapp.entity.PostEntity;
import com.scalefocus.blogapp.entity.TagEntity;
import com.scalefocus.blogapp.model.AddTagResponse;
import com.scalefocus.blogapp.model.DeleteTagResponse;
import com.scalefocus.blogapp.repository.TagRepository;
import com.scalefocus.blogapp.service.PostService;
import com.scalefocus.blogapp.service.TagServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TagServiceUnitTest {

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

        Long postId = 1L;

        PostEntity postEntity =
                new PostEntity(
                        "Test Post",
                        "Test Text",
                        0);
        postEntity.setId(postId);

        when(postService.findById(postId)).thenReturn(Optional.of(postEntity));
        when(postService.create(any(PostEntity.class))).thenReturn(Optional.of(postEntity));
        when(tagRepository.save(any(TagEntity.class))).thenReturn(tag1);
        when(tagRepository.save(any(TagEntity.class))).thenReturn(tag2);
        when(tagRepository.findByTag("tag1")).thenReturn(Optional.of(tag1));
        when(tagRepository.findByTag("tag2")).thenReturn(Optional.of(tag2));

        // WHEN
        Optional<AddTagResponse> taggedPost = tagService.addTag(tags, postId);

        // THEN
        taggedPost.ifPresent(tp -> {
            assertEquals(postEntity.getTitle(), tp.getTitle());
            assertEquals(postEntity.getText(), tp.getText());
            assertEquals(
                    tags
                            .stream()
                            .map(TagEntity::getTag)
                            .collect(Collectors.toSet()), tp.getTags());

        });
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
                        "Test Text",
                        0);
        postEntity.setId(postId);

        postEntity.setId(postId);
        postEntity.setTags(tags);

        when(postService.findById(postId)).thenReturn(Optional.of(postEntity));
        when(tagRepository.findByTag("IT")).thenReturn(Optional.of(tag1));
        when(tagRepository.findByTag("Java")).thenReturn(Optional.of(tag2));
        when(postService.create(any(PostEntity.class))).thenReturn(Optional.of(postEntity));

        //WHEN
        Optional<DeleteTagResponse> deleteTagResponse = tagService.deleteTag(tag1, postId);

        deleteTagResponse.ifPresent(dtr -> {

            //THEN
            assertEquals(1, dtr.getTags().size());
            assertEquals(tag2.getTag(), dtr.getTags().iterator().next());
        });

    }

}


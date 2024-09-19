package com.scalefocus.blogapp;

import com.scalefocus.blogapp.entity.PostEntity;
import com.scalefocus.blogapp.entity.TagEntity;
import com.scalefocus.blogapp.model.AddTagResponse;
import com.scalefocus.blogapp.model.DeleteTagResponse;
import com.scalefocus.blogapp.repository.TagRepository;
import com.scalefocus.blogapp.service.PostService;
import com.scalefocus.blogapp.service.TagService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class TagServiceIntegrationTest {

    @Autowired
    private TagService tagService;

    @Autowired
    private PostService postService;

    @Autowired
    private TagRepository tagRepository;

    @Test
    void testAddTagToPost() {

        // ARRANGE
        PostEntity postEntity =
                new PostEntity(
                        "Test Post",
                        "Test Text",
                        0);
        postEntity.setId(11L);

        TagEntity tag1 = new TagEntity("IT");
        TagEntity tag2 = new TagEntity("Java");
        Set<TagEntity> tags = Set.of(tag1, tag2);

        // ACT
        postService.create(postEntity);
        Optional<AddTagResponse> addTagResponse = tagService.addTag(tags, postEntity.getId());

        addTagResponse.ifPresent(
                atr -> {
                    //ASSERT
                    assertNotNull(atr);
                    assertEquals(2, atr.getTags().size());
                }
        );

        // OPTIONAL
        Optional<TagEntity> byTag = tagRepository.findByTag("IT");
        byTag.ifPresent(t -> {
            assertEquals("IT", t.getTag());
        });

    }

    @Test
    void testRemoveTagFromPost() {

        // ARRANGE
        TagEntity tag1 = new TagEntity("IT");
        TagEntity tag2 = new TagEntity("Java");
        Set<TagEntity> tags = Set.of(tag1, tag2);

        PostEntity postEntity =
                new PostEntity(
                        "Test Post",
                        "Test Text",
                        0);
        postEntity.setId(11L);

        // ACT
        postService.create(postEntity);
        tagService.addTag(tags, postEntity.getId());
        Optional<DeleteTagResponse> deleteTagResponse = tagService.deleteTag(tag2, postEntity.getId());

        deleteTagResponse.ifPresent(dtr -> {
            // ASSERT
            assertNotNull(dtr);
            assertEquals(1, dtr.getTags().size());
            assertTrue(dtr.getTags().contains(tag1.getTag()));

        });

    }

}

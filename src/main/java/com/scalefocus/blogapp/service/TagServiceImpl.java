package com.scalefocus.blogapp.service;

import com.scalefocus.blogapp.entity.PostEntity;
import com.scalefocus.blogapp.entity.TagEntity;
import com.scalefocus.blogapp.model.AddTagResponse;
import com.scalefocus.blogapp.model.DeleteTagResponse;
import com.scalefocus.blogapp.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    private final PostService postService;

    @Override
    public AddTagResponse addTag(Set<TagEntity> tags, Long postId) {

        Optional<PostEntity> post = postService.findById(postId);

        if (post.isEmpty()) {
            return null;
        }

        Set<TagEntity> tagEntities = tags.stream()
                .map(t -> tagRepository.findByTag(t.getTag())
                        .orElseGet(() -> {
                            TagEntity newTag = new TagEntity();
                            newTag.setTag(t.getTag());
                            return tagRepository.save(newTag);
                        })
                )
                .collect(Collectors.toSet());

        if (post.get().getTags() == null) {
            post.get().setTags(tagEntities);
        } else {
            post.get().getTags().addAll(tagEntities);
        }

        final PostEntity taggedPost = postService.save(post.get());

        return new AddTagResponse(
                taggedPost.getTitle(),
                taggedPost.getText(),
                taggedPost
                        .getTags()
                        .stream()
                        .map(TagEntity::getTag)
                        .collect(Collectors.toSet())
        );
    }

    @Override
    public DeleteTagResponse deleteTag(TagEntity tagEntity, Long postId) {

        Optional<PostEntity> post = postService.findById(postId);

        if (post.isEmpty()) {
            return null;
        }

        Set<TagEntity> tags = new HashSet<>(post.get().getTags());

        tags.remove(tagEntity);

        post.get().setTags(tags);
        final PostEntity deletedTagPost = postService.save(post.get());

        return new DeleteTagResponse(
                deletedTagPost.getTitle(),
                deletedTagPost.getText(),
                deletedTagPost
                        .getTags()
                        .stream()
                        .map(TagEntity::getTag)
                        .collect(Collectors.toSet())
        );

    }

}

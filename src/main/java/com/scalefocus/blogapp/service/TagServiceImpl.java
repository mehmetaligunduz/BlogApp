package com.scalefocus.blogapp.service;

import com.scalefocus.blogapp.entity.PostEntity;
import com.scalefocus.blogapp.entity.TagEntity;
import com.scalefocus.blogapp.mapper.TagMapper;
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
    public Optional<AddTagResponse> addTag(Set<TagEntity> tags, Long postId) {

        Optional<PostEntity> post = postService.findById(postId);

        if (post.isEmpty()) {

            return Optional.empty();

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


        PostEntity getPost = post.get();

        if (getPost.getTags() == null) {

            getPost.setTags(tagEntities);

        } else {

            getPost.getTags().addAll(tagEntities);

        }

        final Optional<PostEntity> taggedPost = postService.create(getPost);

        return taggedPost.map(TagMapper.INSTANCE::addTagPostToModel);

    }

    @Override
    public Optional<DeleteTagResponse> deleteTag(TagEntity tagEntity, Long postId) {

        Optional<PostEntity> post = postService.findById(postId);

        if (post.isEmpty()) {

            return Optional.empty();

        }

        Set<TagEntity> tags = new HashSet<>(post.get().getTags());

        tags.remove(tagEntity);

        post.get().setTags(tags);

        Optional<PostEntity> deletedTagPost = postService.create(post.get());

        return deletedTagPost.map(TagMapper.INSTANCE::deleteTagPostToModel);

    }

}

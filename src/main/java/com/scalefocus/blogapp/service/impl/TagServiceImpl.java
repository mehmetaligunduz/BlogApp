package com.scalefocus.blogapp.service.impl;

import com.scalefocus.blogapp.entity.PostEntity;
import com.scalefocus.blogapp.entity.TagEntity;
import com.scalefocus.blogapp.mapper.PostMapper;
import com.scalefocus.blogapp.mapper.TagMapper;
import com.scalefocus.blogapp.model.*;
import com.scalefocus.blogapp.repository.TagRepository;
import com.scalefocus.blogapp.service.PostService;
import com.scalefocus.blogapp.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    private final PostService postService;

    @Override
    public Optional<AddTagResponse> addTag(AddTagRequest addTagRequest, Long postId) {

        Optional<PostModel> post = postService.findById(postId);

        if (post.isEmpty()) {

            return Optional.empty();

        }

        Set<TagEntity> tagEntity = addTagRequest
                .getTags()
                .stream()
                .map(t -> tagRepository.findByTag(t)
                        .orElseGet(() ->
                                tagRepository.save(new TagEntity(t))
                        )).collect(Collectors.toSet());


        PostEntity postEntity = PostMapper.INSTANCE.postModelToEntity(post.get());
        postEntity.getTags().addAll(tagEntity);

        PostModel mapperPostModel = PostMapper.INSTANCE.postEntityToModel(postEntity);

        Optional<PostModel> postModel = postService.create(mapperPostModel);

        log.info("Post tagged - tag(s): {}", addTagRequest.getTags());

        return postModel
                .map(TagMapper.INSTANCE::postModelToAddTagResponse);

    }

    @Override
    public Optional<DeleteTagResponse> deleteTag(DeleteTagRequest deleteTagRequest, Long postId) {

        Optional<PostModel> post = postService.findById(postId);

        if (post.isEmpty()) {

            return Optional.empty();

        }

        TagModel tagModel = new TagModel(deleteTagRequest.getTag());

        post.get().getTags().remove(tagModel);


        Optional<PostModel> savedPostEntity = postService.create(post.get());

        return savedPostEntity
                .map(TagMapper
                        .INSTANCE::postEntityToDeleteTagResponse);

    }

}

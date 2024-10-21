package com.scalefocus.blogapp.mapper;

import com.scalefocus.blogapp.entity.PostEntity;
import com.scalefocus.blogapp.entity.TagEntity;
import com.scalefocus.blogapp.model.GetPostsByTagResponse;
import com.scalefocus.blogapp.model.PostModel;
import com.scalefocus.blogapp.model.PostWithSummaryTextResponse;
import com.scalefocus.blogapp.model.UpdatePostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    List<PostWithSummaryTextResponse> allPostEntityToModel(List<PostEntity> postEntities);

    List<PostWithSummaryTextResponse> allPostsEntityToModel(List<PostEntity> postEntities);

    UpdatePostResponse updatePostEntityToModel(PostEntity postEntity);

    PostModel postEntityToModel(PostEntity postEntity);

    PostEntity postModelToEntity(PostModel postModel);

    @Mapping(source = "tags", target = "tags", qualifiedByName = "setToString")
    List<GetPostsByTagResponse> postEntityToModelWithTags(List<PostEntity> postEntities);

    default Set<String> setToString(Set<TagEntity> tagEntitySet) {
        return tagEntitySet
                .stream()
                .map(TagEntity::getTag)
                .collect(Collectors.toSet());
    }

}

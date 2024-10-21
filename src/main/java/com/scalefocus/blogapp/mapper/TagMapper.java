package com.scalefocus.blogapp.mapper;

import com.scalefocus.blogapp.entity.PostEntity;
import com.scalefocus.blogapp.entity.TagEntity;
import com.scalefocus.blogapp.model.AddTagResponse;
import com.scalefocus.blogapp.model.DeleteTagResponse;
import com.scalefocus.blogapp.model.PostModel;
import com.scalefocus.blogapp.model.TagModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper
public interface TagMapper {

    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    AddTagResponse addTagPostToModel(PostEntity postEntity);

    DeleteTagResponse postEntityToDeleteTagResponse(PostModel postModel);

    Set<TagModel> tagEntityToTagModel(Set<TagEntity> tagEntities);

    AddTagResponse postModelToAddTagResponse(PostModel postModel);

}

package com.scalefocus.blogapp.mapper;

import com.scalefocus.blogapp.entity.PostEntity;
import com.scalefocus.blogapp.model.AddTagResponse;
import com.scalefocus.blogapp.model.DeleteTagResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TagMapper {

    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    AddTagResponse addTagPostToModel(PostEntity postEntity);

    DeleteTagResponse deleteTagPostToModel(PostEntity postEntity);

}

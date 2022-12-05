package com.alpersurekci.blogprojectwspring.mapper;

import com.alpersurekci.blogprojectwspring.business.dto.BlogDto;
import com.alpersurekci.blogprojectwspring.data.entity.BlogEntity;


public class PostMapper {

    // map post entity to dto
    public static BlogDto mapToBlogDto(BlogEntity blogEntity) {
        return BlogDto.builder().blogID(blogEntity.getBlogID())
                .title(blogEntity.getBlogTitle())
                .blogContain(blogEntity.getBlogContain())
                .blogShort(blogEntity.getBlogShort())
                .writtenBy(blogEntity.getWrittenBy())
                .build();
    }

    // map dto to entity
    public static BlogEntity mapToBlogEntity(BlogDto blogDto) {
        return BlogEntity.builder()
                .blogID(blogDto.getBlogID())
                .blogContain(blogDto.getBlogContain())
                .blogShort(blogDto.getBlogShort())
                .writtenBy(blogDto.getWrittenBy())
                .blogTitle(blogDto.getTitle())
                .build();
    }

}

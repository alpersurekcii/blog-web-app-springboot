package com.alpersurekci.blogprojectwspring.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogDto {

    private Long blogID;

    @NotEmpty(message = "Blog's title can not be empty.")
    private String title;

    @NotEmpty(message = "Blog's contain can not be empty.")
    private String blogContain;

    @NotEmpty(message = "Blog's short can not be empty.")
    private String blogShort;

    private String writtenBy;


}

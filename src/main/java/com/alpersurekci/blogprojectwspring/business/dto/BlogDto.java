package com.alpersurekci.blogprojectwspring.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.beans.Transient;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogDto {

    @NotEmpty(message = "Blog's title can not be empty.")
    private String title;

    @NotEmpty(message = "Blog's contain can not be empty.")
    private String blogContain;


    private String blogImage;


    private String blogShort;




}
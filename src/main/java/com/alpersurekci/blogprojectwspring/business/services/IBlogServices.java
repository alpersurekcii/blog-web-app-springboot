package com.alpersurekci.blogprojectwspring.business.services;

import com.alpersurekci.blogprojectwspring.business.dto.BlogDto;
import com.alpersurekci.blogprojectwspring.business.dto.UserDto;
import com.alpersurekci.blogprojectwspring.data.entity.BlogEntity;
import com.alpersurekci.blogprojectwspring.data.entity.UserEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface IBlogServices {


    void userSave(UserDto userDto);

    BlogEntity saveBlog(BlogDto blogDto);


    List<BlogDto> findAllBlogsById();

    ResponseEntity<BlogDto> deleteBlogById(Long id);

    BlogEntity updateBlogById(Long id, BlogDto blogDto);

    BlogDto showBlogById(Long id);

    List<BlogDto> IndexBlog();

    boolean userControl(Long id);

    List<BlogDto> listAllBlog();


    UserEntity userDtoToEntity(UserDto userDto);

    UserDto userEntityToDto(UserEntity userEntity);

}

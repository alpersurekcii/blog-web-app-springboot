package com.alpersurekci.blogprojectwspring.business.services;

import com.alpersurekci.blogprojectwspring.business.dto.BlogDto;
import com.alpersurekci.blogprojectwspring.business.dto.UserDto;
import com.alpersurekci.blogprojectwspring.data.entity.BlogEntity;
import com.alpersurekci.blogprojectwspring.data.entity.UserEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface IBlogServices {

    public UserEntity userDtoToEntity(UserDto userDto);

    public UserDto userEntityToDto(UserEntity userEntity);

    public void userSave(UserDto userDto);

    public BlogEntity saveBlog(BlogDto blogDto);

    public BlogDto blogEntityToDto(BlogEntity blogEntity);

    public BlogEntity blogDtoToEntity(BlogDto blogDto);

    public List<BlogEntity> findAllBlogsById();

    public ResponseEntity<BlogDto> deleteBlogById(Long id);

    public BlogEntity updateBlogById(Long id, BlogDto blogDto);

    public BlogEntity showBlogById(Long id);

    public List<BlogEntity> IndexBlog();

    public boolean userControl(Long id);

    public List<BlogEntity> listAllBlog();
}

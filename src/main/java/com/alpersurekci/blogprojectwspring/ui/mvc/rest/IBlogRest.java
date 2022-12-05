package com.alpersurekci.blogprojectwspring.ui.mvc.rest;


import com.alpersurekci.blogprojectwspring.business.dto.BlogDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface IBlogRest {

    List<BlogDto> listBlogs();

    ResponseEntity<BlogDto> saveBlog(BlogDto blogDto);

    ResponseEntity<Map<String, Boolean>> updateBlog(Long id, BlogDto blogDto);

    ResponseEntity<Map<String, Boolean>> deleteBlog(Long id);

    BlogDto showBlog(Long id);
}

package com.alpersurekci.blogprojectwspring.ui.mvc.rest.impl;

import com.alpersurekci.blogprojectwspring.business.dto.BlogDto;
import com.alpersurekci.blogprojectwspring.business.services.IBlogServices;
import com.alpersurekci.blogprojectwspring.data.entity.BlogEntity;
import com.alpersurekci.blogprojectwspring.ui.mvc.rest.IBlogRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class BlogPageRestImp implements IBlogRest {

    @Autowired
    IBlogServices iBlogServices;


    @Override
    @GetMapping("/list")
    public List<BlogDto> listBlogs() {
        List<BlogEntity> blogEntity = iBlogServices.IndexBlog();
        List<BlogDto> blogDtos = new ArrayList<>();

        for (int i = 0; i < blogEntity.size(); i++) {
            blogDtos.add(iBlogServices.blogEntityToDto(blogEntity.get(i)));
        }
        return  blogDtos;
    }

    @Override
    @PostMapping("/save/blog")
    public ResponseEntity<BlogDto> saveBlog(BlogDto blogDto) {
        iBlogServices.saveBlog(blogDto);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Saved", Boolean.TRUE);
        return ResponseEntity.ok(blogDto);
    }

    @Override
    @PutMapping("/update/blog/{id}")
    public ResponseEntity<Map<String, Boolean>> updateBlog(@PathVariable(name="id") Long id, BlogDto blogDto) {
        iBlogServices.updateBlogById(id, blogDto);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Updated", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @Override
    @DeleteMapping("/delete/blog/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteBlog(@PathVariable(name="id") Long id) {
        iBlogServices.deleteBlogById(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("/show/{id}")
    public BlogDto showBlog(@PathVariable(name="id") Long id) {
        BlogEntity blogEntity = iBlogServices.showBlogById(id);
        BlogDto blogDto  = iBlogServices.blogEntityToDto(blogEntity);
        return blogDto;



    }
}

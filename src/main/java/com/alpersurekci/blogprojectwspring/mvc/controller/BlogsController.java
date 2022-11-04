package com.alpersurekci.blogprojectwspring.mvc.controller;

import com.alpersurekci.blogprojectwspring.business.dto.BlogDto;
import com.alpersurekci.blogprojectwspring.business.services.FileUploadUtil;
import com.alpersurekci.blogprojectwspring.business.services.IBlogServices;
import com.alpersurekci.blogprojectwspring.data.entity.BlogEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Log4j2
@Controller
public class BlogsController {

    @Autowired
    IBlogServices services;

    @GetMapping("/save")
    public String getBlogs(Model model){
        model.addAttribute("blog_key", new BlogDto());
        return "save";
    }

    //@Valid @ModelAttribute("blog")BlogDto blogDto,
    @PostMapping("/save")
    public String postSaveBlog(@Valid @ModelAttribute("blog_key")BlogDto blogDto, @RequestParam("fileimage") MultipartFile multipartFile, BindingResult bindingResult) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        if(bindingResult.hasErrors()){
            log.info("blog kaydetmede hata");
        }
        String shortest = blogDto.getBlogContain().substring(0,200);
        blogDto.setBlogImage(fileName);
        blogDto.setBlogShort(shortest);

      BlogEntity blogEntity =  services.saveBlog(blogDto);
       String uploadDir = "blogs-photos/" + blogEntity.getBlogID();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        return "redirect:/";
    }



    @GetMapping("/delete/blog/{id}")
    public String deleteBlogById(@PathVariable(name="id")Long id){
        services.deleteBlogById(id);
        return "redirect:/list/blog";
    }

    @GetMapping("/update/blog/{id}")
    public String getUpdateBlogById(@PathVariable(name="id")Long id, Model model){
        BlogEntity blogEntity = services.showBlogById(id);
        BlogDto blogDto = services.blogEntityToDto(blogEntity);
        model.addAttribute("blog_key", blogDto);
        return "blogupdate";
    }

    @PostMapping("/update/{id}")
    public String updateBlogById(@PathVariable(name="id")Long id, BlogDto blogDto,  @RequestParam("fileimage") MultipartFile multipartFile, Model model) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String shortest = blogDto.getBlogContain().substring(0,200);
        blogDto.setBlogImage(fileName);
        blogDto.setBlogShort(shortest);
        BlogEntity blogEntity = services.updateBlogById(id,blogDto);
        String uploadDir = "blogs-photos/" + blogEntity.getBlogID();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        return "redirect:/list/blog";
    }

    @GetMapping("/update/control/{id}")
    public String isUserUpdateBlog(@PathVariable(name="id")Long id, BlogDto blogDto, BindingResult bindingResult){
        String rt = "";
        if(bindingResult.hasErrors()){
            log.info("Update kontrolünde hata var");
        }
        if(services.userControl(id)){
            rt = "redirect:/update/blog/"+id;
        }
        else {
            rt = "updateerror";
        }
        return rt;
    }

    @GetMapping("/blog/{id}")
    public String getBlogDetail(@PathVariable(name="id")Long id, Model model){
        BlogEntity blogEntity = services.showBlogById(id);

        if(blogEntity != null) {
            model.addAttribute("blogInf", blogEntity);
            return "ShowBlog";
        }else {
            return "BlogNotFound";
        }


    }

    @GetMapping("/")
    public String getIndex(Model model){

       model.addAttribute("blogs_keys", services.IndexBlog());

    return "index";
    }

    @GetMapping("/list/blog")
    public String getBlogById(Model model){
        model.addAttribute("blogs_list", services.findAllBlogsById());
        return "list";
    }
}

package com.alpersurekci.blogprojectwspring.mvc.controller;

import com.alpersurekci.blogprojectwspring.business.dto.BlogDto;
import com.alpersurekci.blogprojectwspring.business.services.IBlogServices;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Log4j2
@Controller
public class BlogsController {

    @Autowired
    IBlogServices services;

    @GetMapping("/save")
    public String getBlogs(Model model) {
        model.addAttribute("blog_key", new BlogDto());
        return "save";
    }

    //@Valid @ModelAttribute("blog")BlogDto blogDto,
    @PostMapping("/save")
    public String postSaveBlog(@Valid @ModelAttribute("blog_key") BlogDto blogDto, BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            log.info("blog kaydetmede hata");
        }


        services.saveBlog(blogDto);


        return "redirect:/";
    }

    @GetMapping("/admin/list")
    public String listAllBlog(Model model) {


        model.addAttribute("blogs_key", services.IndexBlog());
        return "listallblog";
    }


    @GetMapping("/delete/blog/{id}")
    public String deleteBlogById(@PathVariable(name = "id") Long id) {
        services.deleteBlogById(id);
        return "redirect:/list/blog";
    }

    @GetMapping("/update/blog/{id}")
    public String getUpdateBlogById(@PathVariable(name = "id") Long id, Model model) {

        BlogDto blogDto = services.showBlogById(id);
        model.addAttribute("blog_key", blogDto);
        return "blogupdate";
    }

    @PostMapping("/update/{id}")
    public String updateBlogById(@PathVariable(name = "id") Long id, BlogDto blogDto) {

        services.updateBlogById(id, blogDto);

        return "redirect:/list/blog";
    }

    @GetMapping("/update/control/{id}")
    public String isUserUpdateBlog(@PathVariable(name = "id") Long id, BlogDto blogDto, BindingResult bindingResult) {
        String rt = "error";
        if (bindingResult.hasErrors()) {
            log.info("Update kontrolünde hata var");
        }
        if (services.userControl(id)) {
            rt = "redirect:/update/blog/" + id;
        } else {
            rt = "updateerror";
        }
        return rt;
    }

    @GetMapping("/blog/{id}")
    public String getBlogDetail(@PathVariable(name = "id") Long id, Model model) {
        BlogDto blogDto = services.showBlogById(id);

        model.addAttribute("blogInf", blogDto);

        return "ShowBlog";


    }

    @GetMapping("/")
    public String getIndex(Model model) {

        model.addAttribute("blogs_keys", services.IndexBlog());

        return "index";
    }

    @GetMapping("/list/blog")
    public String getBlogById(Model model) {
        List<BlogDto> blogDto = services.findAllBlogsById();
        model.addAttribute("blogs_list", blogDto);
        return "list";
    }
}

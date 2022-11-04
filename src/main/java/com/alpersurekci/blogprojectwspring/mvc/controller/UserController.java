package com.alpersurekci.blogprojectwspring.mvc.controller;

import com.alpersurekci.blogprojectwspring.business.dto.UserDto;
import com.alpersurekci.blogprojectwspring.business.services.IBlogServices;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.naming.Binding;
import javax.validation.Valid;

@Controller
@Log4j2
public class UserController {

    @Autowired
    IBlogServices services;

    @GetMapping("/register")
    public String getUserRegister(Model model){
        model.addAttribute("user_register",new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String postUserRegister(@Valid @ModelAttribute("user_register")UserDto userDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("register sırasında hata meydana geldi");
        }
        services.userSave(userDto);
        return "redicrect:/login";
    }

}

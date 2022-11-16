package com.alpersurekci.blogprojectwspring.business.services.impl;

import com.alpersurekci.blogprojectwspring.business.dto.BlogDto;
import com.alpersurekci.blogprojectwspring.business.dto.CustomUserDetails;
import com.alpersurekci.blogprojectwspring.business.dto.UserDto;
import com.alpersurekci.blogprojectwspring.business.services.IBlogServices;
import com.alpersurekci.blogprojectwspring.data.entity.BlogEntity;
import com.alpersurekci.blogprojectwspring.data.entity.Role;
import com.alpersurekci.blogprojectwspring.data.entity.UserEntity;
import com.alpersurekci.blogprojectwspring.data.repository.IBlogsRepository;
import com.alpersurekci.blogprojectwspring.data.repository.IRoleRepository;
import com.alpersurekci.blogprojectwspring.data.repository.IUserRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class BlogsServiceImpl implements IBlogServices {
    @Autowired
    IRoleRepository iRoleRepository;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IBlogsRepository blogsRepository;

    @Autowired
    ModelMapper modelMapper;


    CustomUserDetails customUserDetails;
    @Override
    public UserEntity userDtoToEntity(UserDto userDto) {
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        return userEntity;
    }

    @Override
    public UserDto userEntityToDto(UserEntity userEntity) {
        UserDto userDto = modelMapper.map(userEntity, UserDto.class);
        return userDto;
    }

    @Override
    public void userSave(UserDto userDto) {
        
        UserEntity userEntity = userDtoToEntity(userDto);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        userEntity.setUserPassword(bCryptPasswordEncoder.encode(userDto.getUserPassword()));
        Role role = iRoleRepository.findByName("ADMIN");
        userEntity.addRole(role);

        //iRoleRepository.save(role);
        userRepository.save(userEntity);
    }

    @Override
    public BlogEntity saveBlog(BlogDto blogDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        UserEntity userEntity = userRepository.findAllByUserEmailEquals(username);
        BlogEntity blogEntity = blogDtoToEntity(blogDto);

        blogEntity.setWrittenBy(username);
        blogEntity.setUserEntity(userEntity);
       return blogsRepository.save(blogEntity);


    }

    @Override
    public BlogDto blogEntityToDto(BlogEntity blogEntity) {
        BlogDto blogDto = modelMapper.map(blogEntity, BlogDto.class);
        return blogDto;
    }

    @Override
    public BlogEntity blogDtoToEntity(BlogDto blogDto) {
        BlogEntity blogEntity = modelMapper.map(blogDto, BlogEntity.class);
        return blogEntity;
    }

    @Override
    public List<BlogEntity> findAllBlogsById() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        UserEntity userEntity = userRepository.findAllByUserEmailEquals(username);
        Long id = userEntity.getUserID();
        List<BlogEntity> blogEntities = blogsRepository.findAllByUserEntity(id);
        return blogEntities;
    }

    @Override
    public ResponseEntity<BlogDto> deleteBlogById(Long id) {
        Optional<BlogEntity> blogEntity = blogsRepository.findById(id);
        BlogDto blogDto = new BlogDto();
        if (blogEntity.isPresent()) {
            blogsRepository.deleteById(id);
            blogDto = blogEntityToDto(blogEntity.get());
        } else {
            log.info("can't delete");
        }
        return ResponseEntity.ok(blogDto);
    }

    @Override
    public BlogEntity updateBlogById(Long id, BlogDto blogDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username= ((UserDetails)principal).getUsername();
        Long userID = userRepository.findAllByUserEmailEquals(username).getUserID();
        Optional<BlogEntity> blogEntity = blogsRepository.findById(id);
        if (blogEntity.isPresent()) {
           if(blogEntity.get().getUserEntity().getUserID() == userID){
            blogEntity.get().setBlogTitle(blogDto.getTitle());
            blogEntity.get().setBlogImage(blogDto.getBlogImage());
            blogEntity.get().setBlogContain(blogDto.getBlogContain());
            blogEntity.get().setBlogShort(blogDto.getBlogShort());
            log.info(blogEntity.get());
            blogsRepository.save(blogEntity.get());
           }
        }

        return blogEntity.get();
    }

    @Override
    public BlogDto showBlogById(Long id) {


        Optional<BlogEntity> blogEntity = blogsRepository.findById(id);

        BlogDto blogDto;
        if (blogEntity.isPresent()) {

             blogDto = blogEntityToDto(blogEntity.get());

        } else {
            log.info("Blog bulunamadı");
            blogDto= null;
        }
        return blogDto;
    }

    @Override
    public List<BlogDto> IndexBlog() {
        List<BlogEntity> blogEntity = blogsRepository.findAll();
        List<BlogDto> blogDtos = new ArrayList<>();

        for (int i = 0; i < blogEntity.size(); i++) {
            blogDtos.add(blogEntityToDto(blogEntity.get(i)));
        }
        return blogDtos;
    }

    @Override
    public boolean userControl(Long id) {
        boolean isEq=false ;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        Long userID = userRepository.findAllByUserEmailEquals(username).getUserID();
        Optional<BlogEntity> blogEntity = blogsRepository.findById(id);
        if(blogEntity.isPresent()){
            if(blogEntity.get().getUserEntity().getUserID()==userID){
               isEq = true;
            }else{
                isEq = false;
            }
        }
        return isEq;
    }

    @Override
    public List<BlogEntity> listAllBlog() {
       List<BlogEntity> blogEntities =  blogsRepository.findAll();

       return blogEntities;
    }


}

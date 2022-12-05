package com.alpersurekci.blogprojectwspring.business.services.impl;

import com.alpersurekci.blogprojectwspring.business.dto.BlogDto;
import com.alpersurekci.blogprojectwspring.business.dto.UserDto;
import com.alpersurekci.blogprojectwspring.business.services.IBlogServices;
import com.alpersurekci.blogprojectwspring.data.entity.BlogEntity;
import com.alpersurekci.blogprojectwspring.data.entity.Role;
import com.alpersurekci.blogprojectwspring.data.entity.UserEntity;
import com.alpersurekci.blogprojectwspring.data.repository.IBlogsRepository;
import com.alpersurekci.blogprojectwspring.data.repository.IRoleRepository;
import com.alpersurekci.blogprojectwspring.data.repository.IUserRepository;
import com.alpersurekci.blogprojectwspring.mapper.PostMapper;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Role role = iRoleRepository.findByName("USER");
        userEntity.addRole(role);

        iRoleRepository.save(role);
        userRepository.save(userEntity);
    }

    @Override
    public BlogEntity saveBlog(BlogDto blogDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        UserEntity userEntity = userRepository.findAllByUserEmailEquals(username);
        BlogEntity blogEntity = PostMapper.mapToBlogEntity(blogDto);

        blogEntity.setWrittenBy(username);
        blogEntity.setUserEntity(userEntity);
        return blogsRepository.save(blogEntity);


    }


    @Override
    public List<BlogDto> findAllBlogsById() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        UserEntity userEntity = userRepository.findAllByUserEmailEquals(username);
        Long id = userEntity.getUserID();
        List<BlogEntity> blogEntities = blogsRepository.findAllByUserEntity(id);
        return blogEntities.stream().map(PostMapper::mapToBlogDto).collect(Collectors.toList());


    }

    @Override
    public ResponseEntity<BlogDto> deleteBlogById(Long id) {
        Optional<BlogEntity> blogEntity = blogsRepository.findById(id);
        BlogDto blogDto = new BlogDto();
        if (blogEntity.isPresent()) {
            blogsRepository.deleteById(id);
            blogDto = PostMapper.mapToBlogDto(blogEntity.get());
        } else {
            log.info("can't delete");
        }
        return ResponseEntity.ok(blogDto);
    }

    @Override
    public BlogEntity updateBlogById(Long id, BlogDto blogDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        Long userID = userRepository.findAllByUserEmailEquals(username).getUserID();
        Optional<BlogEntity> blogEntity = blogsRepository.findById(id);
        if (blogEntity.isPresent()) {
            if (blogEntity.get().getUserEntity().getUserID() == userID || ((UserDetails) principal).getAuthorities().toString() == "ADMIN") {

                blogEntity.get().setBlogTitle(blogDto.getTitle());
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

            blogDto = PostMapper.mapToBlogDto(blogEntity.get());

        } else {
            log.info("Blog bulunamadı");
            blogDto = null;
        }
        return blogDto;
    }

    @Override
    public List<BlogDto> IndexBlog() {
        List<BlogEntity> blogEntity = blogsRepository.findAll();


        return blogEntity.stream().map(PostMapper::mapToBlogDto).collect(Collectors.toList());
    }

    @Override
    public boolean userControl(Long id) {
        boolean isEq = false;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        Collection<SimpleGrantedAuthority> list = (Collection<SimpleGrantedAuthority>) ((UserDetails) principal).getAuthorities();
        String auth = list.iterator().next().toString();
        Long userID = userRepository.findAllByUserEmailEquals(username).getUserID();
        Optional<BlogEntity> blogEntity = blogsRepository.findById(id);

        isEq = blogEntity.get().getUserEntity().getUserID() == userID || auth.equals("ADMIN");

        return isEq;
    }

    @Override
    public List<BlogDto> listAllBlog() {
        List<BlogEntity> blogEntities = blogsRepository.findAll();
        return blogEntities.stream().map(PostMapper::mapToBlogDto).collect(Collectors.toList());

    }


}

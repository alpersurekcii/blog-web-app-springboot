package com.alpersurekci.blogprojectwspring.business.services;

import com.alpersurekci.blogprojectwspring.business.dto.CustomUserDetails;
import com.alpersurekci.blogprojectwspring.data.entity.UserEntity;
import com.alpersurekci.blogprojectwspring.data.repository.IUserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Log4j2
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IBlogServices blogServices;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findAllByUserEmailEquals(username);
        if (username == null) {
            log.info("kullanıcı bulunamadı");
        }
        return new CustomUserDetails(userEntity);
    }
}

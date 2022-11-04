package com.alpersurekci.blogprojectwspring.data.repository;

import com.alpersurekci.blogprojectwspring.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findAllByUserEmailEquals(String email);

}

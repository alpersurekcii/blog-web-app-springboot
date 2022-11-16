package com.alpersurekci.blogprojectwspring.data.repository;

import com.alpersurekci.blogprojectwspring.data.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IRoleRepository extends JpaRepository<Role, Long> {

    @Query("select r from Role as r where r.name=?1")
    public Role findByName(String name);

}

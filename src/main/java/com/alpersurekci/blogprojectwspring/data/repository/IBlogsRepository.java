package com.alpersurekci.blogprojectwspring.data.repository;

import com.alpersurekci.blogprojectwspring.data.entity.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBlogsRepository extends JpaRepository<BlogEntity, Long> {

    @Query(value = "Select * From blogs_table as blogs where blogs.user_entity_userid=?1 ", nativeQuery = true)
    List<BlogEntity> findAllByUserEntity(Long id);

    @Query( value = "Select blogID, blog_title, blog_contain,blog_image,blog_short,written_by From blogs_table", nativeQuery = true)
    List<BlogEntity> findIndexBlog();
}

package com.alpersurekci.blogprojectwspring.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "blogs_table")
public class BlogEntity {

    @Id
    @Column(name = "blogID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long blogID;

    @Column(name="blog_title")
    @NotBlank
    private String blogTitle;

    @Lob
    @Column(name="blog_contain", length = 4000)
    @NotBlank
    private String blogContain;


    @Column(name = "blog_image")
    @NotBlank
    private String blogImage;

    @ManyToOne
    private UserEntity userEntity;

    @Lob
    @Column(name="blog_short")
    private String blogShort;

    @Column(name="written_by")
    private String writtenBy;
/*
    @Transient
    public String getPhotosImagePath() {
        if (blogImage == null || blogID == null) return null;

        return "/blogs-photos/" + blogID + "/" + blogImage;
    }*/
}

package com.alpersurekci.blogprojectwspring.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "user_table")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userID")
    private Long userID;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_surname")
    private String userSurname;

    @Column(name = "user_email" )
    private String userEmail;

    @Lob
    @Column(name="user_password")
    private String userPassword;


    @OneToMany(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name="userID")
    private List<BlogEntity> blogEntityList = new ArrayList<>();


}

package com.alpersurekci.blogprojectwspring.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "user_table")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userID")
    private Long userID;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_surname")
    private String userSurname;

    @Column(name = "user_email")
    private String userEmail;

    @Lob
    @Column(name = "user_password")
    private String userPassword;


    @OneToMany(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "userID")
    private List<BlogEntity> blogEntityList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role) {
        this.roles.add(role);
    }

}


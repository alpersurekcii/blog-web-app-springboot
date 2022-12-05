package com.alpersurekci.blogprojectwspring.business.dto;

import com.alpersurekci.blogprojectwspring.data.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long userId;

    @NotEmpty(message = " Email can not be empty")
    private String userEmail;

    @NotEmpty(message = " Name can not be empty")
    private String userName;

    @NotEmpty(message = " Surname can not be empty")
    private String userSurname;

    @NotEmpty(message = " Password can not be empty")
    private String userPassword;

    private Role role;


}

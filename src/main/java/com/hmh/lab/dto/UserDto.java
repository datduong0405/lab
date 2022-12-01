package com.hmh.lab.dto;

import com.hmh.lab.entity.Laboratory;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private String username;
    private String password;
    private String lastName;
    private String firstName;
    private String email;
    private String phone;
    private String department;
    private String status;
    private List<Laboratory> laboratories;
    private String role;
}

package com.hmh.lab.service;

import com.hmh.lab.dto.LoginDto;
import com.hmh.lab.entity.User;

import java.util.Optional;

public interface UserService {
    User findByUsername(String username);
}

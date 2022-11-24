package com.hmh.lab.controller;

import com.hmh.lab.entity.User;
import com.hmh.lab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/lab/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> findUserByUsername(@PathVariable(name = "username") String username){
        return ResponseEntity.ok(userRepository.findByUsername(username));
    }
}

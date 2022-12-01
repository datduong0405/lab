package com.hmh.lab.controller;

import com.hmh.lab.dto.LoginDto;
import com.hmh.lab.entity.User;
import com.hmh.lab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lab/login")
@CrossOrigin(origins = "*")
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) throws Exception {
        User user = userRepository.findByUsername(loginDto.getUsername());
        if (user.getPassword().equals(loginDto.getPassword())) {
            return ResponseEntity.ok(user);
        }
        return null;
    }

}

package com.example.onlineshoping.controller;


import com.example.onlineshoping.Repo.UserRepository;
import com.example.onlineshoping.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    @PostMapping("/users")
    public String postUser(@RequestBody User user){
        userRepository.save(user);
        return "User added Successfully";
    }
}

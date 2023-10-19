package com.example.onlineshoping.controller;


import com.example.onlineshoping.Repo.UserRepository;
import com.example.onlineshoping.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable int id) {
        Optional<User> user= userRepository.findById(id);
        if (user.isPresent()){
            return new ResponseEntity<>(user,HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/users")
    public String postUser(@RequestBody User user) {
        userRepository.save(user);
        return "User added Successfully";
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        userRepository.save(user);
        return ResponseEntity.ok("User Updated successfully " + user);
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity deleteUser(@PathVariable int id) {
       Optional<User> user=userRepository.findById(id);
        if (user.isPresent()){
            userRepository.deleteById(id);
        }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not present");

    }
}

package com.example.onlineshoping.controller;


import com.example.onlineshoping.Repo.UserRepository;
import com.example.onlineshoping.entity.User;
import com.example.onlineshoping.exception.ApiResponse;
import com.example.onlineshoping.exception.ResourceNotFoundException;

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

<<<<<<< HEAD
=======
    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable int id) {
        Optional<User> user= userRepository.findById(id);
        if(user.isEmpty()) {
        	throw new ResourceNotFoundException("user","id:", id, "1001");
        }
        ApiResponse apiResponse = new ApiResponse();
        UserWrapper userWrapper = new UserWrapper();
        userWrapper.setUser(user.get());
        apiResponse.setData(userWrapper);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

>>>>>>> branch 'master' of https://github.com/Himanshu-sharma73/shoping.git
    @PostMapping("/users")
    public String postUser(@RequestBody User user){
        userRepository.save(user);
        return "User added Successfully";
    }
}


class UserWrapper{
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}

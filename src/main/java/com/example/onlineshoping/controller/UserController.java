package com.example.onlineshoping.controller;

import com.example.onlineshoping.Repo.UserRepository;
import com.example.onlineshoping.entity.User;
import com.example.onlineshoping.exception.ApiResponse;
import com.example.onlineshoping.exception.ResourceNotFoundException;


import com.example.onlineshoping.wrapperclasses.UserWrapper;
import com.example.onlineshoping.wrapperclasses.UsersListWrapper;
import jakarta.validation.Valid;
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
    public ResponseEntity<ApiResponse> getAllUser() {
        List<User> user= userRepository.findAll();
        ApiResponse apiResponse = new ApiResponse();
        UsersListWrapper userWrapper = new UsersListWrapper();
        userWrapper.setUsersList(user);
        apiResponse.setData(userWrapper);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

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

    @PostMapping("/users")
    public ResponseEntity<ApiResponse> postUser(@Valid @RequestBody User user) {
       User user1= userRepository.save(user);
       ApiResponse apiResponse=new ApiResponse();
       UserWrapper userWrapper=new UserWrapper();
       userWrapper.setUser(user1);
       apiResponse.setData(userWrapper);
       return  new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.CREATED);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<ApiResponse> updateUser(@Valid @RequestBody User user, @PathVariable  int id) {
      Optional<User> optionalUser=userRepository.findById(id);
      if (optionalUser.isPresent()){
          User newUser=optionalUser.get();
          if(user.getName()!=null){
              newUser.setName(user.getName());
          }
          if(user.getMobileNo()!=0){
              newUser.setMobileNo(user.getMobileNo());
          }
          if (user.getAddress()!=null){
              newUser.setAddress(user.getAddress());
          }
          User user1= userRepository.save(newUser);
          ApiResponse apiResponse=new ApiResponse();
          UserWrapper userWrapper=new UserWrapper();
          userWrapper.setUser(user1);
          apiResponse.setData(userWrapper);
          return  new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);

      }
        else {
            throw  new ResourceNotFoundException("user","id:", id, "1001");
      }
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
       Optional<User> user=userRepository.findById(id);
        if (user.isPresent()){
            return new ResponseEntity<>("User deleted Successfully",HttpStatus.OK);
        }
        else {
            throw  new ResourceNotFoundException("user","id:", id, "1001");

        }
    }
}




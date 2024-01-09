package com.example.onlineshoping.controller;


import com.example.onlineshoping.dto.UserDto;
import com.example.onlineshoping.entity.ERole;
import com.example.onlineshoping.entity.Role;
import com.example.onlineshoping.repo.UserRepository;
import com.example.onlineshoping.entity.User;
import com.example.onlineshoping.responce.ApiResponse;
import com.example.onlineshoping.exception.ResourceNotFoundException;


import com.example.onlineshoping.wrapperclasses.UserWrapper;
import com.example.onlineshoping.wrapperclasses.UsersListWrapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("http://localhost:4200,http://localhost:4401")
@PreAuthorize("hasRole('USER') || hasRole('ADMIN') ")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse> getAllUser() {
        List<User> userList = userRepository.findAll();
        List<UserDto> userDtoList = userList.stream()
                .map(user -> {
                    UserDto userDto = new UserDto();
                    BeanUtils.copyProperties(user, userDto);
                    List<String> roleNames  = user.getRoles().stream()
                            .map(Role::getName)
                            .map(ERole::name)
                            .collect(Collectors.toList());
                    userDto.setRoles(roleNames);
                    return userDto;
                })
                .collect(Collectors.toList());
        ApiResponse apiResponse = new ApiResponse();
        UsersListWrapper userWrapper = new UsersListWrapper();
        userWrapper.setUsersList(userDtoList);
        apiResponse.setData(userWrapper);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable int id) {
        Optional<User> optionalUser= userRepository.findById(id);
        if(optionalUser.isEmpty()) {
        	throw new ResourceNotFoundException("user","id:", id, "1001");
        }
        User user=optionalUser.get();
        UserDto userDto=new UserDto();
        BeanUtils.copyProperties(user,userDto);
        List<Role> rolesSet = user.getRoles();
        List<String> roleNamesList = rolesSet.stream()
                .map(Role::getName)
                .map(ERole::name)
                .collect(Collectors.toList());

        userDto.setRoles(roleNamesList);
        ApiResponse apiResponse = new ApiResponse();
        UserWrapper userWrapper = new UserWrapper();
        userWrapper.setUser(userDto);
        apiResponse.setData(userWrapper);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable  int id) {
      Optional<User> optionalUser=userRepository.findById(id);
      if (optionalUser.isPresent()){
          User newUser=optionalUser.get();
          if(userDto.getName()!=null){
              newUser.setName(userDto.getName());
          }
          if(userDto.getMobileNo()!=0){
              newUser.setMobileNo(userDto.getMobileNo());
          }
          if(userDto.getEmail()!=null){
              newUser.setEmail(userDto.getEmail());
          }
          if (userDto.getAddress()!=null){
              newUser.setAddress(userDto.getAddress());
          }
          User savedUser= userRepository.save(newUser);
          BeanUtils.copyProperties(savedUser,userDto);
          ApiResponse apiResponse=new ApiResponse();
          UserWrapper userWrapper=new UserWrapper();
          userWrapper.setUser(userDto);
          apiResponse.setData(userWrapper);
          return  new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);

      }
        else {
            throw  new ResourceNotFoundException("user","id:", id, "1001");
      }
    }

    @DeleteMapping("users/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Object> deleteUser(@PathVariable int id) {
       Optional<User> user=userRepository.findById(id);
        if (user.isPresent()){
            userRepository.deleteById(id);
            return ResponseEntity.ok().body(Map.of("message", "User deleted Successfully"));
        }
        else {
            throw  new ResourceNotFoundException("user","id:", id, "1001");

        }
    }

}





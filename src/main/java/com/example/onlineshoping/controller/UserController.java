package com.example.onlineshoping.controller;


import com.example.onlineshoping.entity.Cart;
import com.example.onlineshoping.entity.Item;
import com.example.onlineshoping.repo.CartRepository;
import com.example.onlineshoping.repo.ItemRepository;
import com.example.onlineshoping.repo.UserRepository;
import com.example.onlineshoping.entity.User;
import com.example.onlineshoping.exception.ApiResponse;
import com.example.onlineshoping.exception.ResourceNotFoundException;


import com.example.onlineshoping.wrapperclasses.UserWrapper;
import com.example.onlineshoping.wrapperclasses.UsersListWrapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin("http://localhost:4200,http://localhost:4401")

public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ItemRepository itemRepository;

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
       HttpHeaders headers = new HttpHeaders();
       headers.add("Content-Type", "application/json");
       ApiResponse apiResponse=new ApiResponse();
       UserWrapper userWrapper=new UserWrapper();
       userWrapper.setUser(user1);
       apiResponse.setData(userWrapper);
       return  new ResponseEntity<ApiResponse>(apiResponse,headers,HttpStatus.OK);
    }

    @CrossOrigin("http://localhost:4200")
    @PutMapping("/users/{id}")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody User user, @PathVariable  int id) {
      Optional<User> optionalUser=userRepository.findById(id);
      if (optionalUser.isPresent()){
          User newUser=optionalUser.get();
          if(user.getName()!=null){
              newUser.setName(user.getName());
          }
          if(user.getMobileNo()!=0){
              newUser.setMobileNo(user.getMobileNo());
          }
          if(user.getEmail()!=null){
              newUser.setEmail(user.getEmail());
          }
          if (!StringUtils.isEmpty(user.getAddress())){
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
    public ResponseEntity<Object> deleteUser(@PathVariable int id) {
       Optional<User> user=userRepository.findById(id);
        if (user.isPresent()){
            User user1=user.get();
           Optional<Cart> optionalCart=cartRepository.findByUserId(id);
           if(optionalCart.isPresent()){
               int  cartId= optionalCart.get().getId();
               List<Item> items = itemRepository.findByCartId(cartId);
               if (!items.isEmpty()) {
                   for (Item item : items) {
                       int itemId = item.getId();
                       itemRepository.deleteById(itemId);
                   }
               }
               cartRepository.deleteById(cartId);
           }
            userRepository.deleteById(id);
            return ResponseEntity.ok().body(Map.of("message", "User deleted Successfully"));
        }
        else {
            throw  new ResourceNotFoundException("user","id:", id, "1001");

        }
    }

    @PostMapping("/users/pass")
    public Optional<User> getByPassword(@RequestBody User user){
        Optional<User> user1=userRepository.findById(user.getId());
        if(user1.isPresent()) {
            if (user.getPassword().equals(user1.get().getPassword())) {
                return user1;
            } else {
               throw new ResourceNotFoundException("Users","password",100,"1006");
            }
        }
        throw new ResourceNotFoundException("User","id:", user.getId(), "1001");
    }

}





package com.example.onlineshoping.controller;


import com.example.onlineshoping.Repo.CartRepository;
import com.example.onlineshoping.Repo.ItemRepository;
import com.example.onlineshoping.Repo.ProductRepository;
import com.example.onlineshoping.Repo.UserRepository;
import com.example.onlineshoping.entity.Cart;
import com.example.onlineshoping.entity.Item;
import com.example.onlineshoping.entity.Product;
import com.example.onlineshoping.entity.User;
import com.example.onlineshoping.exception.ApiResponse;
import com.example.onlineshoping.exception.ResourceNotFoundException;
import com.example.onlineshoping.wrapperclasses.ProductWrapper;
import com.example.onlineshoping.wrapperclasses.UserWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class CartController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemRepository itemRepository;


    @PostMapping("/addtocart/{userId}/product/{productId}")
    public ResponseEntity<ApiResponse> addToCart(@PathVariable int userId, @PathVariable int productId) {
        Optional<User> optionalUser=userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            Optional<Cart> optional = cartRepository.findByUserId(userId);
            if (optional.isPresent()) {
                Cart cart = optional.get();
                Optional<Product> optionalProduct = productRepository.findById(productId);
                if(optionalProduct.isEmpty()){
                  throw new ResourceNotFoundException("Product", "Id:", productId, "1002");
                }
                Product product=optionalProduct.get();
                Item item = new Item( product, cart);
                itemRepository.save(item);
                ApiResponse apiResponse = new ApiResponse();
                ProductWrapper productWrapper = new ProductWrapper();
                productWrapper.setProduct(product);
                apiResponse.setData(productWrapper);
                return new ResponseEntity<ApiResponse> (apiResponse,HttpStatus.OK);
            } else {
                Cart cart1 = new Cart();
                User user = userRepository.findById(userId).get();
                cart1.setUser(user);
                cartRepository.save(cart1);
                Optional<Product>  optionalProduct = productRepository.findById(productId);
                if(optionalProduct.isEmpty()){
                    throw new ResourceNotFoundException("Product", "Id:", productId, "1002");
                  }
                Product product=optionalProduct.get();
                Item item = new Item(product, cart1);
                itemRepository.save(item);
                ApiResponse apiResponse = new ApiResponse();
                ProductWrapper productWrapper = new ProductWrapper();
                productWrapper.setProduct(product);
                apiResponse.setData(productWrapper);
                return new ResponseEntity<ApiResponse> (apiResponse,HttpStatus.OK);
            }
        }
        else {
        	throw new ResourceNotFoundException("User", "Id", userId, "1001");
        }
        
    }

    @GetMapping("/users/{userId}/cart")
    public List<Product> getCart(@PathVariable int userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Cart> optionalCart = cartRepository.findByUserId(user.getId());
            if (optionalCart.isPresent()) {
                Cart cart = optionalCart.get();
                List<Item> cartItems = itemRepository.findByCartId(cart.getId());

            }
        }
        return null;
    }


}



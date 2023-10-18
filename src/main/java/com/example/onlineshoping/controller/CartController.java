package com.example.onlineshoping.controller;


import com.example.onlineshoping.Repo.CartRepository;
import com.example.onlineshoping.Repo.ItemRepository;
import com.example.onlineshoping.Repo.ProductRepository;
import com.example.onlineshoping.Repo.UserRepository;
import com.example.onlineshoping.entity.Cart;
import com.example.onlineshoping.entity.Item;
import com.example.onlineshoping.entity.Product;
import com.example.onlineshoping.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

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
    public ResponseEntity<String> addToCart(@PathVariable int userId, @PathVariable int productId){
        Optional<Cart> optional = cartRepository.findByUserId(userId);
        if(optional.isPresent()){
            Cart cart = optional.get();
            Product product=productRepository.findById(productId).get();
            Item item=new Item(product,cart);
            itemRepository.save(item);
            return ResponseEntity.ok("Product added to cart successfully");
        }
        else {
            Cart cart1=new Cart();
            User user=userRepository.findById(userId).get();
            cart1.setUser(user);
                cartRepository.save(cart1);
            Product product=productRepository.findById(productId).get();
            Item item=new Item(product,cart1);
            itemRepository.save(item);
            return ResponseEntity.ok("Product added to cart successfully");
        }

    }

}



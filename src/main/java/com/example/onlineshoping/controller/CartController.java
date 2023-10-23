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
    public ResponseEntity<String> addToCart(@PathVariable int userId, @PathVariable int productId) {
        Optional<User> optionalUser=userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            Optional<Cart> optional = cartRepository.findByUserId(userId);
            if (optional.isPresent()) {
                Cart cart = optional.get();
                Optional<Product> optionalProduct = productRepository.findById(productId);
                if(optionalProduct.isEmpty()){
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product is not Present");
                }
                Product product=optionalProduct.get();
                Item item = new Item( product, cart);
                itemRepository.save(item);
                return ResponseEntity.ok("Product added to cart successfully");
            } else {
                Cart cart1 = new Cart();
                User user = userRepository.findById(userId).get();
                cart1.setUser(user);
                cartRepository.save(cart1);
                Product product = productRepository.findById(productId).get();
                Item item = new Item(product, cart1);
                itemRepository.save(item);
                return ResponseEntity.ok("Product added to cart successfully");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not present First Create the User");

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



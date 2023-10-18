package com.example.onlineshoping.controller;


import com.example.onlineshoping.Repo.CartRepository;
import com.example.onlineshoping.Repo.ProductRepository;
import com.example.onlineshoping.Repo.UserRepository;
import com.example.onlineshoping.entity.Cart;
import com.example.onlineshoping.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepository;


    @GetMapping("/products")
    public List<Product> getProductDetails() {
        return productRepository.findAll();
    }

    @GetMapping("/products/{id}")
    public Optional<Product> getProductDetailsById(@PathVariable int id) {
        return productRepository.findById(id);
    }

    @PostMapping("/products")
    public String postProduct(@RequestBody Product product) {
        productRepository.save(product);
        return "product posted successfully";
    }

    @DeleteMapping("product/{id}")
    public String deleteProduct(@PathVariable int id) {
        productRepository.deleteById(id);
        return "product deleted successfully id is" + id;
    }

    @PutMapping("/product/{id}")
    public String updateProduct(@PathVariable int id, @RequestBody Product product) {
        productRepository.save(product);
        return "update successfully";
    }

}

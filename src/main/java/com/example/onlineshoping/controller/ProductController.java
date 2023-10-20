package com.example.onlineshoping.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.onlineshoping.Repo.ProductRepository;
import com.example.onlineshoping.entity.Product;

import jakarta.validation.Valid;

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
    public String postProduct(@Valid @RequestBody Product product) {
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

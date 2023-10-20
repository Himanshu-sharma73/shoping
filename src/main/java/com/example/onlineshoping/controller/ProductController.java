package com.example.onlineshoping.controller;


import com.example.onlineshoping.Repo.CartRepository;
import com.example.onlineshoping.Repo.ProductRepository;
import com.example.onlineshoping.Repo.UserRepository;
import com.example.onlineshoping.entity.Cart;
import com.example.onlineshoping.entity.Product;
import com.example.onlineshoping.exception.ApiResponse;
import com.example.onlineshoping.exception.ResourceNotFoundException;
import com.example.onlineshoping.wrapperclasses.ProductWrapper;
import com.example.onlineshoping.wrapperclasses.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepository;


    @GetMapping("/products")
    public List<Product> getProductDetails() {
        List<Product> products = productRepository.findAll();
        return products;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ApiResponse> getProductDetailsById(@PathVariable int id) {
        Optional<Product> products =  productRepository.findById(id);
       if(products.isEmpty()){
           throw new ResourceNotFoundException("Product","id",id,"1002");
       }
        ApiResponse apiResponse = new ApiResponse();
        ProductWrapper productWrapper=new ProductWrapper();
        productWrapper.setProduct(products.get());
        apiResponse.setData(productWrapper);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/products")
    public String postProduct(@RequestBody Product product) {
        productRepository.save(product);
        return "product posted successfully";
    }

    @DeleteMapping("products/{id}")
    public ResponseEntity deleteProduct(@PathVariable int id) {
        Optional<Product> product=productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.deleteById(id);
            return ResponseEntity.ok("Product deleted successfully id is "+id);
        }return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product is not present");
    }

    @PutMapping("/products/{id}")
    public String updateProduct(@RequestBody Product product) {
        productRepository.save(product);
        return "update successfully";
    }

}

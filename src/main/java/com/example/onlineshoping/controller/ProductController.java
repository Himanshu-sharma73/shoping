package com.example.onlineshoping.controller;


import com.example.onlineshoping.Repo.ProductRepository;
import com.example.onlineshoping.entity.Product;
import com.example.onlineshoping.exception.ApiResponse;
import com.example.onlineshoping.exception.ResourceNotFoundException;
import com.example.onlineshoping.wrapperclasses.ProductWrapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String postProduct(@Valid @RequestBody Product product) {
        productRepository.save(product);
        return "product posted successfully";
    }

    @DeleteMapping("products/{id}")
    public ResponseEntity deleteProduct(@PathVariable int id) {
        Optional<Product> product=productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.deleteById(id);
            return ResponseEntity.ok("Product deleted successfully id:"+id);
        }else {
            throw  new ResourceNotFoundException("Product","id:", id, "1001");
        }
    }

    @PutMapping("/products/{id}")
    public Product updateProduct(@Valid@RequestBody Product product,@PathVariable Integer id) {
        Optional<Product> optionalProduct=productRepository.findById(id);
        if(optionalProduct.isPresent()){
            Product product1=optionalProduct.get();
            if (product.getName()!=null){
                product1.setName(product.getName());
            }
            if (product.getDescription()!=null){
                product1.setDescription(product.getDescription());
            }
            if (product.getMrp()!=0){
                product1.setMrp(product.getMrp());
            }
            if(product.getDiscountPercentage()!=0){
                product1.setDiscountPercentage(product.getDiscountPercentage());
            }
            if (product.getTax()!=0){
                product1.setTax(product.getTax());
            }
            if (product.getExpDate()!=null){
                product1.setExpDate(product.getExpDate());
            }
            if (product.getMfgDate()!=null){
                product1.setMfgDate(product.getMfgDate());
            }
            if(product.getQuantity()!=0){
                product1.setQuantity(product.getQuantity());
            }
            productRepository.save(product1);
            return product1;
        }
        else {
            throw new ResourceNotFoundException("Product", "id", id, "1002");
        }

    }

}

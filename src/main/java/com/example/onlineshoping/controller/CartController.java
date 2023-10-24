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
import com.example.onlineshoping.wrapperclasses.ProductListWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
            if (optionalCart.isPresent()) {
                Cart cart = optionalCart.get();
                Optional<Product> optionalProduct = productRepository.findById(productId);
                if(optionalProduct.isEmpty()){
                    throw  new ResourceNotFoundException("Product","id:", productId,"1002");
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
       throw new ResourceNotFoundException("User","id:",userId,"1001");

    }

    @GetMapping("/users/{userId}/cart")
    public ResponseEntity<ApiResponse> getCart(@PathVariable int userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Cart> optionalCart = cartRepository.findByUserId(user.getId());
            Cart cart;
            if (optionalCart.isPresent()) {
                cart = optionalCart.get();
                List<Item> cartItems = itemRepository.findByCartId(cart.getId());
                List<Product> products = cartItems.stream().map(i -> i.getProduct()).collect(Collectors.toList());


                double totalMRP=0;
                int totalDiscountPercentage=0;
                long totalTax=0;
                double totalCost=0;
                for (int i=0;i<products.size();i++){
                    Product product=products.get(i);
                    totalMRP+=product.getMrp();
                    totalDiscountPercentage+=product.getDiscountPercentage();
                    totalTax+=product.getTax();
                }

                totalCost=((totalMRP+(totalMRP*totalTax/100))*(100-totalDiscountPercentage))/100;

                ProductListWrapper productListWrapper=new ProductListWrapper();
                productListWrapper.setTotalMRP(totalMRP);
                productListWrapper.setTotalDiscountPercentage(totalDiscountPercentage);
                productListWrapper.setTotalTax(totalTax);
                productListWrapper.setTotalCost(totalCost);

                ApiResponse apiResponse=new ApiResponse();
                productListWrapper.setProductList(products);
                apiResponse.setData(productListWrapper);
                return  new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
            }
            else {
                ApiResponse apiResponse=new ApiResponse();
                ProductListWrapper productListWrapper=new ProductListWrapper();
                productListWrapper.setProductList(null);
                apiResponse.setData(productListWrapper);
                return  new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
            }
        }
        else {
            throw new ResourceNotFoundException("User","Id:",userId,"1001");
        }

    }


}



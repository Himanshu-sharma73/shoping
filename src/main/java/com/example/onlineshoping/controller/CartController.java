package com.example.onlineshoping.controller;


import com.example.onlineshoping.entity.Cart;
import com.example.onlineshoping.entity.Item;
import com.example.onlineshoping.entity.Product;
import com.example.onlineshoping.entity.User;
import com.example.onlineshoping.responce.ApiResponse;
import com.example.onlineshoping.exception.ResourceNotFoundException;
import com.example.onlineshoping.repo.CartRepository;
import com.example.onlineshoping.repo.ItemRepository;
import com.example.onlineshoping.repo.ProductRepository;
import com.example.onlineshoping.repo.UserRepository;
import com.example.onlineshoping.wrapperclasses.ProductDetails;
import com.example.onlineshoping.wrapperclasses.ProductListWrapper;
import com.example.onlineshoping.wrapperclasses.ProductWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("http://localhost:4200,http://localhost:4401")
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
    public ResponseEntity<ApiResponse> addToCart(@PathVariable int userId, @PathVariable int productId, @RequestBody Map<String, Integer> requestBody) {
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
                int quantity = requestBody.get("quantity");
                Item item = new Item( product, cart,quantity);
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
                int quantity = requestBody.get("quantity");
                Item item = new Item(product, cart1,quantity);
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
    public ResponseEntity<ApiResponse> getCart(@PathVariable int userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Cart> optionalCart = cartRepository.findByUserId(user.getId());
            Cart cart;
            if (optionalCart.isPresent()) {
                cart = optionalCart.get();
                List<Item> cartItems = itemRepository.findByCartId(cart.getId());
                List<Product> products = cartItems.stream().map(Item::getProduct).collect(Collectors.toList());

                List<ProductDetails> productDetailsList = new ArrayList<>();

                double totalMRP=0;
                int totalDiscountPercentage=0;
                long totalTax=0;
                double allTotalCost=0;
                for (Item item : cartItems) {
                    Product product = item.getProduct();
                    int quantity = item.getQuantity();


                    totalMRP = product.getMrp();
                    totalDiscountPercentage = product.getDiscountPercentage();
                    totalTax = product.getTax();
                    double totalCost= quantity*(((totalMRP + (totalMRP * totalTax / 100)) * (100 - totalDiscountPercentage)) / 100);
                    totalCost = Math.round(totalCost * 100.0) / 100.0;
                    allTotalCost+=totalCost;
                    ProductDetails productDetails = new ProductDetails(product, quantity,totalCost);
                    productDetailsList.add(productDetails);
                }

                ProductListWrapper productListWrapper=new ProductListWrapper();
                productListWrapper.setProductLists(productDetailsList);
                allTotalCost = Math.round(allTotalCost * 100.0) / 100.0;
                productListWrapper.setAllTotalCost(allTotalCost);

                ApiResponse apiResponse=new ApiResponse();
                apiResponse.setData(productListWrapper);
                return  new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
            }
            else {
                ApiResponse apiResponse=new ApiResponse();
                ProductListWrapper productListWrapper=new ProductListWrapper();
               // productListWrapper.setProductList(null);
                apiResponse.setData(productListWrapper);
                return  new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
            }
        }
        else {
            throw new ResourceNotFoundException("User","Id:",userId,"1001");
        }
    }

    @GetMapping("/users/{userId}/cart/product/{productId}")
    public boolean isProductInCart(@PathVariable int userId, @PathVariable int productId) {
        Optional<Cart> optionalCart=cartRepository.findByUserId(userId);
        if (optionalCart.isPresent()) {
            List<Item> itemList=itemRepository.findByCartId(optionalCart.get().getId());
            return itemList.stream()
                    .anyMatch(item -> item.getProduct().getId() == productId);
        }
        return false;
    }

    @DeleteMapping("/users/{userId}/cart/product/{productId}")
    public ResponseEntity<Object> deleteFromCart(@PathVariable int userId, @PathVariable int productId) {
        Optional<Cart> cart = cartRepository.findByUserId(userId);
        if (cart.isPresent()) {
            List<Item> items = itemRepository.findByCartId(cart.get().getId());
            Optional<Item> itemToDelete = items.stream()
                    .filter(item -> item.getProduct().getId() == productId)
                    .findFirst();
            itemToDelete.ifPresent(item -> itemRepository.deleteById(item.getId()));

            return ResponseEntity.ok().body(Map.of("status", "success", "message", "Product removed from the cart successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("status", "error", "message", "Cart not found"));
        }
    }


}



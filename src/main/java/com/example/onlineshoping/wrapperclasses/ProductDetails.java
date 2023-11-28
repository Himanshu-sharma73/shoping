package com.example.onlineshoping.wrapperclasses;

import com.example.onlineshoping.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDetails {
    private Product product;
    private int quantity;
    private double totalCost;
}

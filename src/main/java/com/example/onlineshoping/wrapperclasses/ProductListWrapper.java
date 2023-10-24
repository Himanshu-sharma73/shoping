package com.example.onlineshoping.wrapperclasses;

import com.example.onlineshoping.entity.Product;
import lombok.Data;

import java.util.List;

@Data
public class ProductListWrapper {
    private List<Product> productList;
    private double totalMRP;
    private int totalDiscountPercentage;
    private  long totalTax;
    private double totalCost;
}

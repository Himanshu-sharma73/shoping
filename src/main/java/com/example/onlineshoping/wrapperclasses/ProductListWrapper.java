package com.example.onlineshoping.wrapperclasses;

import com.example.onlineshoping.entity.Product;
import lombok.Data;

import java.util.List;

@Data
public class ProductListWrapper {
    private List<Product> productList;
    private List<ProductDetails> productLists;
    private double allTotalCost;

}

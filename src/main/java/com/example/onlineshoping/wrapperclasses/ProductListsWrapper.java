package com.example.onlineshoping.wrapperclasses;

import lombok.Data;

import java.util.List;

@Data
public class ProductListsWrapper {

    private List<ProductDetails> productLists;
    private double allTotalCost;

}

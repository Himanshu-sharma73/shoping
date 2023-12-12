package com.example.onlineshoping.wrapperclasses;

import com.example.onlineshoping.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductListWrapper {
    private List<Product> productList;
}

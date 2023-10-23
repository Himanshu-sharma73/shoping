package com.example.onlineshoping.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Value;

import java.time.LocalDate;

@Entity
@Table(name="productdetails")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull(message = "Name is required and cannot be blank")
    @Size(min = 3,message = "User name should have at least three Character")
    private  String name;// at least 3 char

    @NotNull(message = "Description is required and cannot be blank")
    @Size(min = 10,max = 200,message = "Description should be between 10 to 200 character")
    private String description; //min 10= max 200 char

    @NotNull(message = "MRP is required and cannot be blank")
    @Positive(message = "MRP must be greater than 0")
    private double mrp; //must be > 0


    @Min(value = 0,message = "Discount percentage cannot be negative")
    @Max(value = 100,message = "Discount percentage cannot be grater than 100")
    private int discountPercentage; //min  = 0, max= 100

    @Min(value = 0, message = "Tax rate cannot be negative")
    @Max(value = 28, message = "Tax rate cannot be greater than 28")
    private  long tax;// 0-28

    @NotNull(message = "Mfg date required")
    private LocalDate mfgDate;

    @NotNull(message = "Exp date required")
    private LocalDate expDate;


    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;// at least 1

}

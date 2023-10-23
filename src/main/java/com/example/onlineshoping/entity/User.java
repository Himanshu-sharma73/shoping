package com.example.onlineshoping.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "userdetails")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull(message = "User Name should not bee null")
    @Size(min = 3,message = "User name should have at least three Character")
    private String name;

   //@Range(min = 10,max = 12,message = "Enter Correct Number")
    private long mobileNo;

    @NotNull(message = "Email should not be null")
    @Email(message = "Enter Correct Email")
    private String email;

    @NotBlank(message = "Address should not be Blank")
    @NotNull(message = "Address should not be Null")
    private String address;

}
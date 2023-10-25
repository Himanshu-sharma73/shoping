package com.example.onlineshoping.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.hibernate.validator.constraints.Range;

import java.util.Set;

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

    @Min(value = 1000000000l,message = "Mobile number must be at least 10 digit")
    @Max(value = 9999999999l,message = "Mobile number must not exceed 10 digit")
    private long mobileNo;

    @NotNull(message = "Email should not be null")
    @Email(message = "Enter Correct Email")
    private String email;

    @NotBlank(message = "Address should not be Blank")
    @NotNull(message = "Address should not be Null")
    private String address;

    private String password;


    @ManyToMany
    private Set<Role> roles;

}
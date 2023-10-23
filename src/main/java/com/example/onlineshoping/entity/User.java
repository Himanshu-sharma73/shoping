package com.example.onlineshoping.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
//import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "userdetails")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Size(min = 3, message = "User name should have at least three Character")
	private String name;

	@Min(value = 1000000000L, message = "Mobile number must be at least 10 digits.")
	@Max(value = 9999999999L, message = "Mobile number must not exceed 10 digits.")
	private long mobileNo;

	@NotBlank(message = "Email address cannot be blank")
	@Email(message = "Invalid email address")
	private String email;

	@NotBlank(message = "Address should not be Blank")
	@NotNull(message = "Address should not be Null")
	
	private String address;

}
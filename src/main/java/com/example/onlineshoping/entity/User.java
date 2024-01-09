package com.example.onlineshoping.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "userdetails")
@Builder
public class User{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

    @NotNull(message = "User Name should not bee null")
    @Size(min = 3,message = "User name should have at least three Character")
    private String name;

    @Min(value = 1000000000l,message = "Mobile number must be at least 10 digit")
    @Max(value = 9999999999l,message = "Mobile number must not exceed 10 digit")
    private long mobileNo;

	@NotBlank(message = "Email address cannot be blank")
	@Email(message = "Invalid email address")
	private String email;

	@NotBlank(message = "Address should not be Blank")
	@NotNull(message = "Address should not be Null")
	private String address;

	@NotBlank(message = "Password should not be Blank")
	@NotNull(message = "Password should not be Null")
	private String password;

	@Transient
	private  String token;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(  name = "user_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles=new ArrayList<Role>();
}
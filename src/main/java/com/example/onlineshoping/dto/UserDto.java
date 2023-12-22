package com.example.onlineshoping.dto;

import java.util.List;
import java.util.Set;

import com.example.onlineshoping.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

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
  @Size(message = "Address should have at least three character")
  private String address;

  @NotBlank(message = "Password should not be Blank")
  @NotNull(message = "Password should not be Null")
  @Size(message = "Password should have at least three character")
  private String password;

  private  String token;

  private List<String> roles;
}

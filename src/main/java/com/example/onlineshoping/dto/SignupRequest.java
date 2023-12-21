package com.example.onlineshoping.dto;

import java.util.Set;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

  private String name;

  private long mobileNo;

  private String email;

  private String address;

  private String password;

  private  String token;

  private Set<String> role;
}

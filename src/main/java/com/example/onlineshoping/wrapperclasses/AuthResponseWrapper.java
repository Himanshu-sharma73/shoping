package com.example.onlineshoping.wrapperclasses;

import com.example.onlineshoping.responce.AuthResponse;
import lombok.Data;

@Data
public class AuthResponseWrapper {
    private AuthResponse user;
}

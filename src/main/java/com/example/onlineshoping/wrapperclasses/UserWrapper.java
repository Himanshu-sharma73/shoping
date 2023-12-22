package com.example.onlineshoping.wrapperclasses;

import com.example.onlineshoping.dto.UserDto;
import com.example.onlineshoping.entity.User;
import com.example.onlineshoping.responce.AuthResponse;
import lombok.Data;

@Data
public class UserWrapper{
	private UserDto user;

}
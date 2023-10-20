package com.example.onlineshoping.exception;

import lombok.Data;

@Data
public class ShoppingCartException {
	private String code;
	private String message;
	private String description;
	public ShoppingCartException(String code, String message, String description) {
		super();
		this.code = code;
		this.message = message;
		this.description = description;
	}

	
}

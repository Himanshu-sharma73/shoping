package com.example.onlineshoping.exception;

import lombok.Data;

@Data
public class ShoppingCartException {
	private String code;
	private String message;
	private String descrption;
	public ShoppingCartException(String code, String message, String descrption) {
		super();
		this.code = code;
		this.message = message;
		this.descrption = descrption;
	}

	
}

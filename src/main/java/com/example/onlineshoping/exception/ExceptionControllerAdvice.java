package com.example.onlineshoping.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity<ApiResponse> handleBadRequest(ResourceNotFoundException e){
		String message = e.getMessage();
		ShoppingCartException cartException = new ShoppingCartException(e.errorCode, e.getMessage(), "dsdgjasdhklajdhkaFSfDKJg");
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setError(cartException);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
		
	}

}

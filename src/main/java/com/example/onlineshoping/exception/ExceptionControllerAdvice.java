package com.example.onlineshoping.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity<ApiResponse> handleBadRequest(ResourceNotFoundException e){
		ShoppingCartException cartException = new ShoppingCartException(e.getErrorCode(), e.getMessage(), "You are entering wrong details please check them");
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setError(cartException);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
		
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public final ResponseEntity<ApiResponse> wrongData(MethodArgumentNotValidException e){
		String message=e.getFieldError().getDefaultMessage();
		ShoppingCartException cartException = new ShoppingCartException("1000", message, "You are entering wrong details please check them");
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setError(cartException);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(UserExitByEmailException.class)
	public final ResponseEntity<ApiResponse> userFound(UserExitByEmailException e){
		ShoppingCartException cartException=new ShoppingCartException("1005",e.getMessage(),"This email address is already taken");
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setError(cartException);
		return  new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ApiResponse> defaultException(UserExitByEmailException e){
		ShoppingCartException cartException=new ShoppingCartException("1006",e.getMessage(),"You are entering wrong details please check them");
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setError(cartException);
		return  new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_ACCEPTABLE);
	}

}


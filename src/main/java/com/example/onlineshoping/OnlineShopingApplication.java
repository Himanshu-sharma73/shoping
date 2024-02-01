package com.example.onlineshoping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OnlineShopingApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(OnlineShopingApplication.class, args);
		System.out.println("Application Started...................................");

	}
}

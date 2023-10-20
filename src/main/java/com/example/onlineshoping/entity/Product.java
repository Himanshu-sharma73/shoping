package com.example.onlineshoping.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name = "productdetails")
public class Product {

	@Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Size(min = 3, message = "Invalid ,At least 3 char valid ")
	private String name;// at leaat 3 char
	
    @Size(min = 10,max =200, message = "Must be between 10 to 200 char valid")
	private String description; // min 10= max 200 char
    
    @Size(max = 0, message = "MRP must be greater than 0")
	private double mrp; // must be > 0
	private int discountPercentage; // min = 0, max= 100
	private long tax;// 0-28
	private LocalDate mfgDate;
	private LocalDate expDate;
	private int quantity;// at least 1

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {

		this.description = description;
	}

	public double getMrp() {
		return mrp;
	}

	public void setMrp(double mrp) {

		this.mrp = mrp;
	}

	public int getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(int discountPercentage) {

		this.discountPercentage = discountPercentage;
	}

	public long getTax() {
		return tax;
	}

	public void setTax(long tax) {

		this.tax = tax;
	}

	public LocalDate getMfgDate() {
		return mfgDate;
	}

	public void setMfgDate(LocalDate mfgDate) {
		this.mfgDate = mfgDate;
	}

	public LocalDate getExpDate() {
		return expDate;
	}

	public void setExpDate(LocalDate expDate) {
		this.expDate = expDate;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {

		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Product{" + "id=" + id + ", description='" + description + '\'' + ", mrp=" + mrp
				+ ", discountPercentage=" + discountPercentage + ", tax=" + tax + ", mfgDate=" + mfgDate + ", expDate="
				+ expDate + ", quantity=" + quantity + '}';
	}
}

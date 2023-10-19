package com.example.onlineshoping.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="productdetails")
public class Product {

    @Id
   // @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private  String name;// at leaat 3 char
    
    private String description; //min 10= max 200 char
    private double mrp; //must be > 0
    private int discountPercentage; //min  = 0, max= 100
    private  long tax;// 0-28
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
//    if (validateName(name)) {
//    	this.name =name ;	
//    	}else {
//    		throw new IllegalArgumentException("Invalid name. Name must have at least 3 characters.");
//    	}
//    }
//    
//	private boolean validateName(String name) {
//		
//		return name!=null && name.trim().length()>=3;
    	if(name!=null && name.trim().length()>=3) {
    		this.name = name;
	}else {
		throw new IllegalArgumentException(" Invaild name. Name must have at least 3 characers.");
	}
    	
    }

	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
    	  if (description != null && description.length() >= 10 && description.length() <= 200) {
              this.description = description;
          } else {
              // Throw an exception or handle the invalid input based on your application's requirements
              throw new IllegalArgumentException("Description must be between 10 and 200 characters.");
	}
    }

	public double getMrp() {
        return mrp;
    }

    public void setMrp(double mrp) {
    	if(mrp>0) {
        this.mrp = mrp;
    }else {
    	throw new IllegalArgumentException("MRP must be greater than 0");
    }
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
    	if(discountPercentage>=0 && discountPercentage<=100) {
        this.discountPercentage = discountPercentage;
    }else {
    	throw new IllegalArgumentException("discountPercentage must be between 0 to 100 ");
    }
    }

    public long getTax() {
        return tax;
    }

    public void setTax(long tax) {
    	if(tax>=0 && tax<=28) {
        this.tax = tax;
    }else {
    	throw new IllegalArgumentException(" Tax must be between 0 to 28.");
    }
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
    	if(quantity>=1) {
        this.quantity = quantity;
    }else {
    	throw new IllegalArgumentException("Quntity must be at least 1.");
    }
    }


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", mrp=" + mrp +
                ", discountPercentage=" + discountPercentage +
                ", tax=" + tax +
                ", mfgDate=" + mfgDate +
                ", expDate=" + expDate +
                ", quantity=" + quantity +
                '}';
    }
}

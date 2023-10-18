package com.example.onlineshoping.Repo;

import com.example.onlineshoping.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Integer> {
public Optional<Cart> findByUserId(Integer id);
}

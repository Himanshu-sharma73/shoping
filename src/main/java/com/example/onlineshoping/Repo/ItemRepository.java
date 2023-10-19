package com.example.onlineshoping.Repo;

import com.example.onlineshoping.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    public List<Item> findByCartId(int id);
}

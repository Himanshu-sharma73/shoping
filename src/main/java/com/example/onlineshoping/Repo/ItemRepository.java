package com.example.onlineshoping.Repo;

import com.example.onlineshoping.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository  extends JpaRepository<Item,Integer> {

}

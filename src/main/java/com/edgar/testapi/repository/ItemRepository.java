package com.edgar.testapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.edgar.testapi.entity.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByName(String name); // Find items by exact name

    @Query("SELECT i FROM Item i WHERE i.price <= :price")
    List<Item> findByPriceLessThanEqual(Double price); // Find items with price below or equal to the given price

    @Query("SELECT i FROM Item i WHERE i.price >= :price")
    List<Item> findByPriceGreaterThanEqual(Double price); // Find items with price above or equal to the given price
}

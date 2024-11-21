package com.edgar.testapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.edgar.testapi.repository.CategoryRepository;
import com.edgar.testapi.repository.ItemRepository;
import com.edgar.testapi.entity.Category;
import com.edgar.testapi.entity.Item;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // 1. GET: Retrieve all items
    @GetMapping("/")
    public List<Item> listItems() {
        return itemRepository.findAll();
    }

    // 2. GET: Retrieve a specific item by ID
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isPresent()) {
            return ResponseEntity.ok(item.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 3. POST: Add a new item
    @PostMapping("/")
    public ResponseEntity<String> addItem(@RequestBody Item item) {
        // Set default price if not provided
        if (item.getPrice() == null) {
            item.setPrice(0.0);
        }

       // Link category if categoryId is provided
       if (item.getCategory() != null && item.getCategory().getId() != null) {
            Optional<Category> category = categoryRepository.findById(item.getCategory().getId());
            if (category.isPresent()) {
                item.setCategory(category.get());
            } else {
                return ResponseEntity.badRequest().body("Invalid category ID: " + item.getCategory().getId());
            }
        }

        Item savedItem = itemRepository.save(item);

        // Add category name to response message if category is linked
        String categoryName = (savedItem.getCategory() != null) ? savedItem.getCategory().getName() : "None";

        return ResponseEntity.ok("Item saved: " + savedItem.getName() +
                                " with price: " + savedItem.getPrice() +
                                " and category: " + categoryName);
    }

    // 4. PUT: Update an existing item by ID
    @PutMapping("/{id}")
    public ResponseEntity<String> updateItem(@PathVariable Long id, @RequestBody Item newItemData) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isPresent()) {
            Item existingItem = optionalItem.get();

            // Update only if a field is provided in the request
            if (newItemData.getName() != null) {
                existingItem.setName(newItemData.getName());
            }
            if (newItemData.getPrice() != null) {
                existingItem.setPrice(newItemData.getPrice());
            }

            // Link category if categoryId is provided
            if (newItemData.getCategory() != null && newItemData.getCategory().getId() != null) {
                Optional<Category> category = categoryRepository.findById(newItemData.getCategory().getId());
                if (category.isPresent()) {
                    existingItem.setCategory(category.get());
                } else {
                    return ResponseEntity.badRequest().body("Invalid category ID: " + newItemData.getCategory().getId());
                }
            }

            Item updatedItem = itemRepository.save(existingItem);

            // Add category name to response message if category is linked
            String categoryName = (updatedItem.getCategory() != null) ? updatedItem.getCategory().getName() : "None";

            return ResponseEntity.ok("Item updated: " + updatedItem.getName() +
                                    " with price: " + updatedItem.getPrice() +
                                    " and category: " + categoryName);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. DELETE: Remove an item by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isPresent()) {
            itemRepository.deleteById(id);
            return ResponseEntity.ok("Item deleted with ID: " + id);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 6. Find items by name
    @GetMapping("/name/{name}")
    public List<Item> findByName(@PathVariable String name) {
        return itemRepository.findByName(name);
    }

    // 7. Filter items by below price
    @GetMapping("/price/below/{price}")
    public List<Item> filterByBelowPrice(@PathVariable Double price) {
        return itemRepository.findByPriceLessThanEqual(price);
    }

    // 8. Filter items by above price
    @GetMapping("/price/above/{price}")
    public List<Item> filterByAbovePrice(@PathVariable Double price) {
        return itemRepository.findByPriceGreaterThanEqual(price);
    }

    @GetMapping("/category/{categoryId}")
    public List<Item> findByCategory(@PathVariable Long categoryId) {
        return itemRepository.findAll()
                .stream()
                .filter(item -> item.getCategory() != null && item.getCategory().getId().equals(categoryId))
                .toList();
    }
}

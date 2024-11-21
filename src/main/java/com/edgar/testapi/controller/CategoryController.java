package com.edgar.testapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.edgar.testapi.repository.CategoryRepository;
import com.edgar.testapi.entity.Category;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping("/")
    public String addCategory(@RequestBody Category category) {
        categoryRepository.save(category);
        return "Category saved: " + category.getName();
    }

    @GetMapping("/")
    public List<Category> listCategories() {
        return categoryRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return categoryRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id, @RequestBody Category newCategoryData) {
      Optional<Category> optionalCategory = categoryRepository.findById(id);
      if (optionalCategory.isPresent()) {
          Category existingCategory = optionalCategory.get();
          // Update the name if provided
          if (newCategoryData.getName() != null) {
            existingCategory.setName(newCategoryData.getName());
            categoryRepository.save(existingCategory);
          }

          return ResponseEntity.ok("Category updated: " + existingCategory.getName());
      } else {
          return ResponseEntity.notFound().build();
      }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return ResponseEntity.ok("Category deleted with ID: " + id);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

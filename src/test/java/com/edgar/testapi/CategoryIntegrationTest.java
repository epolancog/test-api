package com.edgar.testapi;

import com.edgar.testapi.entity.Category;
import com.edgar.testapi.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoryIntegrationTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testCreateAndGetCategory() {
        // Create a new category
        Category category = new Category("Books");
        category = categoryRepository.save(category);

        // Retrieve the category
        Optional<Category> retrievedCategory = categoryRepository.findById(category.getId());

        assertTrue(retrievedCategory.isPresent());
        assertEquals("Books", retrievedCategory.get().getName());
    }
}

package com.edgar.testapi.controller;

import com.edgar.testapi.entity.Category;
import com.edgar.testapi.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.datasource.url=",
    "spring.datasource.driver-class-name=",
    "spring.datasource.username=",
    "spring.datasource.password="
})
class CategoryControllerTest {

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryController categoryController;

    @Test
    void testListCategories() {
        // Arrange
        Category category1 = new Category("Electronics");
        Category category2 = new Category("Books");

        List<Category> categories = Arrays.asList(category1, category2);

        // Mock the repository
        when(categoryRepository.findAll()).thenReturn(categories);

        // Act
        List<Category> response = categoryController.listCategories();

        // Assert
        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("Electronics", response.get(0).getName());
        assertEquals("Books", response.get(1).getName());
    }

    @Test
    void testGetCategoryById() {
        Category category = new Category("Electronics");
        category.setId(1L);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        ResponseEntity<Category> response = categoryController.getCategoryById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Electronics", response.getBody().getName());
    }

    @Test
    void testGetCategoryById_NotFound() {
        when(categoryRepository.findById(2L)).thenReturn(Optional.empty());

        ResponseEntity<Category> response = categoryController.getCategoryById(2L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}

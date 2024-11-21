package com.edgar.testapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edgar.testapi.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}

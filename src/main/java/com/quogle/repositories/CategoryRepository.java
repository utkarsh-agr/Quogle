package com.quogle.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quogle.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}

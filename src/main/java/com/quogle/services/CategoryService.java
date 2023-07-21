package com.quogle.services;

import java.util.List;

import com.quogle.payloads.CategoryDto;

public interface CategoryService {
	
	//create
	CategoryDto createCategory(CategoryDto categoryDto);
	
	//update
	CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
	
	//delete
	void deleteCategoryById(Integer categoryId);
	
	//getall
	List<CategoryDto> getAllCategories();
	
	//getbyId
	CategoryDto getCategoryById(Integer categoryId);
}

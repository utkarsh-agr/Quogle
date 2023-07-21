package com.quogle.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quogle.entities.Category;
import com.quogle.exceptions.ResourceNotFoundException;
import com.quogle.payloads.CategoryDto;
import com.quogle.repositories.CategoryRepository;
import com.quogle.services.CategoryService;

@Service
public class CategoryServicesImpl implements CategoryService {
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category=this.categoryRepository.save(this.modelMapper.map(categoryDto, Category.class));
		return this.modelMapper.map(category, CategoryDto.class);
		
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category category=this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		
		this.categoryRepository.save(category);
		
		return this.modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public void deleteCategoryById(Integer categoryId) {
		Category category=this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));
		this.categoryRepository.delete(category);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> list=this.categoryRepository.findAll();
		List<CategoryDto> dtoList = list.stream().map(category->this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
		return dtoList;
		
	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		Category category=this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));
		return this.modelMapper.map(category, CategoryDto.class);
	}

}

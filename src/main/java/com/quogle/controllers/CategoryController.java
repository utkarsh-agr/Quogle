package com.quogle.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quogle.payloads.ApiResponse;
import com.quogle.payloads.CategoryDto;
import com.quogle.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/apis/categories")
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;
	
	//create
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto returnedCategoryDto = this.categoryService.createCategory(categoryDto);
		
		return ResponseEntity.of(Optional.of(returnedCategoryDto));
		
		//return new ResponseEntity<ApiResponse>(new ApiResponse("Category created successfully", true),HttpStatus.CREATED);
	}
	
	//update
	@PutMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId){
		this.categoryService.updateCategory(categoryDto, categoryId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category updated successfully",true),HttpStatus.OK);
	}
	
	//deleteById
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteUserById(@PathVariable Integer categoryId){
		this.categoryService.deleteCategoryById(categoryId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted successfully",true),HttpStatus.OK);
	}
	
	//getAll
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> gellAllCategories(){
		
		List<CategoryDto> allCategories = this.categoryService.getAllCategories();
		if(allCategories.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		return ResponseEntity.of(Optional.of(allCategories));
		
	}
	
	//getById
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer categoryId){
		CategoryDto categoryDto= this.categoryService.getCategoryById(categoryId);
		
		return ResponseEntity.of(Optional.of(categoryDto));
	}

}

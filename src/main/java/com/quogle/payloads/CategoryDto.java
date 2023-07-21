package com.quogle.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CategoryDto {
	
	
	public Integer categoryId;
	
	@NotEmpty(message = "The category Title cannot be Empty")
	public String categoryTitle;
	
	@Size(min = 10, message="The category size must be more than 10 letters")
	public String categoryDescription;

}

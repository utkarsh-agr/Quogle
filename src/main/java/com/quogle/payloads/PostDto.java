package com.quogle.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

//import com.quogle.entities.Comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
	
	private String postTitle;
	
	private String postContent;
	
	private String postImageName;
	
	private Date postAddingDate;
	
	private UserDto postUser;
	
	private CategoryDto postCategory;
	
	private Set<CommentDto> comments=new HashSet<>();
	
	
	

}

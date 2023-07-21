package com.quogle.services;

import com.quogle.payloads.CommentDto;

public interface CommentService {
	
	public CommentDto createComment(CommentDto commentDto, Integer userId, Integer postId);
	
	public void deleteComment(Integer commentId);
	
	public CommentDto updateComment(CommentDto commentDto,Integer commentId);

}

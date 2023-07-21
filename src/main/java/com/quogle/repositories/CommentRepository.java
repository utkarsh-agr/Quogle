package com.quogle.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quogle.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}

package com.quogle.services;

import java.util.List;

import com.quogle.entities.User;
import com.quogle.payloads.UserDto;

public interface UserServices {
	
	UserDto registerUser(UserDto userdto);
	void deleteUserById(Integer userId);
	UserDto updateUser(UserDto userDto, Integer userId);
	UserDto getUserById(Integer userId);
	List<UserDto> getAllUsers();

}

package com.quogle.services.impl;



import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quogle.entities.Role;
import com.quogle.exceptions.ResourceNotFoundException;
import com.quogle.payloads.RoleDto;
import com.quogle.repositories.RoleRepository;
import com.quogle.services.RoleServices;

@Service
public class RoleServicesImpl implements RoleServices  {

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public RoleDto createRole(RoleDto roleDto) {
		
		Role role = this.modelMapper.map(roleDto,Role.class);
		
		Role returnedRole = this.roleRepository.save(role);
		
		RoleDto returnedRoleDto=this.modelMapper.map(returnedRole, RoleDto.class);
		
		return returnedRoleDto;
	}

	@Override
	public void deleteRole(int roleId) {
		
		Role role = this.roleRepository.findById(roleId).orElseThrow(()-> new ResourceNotFoundException("Role", "Id", roleId));
		
		this.roleRepository.delete(role);
		
	}

	@Override
	public Role getRole(int roleId) {
		
		 Role role = this.roleRepository.findById(roleId).orElseThrow(()-> new ResourceNotFoundException("Role", "Id", roleId));
		
		return role;
	}

	
	
	

}

package com.quogle.services;

import com.quogle.entities.Role;
import com.quogle.payloads.RoleDto;
//import com.quogle.payloads.RoleDto;

public interface RoleServices {
	
	public RoleDto createRole(RoleDto RoleDto);
	
	public void deleteRole(int roleId);
	
	public Role getRole(int roleId);
	
	

}

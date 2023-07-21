package com.quogle.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quogle.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}

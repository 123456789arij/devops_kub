package com.commerceBeauty.commerce.DAO;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.commerceBeauty.commerce.entities.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, String> {
    
}


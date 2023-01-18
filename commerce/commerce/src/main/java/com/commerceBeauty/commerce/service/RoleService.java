package com.commerceBeauty.commerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commerceBeauty.commerce.DAO.RoleRepository;
import com.commerceBeauty.commerce.entities.Role;


@Service
public class RoleService {

    @Autowired
    private RoleRepository roleDao;

    public Role createNewRole(Role role) {
        return roleDao.save(role);
    }
}

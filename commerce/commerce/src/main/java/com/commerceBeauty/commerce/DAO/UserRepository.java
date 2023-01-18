package com.commerceBeauty.commerce.DAO;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.commerceBeauty.commerce.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

}

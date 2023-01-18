package com.commerceBeauty.commerce.DAO;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.commerceBeauty.commerce.entities.Cart;
import com.commerceBeauty.commerce.entities.User;

@Repository
public interface CartRepository extends CrudRepository<Cart, Integer> {
    public List<Cart> findByUser(User user);
}

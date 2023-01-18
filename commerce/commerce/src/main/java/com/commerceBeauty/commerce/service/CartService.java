package com.commerceBeauty.commerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commerceBeauty.commerce.DAO.CartRepository;
import com.commerceBeauty.commerce.DAO.ProductRepository;
import com.commerceBeauty.commerce.DAO.UserRepository;
import com.commerceBeauty.commerce.configuration.JwtRequestFilter;
import com.commerceBeauty.commerce.entities.Cart;
import com.commerceBeauty.commerce.entities.Product;
import com.commerceBeauty.commerce.entities.User;



@Service
public class CartService {

    @Autowired
    private CartRepository cartDao;

    @Autowired
    private ProductRepository productDao;

    @Autowired
    private UserRepository userDao;

    public Cart addToCart(Integer productId) {
        Product product = productDao.findById(productId).get();
        String username = JwtRequestFilter.CURRENT_USER;

        User user = null;
        if (username != null) {
            user = userDao.findById(username).get();
        }

        if (product != null && user != null) {
            Cart cart = new Cart(product, user);
            return cartDao.save(cart);
        }

        return null;
    }

    public List<Cart> getCartDetails() {
        String username = JwtRequestFilter.CURRENT_USER;
        User user = userDao.findById(username).get();
        return cartDao.findByUser(user);
    }
}

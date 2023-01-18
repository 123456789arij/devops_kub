package com.commerceBeauty.commerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commerceBeauty.commerce.DAO.OrderDetailRepository;
import com.commerceBeauty.commerce.DAO.ProductRepository;
import com.commerceBeauty.commerce.DAO.UserRepository;
import com.commerceBeauty.commerce.configuration.JwtRequestFilter;
import com.commerceBeauty.commerce.entities.OrderDetail;
import com.commerceBeauty.commerce.entities.OrderInput;
import com.commerceBeauty.commerce.entities.OrderProductQuantity;
import com.commerceBeauty.commerce.entities.Product;
import com.commerceBeauty.commerce.entities.User;



@Service
public class OrderDetailService {

    private static final String ORDER_PLACED = "Placed";

    @Autowired
    private OrderDetailRepository orderDetailDao;

    @Autowired
    private ProductRepository productDao;

    @Autowired
    private UserRepository userDao;

    public void placeOrder(OrderInput orderInput) {
        List<OrderProductQuantity> productQuantityList = orderInput.getOrderProductQuantityList();

        for (OrderProductQuantity o : productQuantityList) {
            Product product = productDao.findById(o.getProductId()).get();

            String currentUser = JwtRequestFilter.CURRENT_USER;
            User user = userDao.findById(currentUser).get();

            OrderDetail orderDetail = new OrderDetail(
                    orderInput.getFullName(),
                    orderInput.getFullAddress(),
                    orderInput.getContactNumber(),
                    orderInput.getAlternateContactNumber(),
                    ORDER_PLACED,
                    product.getProductDiscountedPrice() * o.getQuantity(),
                    product,
                    user);

            orderDetailDao.save(orderDetail);
        }
    }
}

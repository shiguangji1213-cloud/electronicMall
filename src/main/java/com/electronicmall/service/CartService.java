package com.electronicmall.service;

import com.electronicmall.dao.CartDao;
import com.electronicmall.model.Cart;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartService {

    private CartDao cartDao = new CartDao();

    // 获取购物车列表
    public List<Cart> getCartList(int userId) {
        try {
            return cartDao.findByUserId(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // 加入购物车（已有则+1）
    public boolean addToCart(int userId, int productId) {
        try {
            Cart exist = cartDao.findByUserAndProduct(userId, productId);
            if (exist != null) {
                cartDao.increaseQuantity(exist.getCartId());
            } else {
                cartDao.add(userId, productId);
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 更新数量
    public boolean updateQuantity(int cartId, int quantity) {
        try {
            if (quantity <= 0) {
                cartDao.delete(cartId);
            } else {
                cartDao.updateQuantity(cartId, quantity);
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 删除购物车条目
    public boolean deleteItem(int cartId) {
        try {
            return cartDao.delete(cartId) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 计算总价
    public BigDecimal getTotalPrice(List<Cart> cartList) {
        BigDecimal total = BigDecimal.ZERO;
        for (Cart cart : cartList) {
            total = total.add(cart.getSubtotal());
        }
        return total;
    }
}
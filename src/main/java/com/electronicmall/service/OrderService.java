package com.electronicmall.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.electronicmall.dao.CartDao;
import com.electronicmall.dao.OrderDao;
import com.electronicmall.dao.ProductDao;
import com.electronicmall.model.Cart;
import com.electronicmall.model.Order;
import com.electronicmall.model.OrderItem;

public class OrderService {

    private OrderDao orderDao = new OrderDao();
    private CartDao cartDao = new CartDao();
    private ProductDao productDao = new ProductDao();

    // 从购物车结算，创建订单（完美融入 payType 支付仿真闭环）
    // payType: "1"=在线支付(初始状态置为1-已付款) "2"=货到付款(初始状态置为0-待付款)
    public int createOrderFromCart(int userId, String address, String payType) {
  
        try {
            // 1. 获取购物车
            List<Cart> cartList = cartDao.findByUserId(userId);
            if (cartList == null || cartList.isEmpty()) return -1;

            // 2. 计算总价
            BigDecimal total = BigDecimal.ZERO;
            for (Cart c : cartList) {
                total = total.add(c.getSubtotal());
            }

            // 3. 💡 新增核心：根据支付方式动态决定初始状态
            int initialStatus = "1".equals(payType) ? 1 : 0;

            // 4. 创建订单
            Order order = new Order();
            order.setUserId(userId);
            order.setTotalPrice(total);
            order.setAddress(address);
            order.setStatus(initialStatus); // 👈 将动态状态注入订单模型
            int orderId = orderDao.createOrder(order);

            // 5. 插入订单明细 + 减库存
            for (Cart c : cartList) {
                OrderItem item = new OrderItem();
                item.setOrderId(orderId);
                item.setProductId(c.getProductId());
                item.setQuantity(c.getQuantity());
                item.setPrice(c.getPrice());
                orderDao.addOrderItem(item);
                productDao.reduceStock(c.getProductId(), c.getQuantity());
            }

            // 6. 清空购物车
            cartDao.clearByUserId(userId);

            return orderId;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 查询我的订单列表（保持你原汁原味的空集合容错处理）
    public List<Order> getMyOrders(int userId) {
        try {
            List<Order> orders = orderDao.findByUserId(userId);
            // 每个订单加载明细
            for (Order o : orders) {
                o.setItems(orderDao.findItemsByOrderId(o.getOrderId()));
            }
            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // ✅ 完美保留：你原本给管理员后台准备的查所有订单方法，确保后台大盘不报红
    public List<Order> getAllOrders() {
        try {
            return orderDao.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // 管理员：更新订单状态
    public boolean updateStatus(int orderId, int status) {
        try {
            return orderDao.updateStatus(orderId, status) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
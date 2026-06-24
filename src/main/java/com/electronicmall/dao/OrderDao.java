package com.electronicmall.dao;

import com.electronicmall.model.Order;
import com.electronicmall.model.OrderItem;
import com.electronicmall.util.JDBCUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class OrderDao {

    private QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());

    // 创建订单，返回新自增生成的订单 ID（完美避开 BigInteger 转型 500 错误，并显式注入初始支付状态）
    public int createOrder(Order order) throws SQLException {
        // 💡 核心缝合：SQL 语句中追加了 status 字段与对应的占位符 ?
        String sql = "INSERT INTO orders(user_id, total_price, address, status) VALUES(?,?,?,?)";
        
        // 💡 核心缝合：在参数列表最后追加传入 order.getStatus()
        Number id = qr.insert(sql, new ScalarHandler<Number>(),
                order.getUserId(), order.getTotalPrice(), order.getAddress(), order.getStatus());
        
        return id.intValue();
    }

    // 插入订单明细
    public int addOrderItem(OrderItem item) throws SQLException {
        String sql = "INSERT INTO order_item(order_id, product_id, quantity, price) VALUES(?,?,?,?)";
        return qr.update(sql, item.getOrderId(), item.getProductId(),
                item.getQuantity(), item.getPrice());
    }

    // 查某用户所有订单
    public List<Order> findByUserId(int userId) throws SQLException {
        String sql = "SELECT order_id orderId, user_id userId, total_price totalPrice, " +
                     "status, address, create_time createTime " +
                     "FROM orders WHERE user_id=? ORDER BY create_time DESC";
        return qr.query(sql, new BeanListHandler<>(Order.class), userId);
    }

    // 查订单明细
    public List<OrderItem> findItemsByOrderId(int orderId) throws SQLException {
        String sql = "SELECT oi.item_id itemId, oi.order_id orderId, oi.product_id productId, " +
                     "oi.quantity, oi.price, p.product_name productName, p.img_url imgUrl " +
                     "FROM order_item oi LEFT JOIN product p ON oi.product_id=p.product_id " +
                     "WHERE oi.order_id=?";
        return qr.query(sql, new BeanListHandler<>(OrderItem.class), orderId);
    }

    // 管理员：查所有订单
    public List<Order> findAll() throws SQLException {
        String sql = "SELECT o.order_id orderId, o.user_id userId, o.total_price totalPrice, " +
                     "o.status, o.address, o.create_time createTime, u.username " +
                     "FROM orders o LEFT JOIN user u ON o.user_id=u.user_id " +
                     "ORDER BY o.create_time DESC";
        return qr.query(sql, new BeanListHandler<>(Order.class));
    }

    // 更新订单状态
    public int updateStatus(int orderId, int status) throws SQLException {
        String sql = "UPDATE orders SET status=? WHERE order_id=?";
        return qr.update(sql, status, orderId);
    }
}
package com.electronicmall.dao;

import com.electronicmall.model.Cart;
import com.electronicmall.util.JDBCUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class CartDao {

    private QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());

    // 查询某用户的购物车（带商品信息）
    public List<Cart> findByUserId(int userId) throws SQLException {
        String sql = "SELECT c.cart_id cartId, c.user_id userId, c.product_id productId, " +
                     "c.quantity, p.product_name productName, p.price, p.img_url imgUrl " +
                     "FROM cart c LEFT JOIN product p ON c.product_id=p.product_id " +
                     "WHERE c.user_id=? ORDER BY c.add_time DESC";
        return qr.query(sql, new BeanListHandler<>(Cart.class), userId);
    }

    // 查某用户某商品是否已在购物车
    public Cart findByUserAndProduct(int userId, int productId) throws SQLException {
        String sql = "SELECT cart_id cartId, user_id userId, product_id productId, quantity " +
                     "FROM cart WHERE user_id=? AND product_id=?";
        return qr.query(sql, new BeanHandler<>(Cart.class), userId, productId);
    }

    // 加入购物车
    public int add(int userId, int productId) throws SQLException {
        String sql = "INSERT INTO cart(user_id, product_id, quantity) VALUES(?,?,1)";
        return qr.update(sql, userId, productId);
    }

    // 已存在则数量+1
    public int increaseQuantity(int cartId) throws SQLException {
        String sql = "UPDATE cart SET quantity=quantity+1 WHERE cart_id=?";
        return qr.update(sql, cartId);
    }

    // 更新数量
    public int updateQuantity(int cartId, int quantity) throws SQLException {
        String sql = "UPDATE cart SET quantity=? WHERE cart_id=?";
        return qr.update(sql, quantity, cartId);
    }

    // 删除单条
    public int delete(int cartId) throws SQLException {
        String sql = "DELETE FROM cart WHERE cart_id=?";
        return qr.update(sql, cartId);
    }

    // 结算后清空该用户购物车
    public int clearByUserId(int userId) throws SQLException {
        String sql = "DELETE FROM cart WHERE user_id=?";
        return qr.update(sql, userId);
    }
}
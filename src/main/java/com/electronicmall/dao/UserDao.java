package com.electronicmall.dao;

import com.electronicmall.model.User;
import com.electronicmall.util.JDBCUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class UserDao {

    private QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());

    // 根据用户名查用户（登录用）
    public User findByUsername(String username) throws SQLException {
        String sql = "SELECT user_id userId, username, password, email, phone, role, create_time createTime FROM user WHERE username=?";
        return qr.query(sql, new BeanHandler<>(User.class), username);
    }

    // 注册：插入新用户
    public int register(User user) throws SQLException {
        String sql = "INSERT INTO user(username, password, email, phone) VALUES(?,?,?,?)";
        return qr.update(sql, user.getUsername(), user.getPassword(), user.getEmail(), user.getPhone());
    }

    // 判断用户名是否已存在
    public boolean isUsernameExist(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM user WHERE username=?";
        long count = qr.query(sql, rs -> {
            rs.next();
            return rs.getLong(1);
        }, username);
        return count > 0;
    }

    // 查询所有用户（管理员用）
    public List<User> findAll() throws SQLException {
        String sql = "SELECT user_id userId, username, email, phone, role, create_time createTime FROM user";
        return qr.query(sql, new BeanListHandler<>(User.class));
    }

    // 根据ID查用户
    public User findById(int userId) throws SQLException {
        String sql = "SELECT user_id userId, username, email, phone, role FROM user WHERE user_id=?";
        return qr.query(sql, new BeanHandler<>(User.class), userId);
    }
}
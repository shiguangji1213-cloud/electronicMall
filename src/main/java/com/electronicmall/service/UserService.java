package com.electronicmall.service;

import com.electronicmall.dao.UserDao;
import com.electronicmall.model.User;
import com.electronicmall.util.MD5Util;

import java.sql.SQLException;

public class UserService {

    private UserDao userDao = new UserDao();

    // 登录：返回User对象表示成功，返回null表示失败
    public User login(String username, String password) {
        try {
            User user = userDao.findByUsername(username);
            if (user != null && MD5Util.verify(password, user.getPassword())) {
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 注册：返回0成功，1用户名已存在，-1系统错误
    public int register(String username, String password, String email, String phone) {
        try {
            if (userDao.isUsernameExist(username)) {
                return 1;
            }
            User user = new User();
            user.setUsername(username);
            user.setPassword(MD5Util.encrypt(password));
            user.setEmail(email);
            user.setPhone(phone);
            userDao.register(user);
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
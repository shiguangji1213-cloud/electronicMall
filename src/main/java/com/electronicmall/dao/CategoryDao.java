package com.electronicmall.dao;

import com.electronicmall.model.Category;
import com.electronicmall.util.JDBCUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class CategoryDao {

    private QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());

    // 查询所有分类
    public List<Category> findAll() throws SQLException {
        String sql = "SELECT category_id categoryId, category_name categoryName FROM category";
        return qr.query(sql, new BeanListHandler<>(Category.class));
    }

    // 新增分类
    public int add(Category category) throws SQLException {
        String sql = "INSERT INTO category(category_name) VALUES(?)";
        return qr.update(sql, category.getCategoryName());
    }

    // 删除分类
    public int delete(int categoryId) throws SQLException {
        String sql = "DELETE FROM category WHERE category_id=?";
        return qr.update(sql, categoryId);
    }
}
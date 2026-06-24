package com.electronicmall.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.electronicmall.model.Product;
import com.electronicmall.util.JDBCUtil;

public class ProductDao {

    private QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());

    // 查询所有上架商品（首页展示）
    public List<Product> findAllOnSale() throws SQLException {
        String sql = "SELECT p.product_id productId, p.product_name productName, " +
                     "p.price, p.stock, p.img_url imgUrl, p.description, " +
                     "c.category_name categoryName, p.category_id categoryId " +
                     "FROM product p LEFT JOIN category c ON p.category_id=c.category_id " +
                     "WHERE p.status=1 ORDER BY p.create_time DESC";
        return qr.query(sql, new BeanListHandler<>(Product.class));
    }

    // 按分类查商品
    public List<Product> findByCategory(int categoryId) throws SQLException {
        String sql = "SELECT p.product_id productId, p.product_name productName, " +
                     "p.price, p.stock, p.img_url imgUrl, p.description, " +
                     "c.category_name categoryName, p.category_id categoryId " +
                     "FROM product p LEFT JOIN category c ON p.category_id=c.category_id " +
                     "WHERE p.status=1 AND p.category_id=? ORDER BY p.create_time DESC";
        return qr.query(sql, new BeanListHandler<>(Product.class), categoryId);
    }

    // 关键字搜索
    public List<Product> search(String keyword) throws SQLException {
        String sql = "SELECT p.product_id productId, p.product_name productName, " +
                     "p.price, p.stock, p.img_url imgUrl, p.description, " +
                     "c.category_name categoryName, p.category_id categoryId " +
                     "FROM product p LEFT JOIN category c ON p.category_id=c.category_id " +
                     "WHERE p.status=1 AND p.product_name LIKE ?";
        return qr.query(sql, new BeanListHandler<>(Product.class), "%" + keyword + "%");
    }

    // 根据ID查单个商品
    public Product findById(int productId) throws SQLException {
        String sql = "SELECT p.product_id productId, p.product_name productName, " +
                     "p.price, p.stock, p.img_url imgUrl, p.description, p.status, " +
                     "c.category_name categoryName, p.category_id categoryId " +
                     "FROM product p LEFT JOIN category c ON p.category_id=c.category_id " +
                     "WHERE p.product_id=?";
        return qr.query(sql, new BeanHandler<>(Product.class), productId);
    }

    // 管理员：查所有商品（含下架）
    public List<Product> findAll() throws SQLException {
        String sql = "SELECT p.product_id productId, p.product_name productName, " +
                     "p.price, p.stock, p.img_url imgUrl, p.status, " +
                     "c.category_name categoryName, p.category_id categoryId " +
                     "FROM product p LEFT JOIN category c ON p.category_id=c.category_id " +
                     "ORDER BY p.create_time DESC";
        return qr.query(sql, new BeanListHandler<>(Product.class));
    }

    // 新增商品
    public int add(Product p) throws SQLException {
        String sql = "INSERT INTO product(product_name,category_id,price,stock,description,img_url) VALUES(?,?,?,?,?,?)";
        return qr.update(sql, p.getProductName(), p.getCategoryId(),
                p.getPrice(), p.getStock(), p.getDescription(), p.getImgUrl());
    }

    // 修改商品
    public int update(Product p) throws SQLException {
        String sql = "UPDATE product SET product_name=?,category_id=?,price=?,stock=?,description=?,img_url=?,status=? WHERE product_id=?";
        return qr.update(sql, p.getProductName(), p.getCategoryId(),
                p.getPrice(), p.getStock(), p.getDescription(), p.getImgUrl(),
                p.getStatus(), p.getProductId());
    }

    // 删除商品
    public int delete(int productId) throws SQLException {
        String sql = "DELETE FROM product WHERE product_id=?";
        return qr.update(sql, productId);
    }

    // 减库存（下单时用）
    public int reduceStock(int productId, int quantity) throws SQLException {
        String sql = "UPDATE product SET stock=stock-? WHERE product_id=? AND stock>=?";
        return qr.update(sql, quantity, productId, quantity);
    }
}
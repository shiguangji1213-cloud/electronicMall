package com.electronicmall.service;

import com.electronicmall.dao.CategoryDao;
import com.electronicmall.dao.ProductDao;
import com.electronicmall.model.Category;
import com.electronicmall.model.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductService {

    private ProductDao productDao = new ProductDao();
    private CategoryDao categoryDao = new CategoryDao();

    // 首页：获取所有上架商品
    public List<Product> getAllOnSale() {
        try {
            return productDao.findAllOnSale();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // 按分类获取商品
    public List<Product> getByCategory(int categoryId) {
        try {
            return productDao.findByCategory(categoryId);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // 搜索商品
    public List<Product> search(String keyword) {
        try {
            return productDao.search(keyword);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // 商品详情
    public Product getById(int productId) {
        try {
            return productDao.findById(productId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 获取所有分类
    public List<Category> getAllCategories() {
        try {
            return categoryDao.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // 管理员：获取所有商品
    public List<Product> getAll() {
        try {
            return productDao.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // 管理员：新增商品
    public boolean addProduct(Product p) {
        try {
            return productDao.add(p) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 管理员：修改商品
    public boolean updateProduct(Product p) {
        try {
            return productDao.update(p) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 管理员：删除商品
    public boolean deleteProduct(int productId) {
        try {
            return productDao.delete(productId) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
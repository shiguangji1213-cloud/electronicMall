package com.electronicmall.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.electronicmall.model.Category;
import com.electronicmall.model.Product;
import com.electronicmall.service.ProductService;

@WebServlet("/product")
public class ProductServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("detail".equals(action)) {
            // 商品详情
            int productId = Integer.parseInt(req.getParameter("id"));
            Product product = productService.getById(productId);
            req.setAttribute("product", product);
            req.getRequestDispatcher("/product_detail.jsp").forward(req, resp);

        } else if ("search".equals(action)) {
            // 搜索
            String keyword = req.getParameter("keyword");
            if (keyword == null) keyword = "";
            List<Product> list = productService.search(keyword);
            req.setAttribute("productList", list);
            req.setAttribute("keyword", keyword);
            req.getRequestDispatcher("/product_list.jsp").forward(req, resp);

        } else if ("category".equals(action)) {
            // 按分类
            int categoryId = Integer.parseInt(req.getParameter("cid"));
            List<Product> list = productService.getByCategory(categoryId);
            List<Category> categories = productService.getAllCategories();
            req.setAttribute("productList", list);
            req.setAttribute("categories", categories);
            req.getRequestDispatcher("/product_list.jsp").forward(req, resp);

        } else {
            // 默认：加载首页所有商品
            List<Product> list = productService.getAllOnSale();
            List<Category> categories = productService.getAllCategories();
            req.setAttribute("productList", list);
            req.setAttribute("categories", categories);
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
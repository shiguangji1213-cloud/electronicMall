package com.electronicmall.servlet;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.electronicmall.model.Product;
import com.electronicmall.model.User;
import com.electronicmall.service.OrderService;
import com.electronicmall.service.ProductService;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProductService productService = new ProductService();
    private OrderService   orderService   = new OrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 管理员权限校验
        User loginUser = (User) req.getSession().getAttribute("loginUser");
        if (loginUser == null || loginUser.getRole() != 1) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        String action = req.getParameter("action");

        if ("productList".equals(action)) {
            // 商品列表
            req.setAttribute("productList", productService.getAll());
            req.getRequestDispatcher("/admin/product_list.jsp").forward(req, resp);

        } else if ("productAdd".equals(action)) {
            // 跳转新增页
            req.setAttribute("categories", productService.getAllCategories());
            req.getRequestDispatcher("/admin/product_add.jsp").forward(req, resp);

        } else if ("productEdit".equals(action)) {
            // 跳转编辑页
            int id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("product", productService.getById(id));
            req.setAttribute("categories", productService.getAllCategories());
            req.getRequestDispatcher("/admin/product_edit.jsp").forward(req, resp);

        } else if ("productDelete".equals(action)) {
            // 删除商品
            int id = Integer.parseInt(req.getParameter("id"));
            productService.deleteProduct(id);
            resp.sendRedirect(req.getContextPath() + "/admin?action=productList");

        } else if ("orderList".equals(action)) {
            // 订单管理
            req.setAttribute("orderList", orderService.getAllOrders());
            req.getRequestDispatcher("/admin/order_list.jsp").forward(req, resp);

        } else if ("orderStatus".equals(action)) {
            // 更新订单状态
            int orderId = Integer.parseInt(req.getParameter("orderId"));
            int status  = Integer.parseInt(req.getParameter("status"));
            orderService.updateStatus(orderId, status);
            resp.sendRedirect(req.getContextPath() + "/admin?action=orderList");

        } else {
            // 后台首页
            req.setAttribute("productCount", productService.getAll().size());
            req.setAttribute("orderList",    orderService.getAllOrders());
            req.getRequestDispatcher("/admin/index.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User loginUser = (User) req.getSession().getAttribute("loginUser");
        if (loginUser == null || loginUser.getRole() != 1) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        String action = req.getParameter("action");

        if ("productAdd".equals(action)) {
            // 保存新商品
            Product p = buildProductFromRequest(req);
            productService.addProduct(p);
            resp.sendRedirect(req.getContextPath() + "/admin?action=productList");

        } else if ("productEdit".equals(action)) {
            // 保存编辑
            Product p = buildProductFromRequest(req);
            p.setProductId(Integer.parseInt(req.getParameter("productId")));
            p.setStatus(Integer.parseInt(req.getParameter("status")));
            productService.updateProduct(p);
            resp.sendRedirect(req.getContextPath() + "/admin?action=productList");
        }
    }

    // 从表单构建 Product 对象
    private Product buildProductFromRequest(HttpServletRequest req) {
        Product p = new Product();
        p.setProductName(req.getParameter("productName"));
        p.setCategoryId(Integer.parseInt(req.getParameter("categoryId")));
        p.setPrice(new BigDecimal(req.getParameter("price")));
        p.setStock(Integer.parseInt(req.getParameter("stock")));
        p.setDescription(req.getParameter("description"));
        p.setImgUrl(req.getParameter("imgUrl"));
        return p;
    }
}
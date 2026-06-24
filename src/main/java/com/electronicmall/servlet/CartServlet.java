package com.electronicmall.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.electronicmall.model.Cart;
import com.electronicmall.model.User;
import com.electronicmall.service.CartService;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CartService cartService = new CartService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        User loginUser = (User) req.getSession().getAttribute("loginUser");

        // 未登录跳转登录页
        if (loginUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        int userId = loginUser.getUserId();

        if ("add".equals(action)) {
            // 加入购物车
            int productId = Integer.parseInt(req.getParameter("pid"));
            cartService.addToCart(userId, productId);
            resp.sendRedirect(req.getContextPath() + "/cart?action=list");

        } else if ("delete".equals(action)) {
            // 删除
            int cartId = Integer.parseInt(req.getParameter("cartId"));
            cartService.deleteItem(cartId);
            resp.sendRedirect(req.getContextPath() + "/cart?action=list");

        } else if ("update".equals(action)) {
            // 更新数量
            int cartId  = Integer.parseInt(req.getParameter("cartId"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));
            cartService.updateQuantity(cartId, quantity);
            resp.sendRedirect(req.getContextPath() + "/cart?action=list");

        } else {
            // 显示购物车列表
            List<Cart> cartList = cartService.getCartList(userId);
            BigDecimal total = cartService.getTotalPrice(cartList);
            req.setAttribute("cartList", cartList);
            req.setAttribute("totalPrice", total);
            req.getRequestDispatcher("/cart.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
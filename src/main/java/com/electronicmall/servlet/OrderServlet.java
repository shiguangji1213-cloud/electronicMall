package com.electronicmall.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.electronicmall.model.Order;
import com.electronicmall.model.User;
import com.electronicmall.service.OrderService;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        User loginUser = (User) req.getSession().getAttribute("loginUser");

        if (loginUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        if ("myOrders".equals(action)) {
            // 我的订单
            List<Order> orders = orderService.getMyOrders(loginUser.getUserId());
            req.setAttribute("orderList", orders);
            req.getRequestDispatcher("/order_list.jsp").forward(req, resp);

        } else if ("checkout".equals(action)) {
            // 跳转结算页
            req.getRequestDispatcher("/checkout.jsp").forward(req, resp);
            
        } else if ("pay".equals(action)) {
            // 💡 新增核心：处理前端 order_list.jsp 传过来的“去付款”动作
            int orderId = Integer.parseInt(req.getParameter("orderId"));
            // 模拟调用第三方支付网关，付款成功后将订单状态从 0(待付款) 更新为 1(已付款)
            orderService.updateStatus(orderId, 1);
            
            // 将付款成功的订单号存入 session，以便在订单列表页面给用户弹窗提示
            req.getSession().setAttribute("paySuccessOrderId", orderId);
            resp.sendRedirect(req.getContextPath() + "/order?action=myOrders");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        User loginUser = (User) req.getSession().getAttribute("loginUser");

        if (loginUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        if ("submit".equals(action)) {
            // 提交订单
            String address = req.getParameter("address");
            String payType = req.getParameter("payType"); // 从结算表单获取：1=在线支付 2=货到付款

            // 💡 绝杀修复：这里传入 3 个参数，完美匹配刚刚修改好的 OrderService 业务流
            int orderId = orderService.createOrderFromCart(loginUser.getUserId(), address, payType);
            
            if (orderId > 0) {
                req.setAttribute("orderId", orderId);
                req.setAttribute("payType", payType); // 传给 order_success.jsp 用来智能判断显示🎉（已付款）还是📦（货到付款）
                req.getRequestDispatcher("/order_success.jsp").forward(req, resp);
            } else {
                req.setAttribute("errorMsg", "下单失败，购物车为空或库存不足");
                // 下单失败直接让用户导回购物车页面重新检查
                resp.sendRedirect(req.getContextPath() + "/cart?action=list");
            }
        }
    }
}
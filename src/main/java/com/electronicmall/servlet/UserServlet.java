package com.electronicmall.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.electronicmall.model.User;
import com.electronicmall.service.UserService;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("login".equals(action)) {
            doLogin(req, resp);
        } else if ("register".equals(action)) {
            doRegister(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("logout".equals(action)) {
            req.getSession().invalidate();
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }

    private void doLogin(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username   = req.getParameter("username");
        String password   = req.getParameter("password");
        String verifyCode = req.getParameter("verifyCode");

        // 验证码校验（不区分大小写）
        String sessionCode = (String) req.getSession().getAttribute("verifyCode");
        if (sessionCode == null || !sessionCode.equalsIgnoreCase(verifyCode)) {
            req.setAttribute("errorMsg", "验证码错误，请重新输入");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }

        User user = userService.login(username, password);
        if (user != null) {
            req.getSession().setAttribute("loginUser", user);
            if (user.getRole() == 1) {
                resp.sendRedirect(req.getContextPath() + "/admin");
            } else {
                resp.sendRedirect(req.getContextPath() + "/product");
            }
        } else {
            req.setAttribute("errorMsg", "用户名或密码错误");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    private void doRegister(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email    = req.getParameter("email");
        String phone    = req.getParameter("phone");

        int result = userService.register(username, password, email, phone);
        if (result == 0) {
            req.setAttribute("successMsg", "注册成功，请登录");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        } else if (result == 1) {
            req.setAttribute("errorMsg", "用户名已存在");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
        } else {
            req.setAttribute("errorMsg", "系统错误，请稍后重试");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
        }
    }
}
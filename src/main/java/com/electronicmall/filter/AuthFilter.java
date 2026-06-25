package com.electronicmall.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 核心权限过滤器：防范未登录用户绕过前端直接访问核心接口
 */
@WebFilter(urlPatterns = {
    "/cart.jsp", "/checkout.jsp", "/order_list.jsp", "/order_success.jsp", 
    "/cart", "/order", // ✅ 已修正：完美匹配 CartServlet 和 OrderServlet 的真实映射路径
    "/admin/*" 
})
public class AuthFilter implements Filter {

    public void init(FilterConfig fConfig) throws ServletException {
        // 过滤器初始化
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        
        // 精准匹配你在 UserServlet 里设置的 Session 键名 "loginUser"
        Object currentUser = session.getAttribute("loginUser");

        // 熔断拦截机制
        if (currentUser == null) {
            // 存入错误提示（对齐整个项目的 errorMsg 命名规范，方便 login.jsp 回显）
            req.setAttribute("errorMsg", "🔴 安全拦截：请先登录后再进行购买或查看订单！");
            
            // 强制打回登录页
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return; // 极其关键：切断后续执行，阻止请求到达 Servlet
        }

        // 安全放行
        chain.doFilter(request, response);
    }

    public void destroy() {
        // 过滤器销毁
    }
}

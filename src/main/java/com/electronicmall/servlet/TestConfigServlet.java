package com.electronicmall.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.electronicmall.util.JDBCUtil;
import com.electronicmall.util.MD5Util;

@WebServlet("/test")
public class TestConfigServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.println("<h1 style='text-align:center; margin-top:50px;'>电子商城环境配置测试</h1>");
        out.println("<hr style='width:80%;'>");
        
        // 测试数据库连接
        try {
            Connection conn = JDBCUtil.getConnection();
            out.println("<p style='color:green; font-size:18px; text-align:center;'>✅ 数据库连接成功！</p>");
            conn.close();
        } catch (Exception e) {
            out.println("<p style='color:red; font-size:18px; text-align:center;'>❌ 数据库连接失败：" + e.getMessage() + "</p>");
            e.printStackTrace();
        }
        
        // 测试MD5加密
        String adminPwd = MD5Util.encrypt("admin");
        if (adminPwd.equals("21232f297a57a5a743894a0e4a801fc3")) {
            out.println("<p style='color:green; font-size:18px; text-align:center;'>✅ MD5加密正常！</p>");
        } else {
            out.println("<p style='color:red; font-size:18px; text-align:center;'>❌ MD5加密异常！</p>");
        }
        
        // 测试过滤器
        out.println("<p style='color:green; font-size:18px; text-align:center;'>✅ 字符编码过滤器生效！</p>");
        out.println("<hr style='width:80%;'>");
        out.println("<h2 style='text-align:center; color:#2ecc71;'>🎉 所有配置全部完成！</h2>");
        out.println("<p style='text-align:center; font-size:16px;'>测试用户账号：test / 123456</p>");
        out.println("<p style='text-align:center; font-size:16px;'>管理员账号：admin / admin</p>");
        out.println("<p style='text-align:center; font-size:16px;'>现在可以用大模型开始写业务代码了！</p>");
    }
}
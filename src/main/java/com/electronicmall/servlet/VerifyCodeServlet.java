package com.electronicmall.servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@WebServlet("/verifyCode")
public class VerifyCodeServlet extends HttpServlet {

    private static final String CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final int WIDTH = 120, HEIGHT = 40;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("image/jpeg");
        resp.setHeader("Cache-Control", "no-cache");

        BufferedImage img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();

        // 背景
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(new Color(245, 245, 250));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // 干扰线
        Random rand = new Random();
        g.setStroke(new BasicStroke(1.2f));
        for (int i = 0; i < 5; i++) {
            g.setColor(new Color(rand.nextInt(180) + 60, rand.nextInt(180) + 60, rand.nextInt(180) + 60));
            g.drawLine(rand.nextInt(WIDTH), rand.nextInt(HEIGHT),
                       rand.nextInt(WIDTH), rand.nextInt(HEIGHT));
        }

        // 验证码文字
        StringBuilder code = new StringBuilder();
        g.setFont(new Font("Arial", Font.BOLD, 24));
        for (int i = 0; i < 4; i++) {
            char c = CHARS.charAt(rand.nextInt(CHARS.length()));
            code.append(c);
            g.setColor(new Color(rand.nextInt(80), rand.nextInt(80) + 50, rand.nextInt(150) + 80));
            g.drawString(String.valueOf(c), 10 + i * 26, 28 + rand.nextInt(6) - 3);
        }

        // 干扰点
        for (int i = 0; i < 40; i++) {
            g.setColor(new Color(rand.nextInt(200), rand.nextInt(200), rand.nextInt(200)));
            g.fillOval(rand.nextInt(WIDTH), rand.nextInt(HEIGHT), 2, 2);
        }

        g.dispose();

        // 存入session
        req.getSession().setAttribute("verifyCode", code.toString());
        ImageIO.write(img, "jpg", resp.getOutputStream());
    }
}
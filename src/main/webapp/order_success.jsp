<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>下单成功 - 电子商城</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="navbar">
    <a class="logo" href="${pageContext.request.contextPath}/product">🛒 电子商城</a>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/product">首页</a>
        <a href="${pageContext.request.contextPath}/order?action=myOrders">我的订单</a>
    </div>
</div>

<div class="main-content">
    <div class="success-page">
        <c:choose>
            <c:when test="${payType == '1'}">
                <div class="success-icon">🎉</div>
                <h2 style="color:#27ae60;">下单并付款成功！</h2>
                <p class="order-no">订单号：<strong>${orderId}</strong></p>
                <p class="tip">
                    <span style="background:#d4edda;color:#155724;padding:6px 16px;border-radius:20px;font-size:14px;font-weight:600;">
                        ✅ 已付款 · 在线支付
                    </span>
                </p>
                <p class="tip" style="margin-top:12px;">系统已自动通知总仓，我们将尽快为您安排发货～</p>
            </c:when>
            <c:otherwise>
                <div class="success-icon">📦</div>
                <h2>订单提交成功！</h2>
                <p class="order-no">订单号：<strong>${orderId}</strong></p>
                <p class="tip">
                    <span style="background:#fff3cd;color:#856404;padding:6px 16px;border-radius:20px;font-size:14px;font-weight:600;">
                        💵 货到付款 · 待签收
                    </span>
                </p>
                <p class="tip" style="margin-top:12px;">快递小哥送达时，请您准备好现金或扫码支付，谢谢～</p>
            </c:otherwise>
        </c:choose>
        
        <div class="btn-group" style="margin-top:32px;">
            <a class="btn btn-outline" href="${pageContext.request.contextPath}/order?action=myOrders">
                查看我的订单
            </a>
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/product">
                继续购物
            </a>
        </div>
    </div>
</div>

<div class="footer">© 2026 <span>电子商城</span> electronicMall · JSP 实训项目</div>
</body>
</html>
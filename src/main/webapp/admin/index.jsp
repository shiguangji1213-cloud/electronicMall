<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>后台管理 - 电子商城</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .admin-layout { display:flex; min-height:calc(100vh - 60px); }
        .sidebar { width:200px; background:#2c3e50; padding-top:20px; flex-shrink:0; }
        .sidebar a { display:block; padding:14px 24px; color:#bdc3c7;
            text-decoration:none; font-size:14px; border-left:3px solid transparent; }
        .sidebar a:hover, .sidebar a.active {
            background:#34495e; color:white; border-left-color:#e74c3c; }
        .admin-content { flex:1; padding:30px; }
        .stat-cards { display:grid; grid-template-columns:repeat(3,1fr); gap:20px; margin-bottom:30px; }
        .stat-card { background:white; border-radius:8px; padding:24px;
            box-shadow:0 1px 4px rgba(0,0,0,0.1); text-align:center; }
        .stat-card .num { font-size:36px; font-weight:bold; color:#e74c3c; }
        .stat-card .label { color:#999; font-size:14px; margin-top:6px; }
    </style>
</head>
<body>
<div class="navbar">
    <a class="logo" href="${pageContext.request.contextPath}/product">🛒 电子商城</a>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/product">前台首页</a>
        <a href="${pageContext.request.contextPath}/user?action=logout">退出(${sessionScope.loginUser.username})</a>
    </div>
</div>

<div class="admin-layout">
    <div class="sidebar">
        <a href="${pageContext.request.contextPath}/admin" class="active">📊 控制台</a>
        <a href="${pageContext.request.contextPath}/admin?action=productList">📦 商品管理</a>
        <a href="${pageContext.request.contextPath}/admin?action=orderList">📋 订单管理</a>
    </div>
    <div class="admin-content">
        <h2 style="margin-bottom:24px;">控制台</h2>
        <div class="stat-cards">
            <div class="stat-card">
                <div class="num">${productCount}</div>
                <div class="label">商品总数</div>
            </div>
            <div class="stat-card">
                <div class="num">${orderList.size()}</div>
                <div class="label">订单总数</div>
            </div>
            <div class="stat-card">
                <div class="num" style="color:#27ae60;">营业中</div>
                <div class="label">店铺状态</div>
            </div>
        </div>
        <div class="section-title">最近订单</div>
        <table class="cart-table" style="margin-top:12px;">
            <thead>
                <tr><th>订单号</th><th>用户</th><th>金额</th><th>状态</th><th>时间</th></tr>
            </thead>
            <tbody>
                <c:forEach var="o" items="${orderList}" begin="0" end="4">
                    <tr>
                        <td>${o.orderId}</td>
                        <td>${o.username}</td>
                        <td style="color:#e74c3c;">¥${o.totalPrice}</td>
                        <td>${o.statusText}</td>
                        <td>${o.createTime}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
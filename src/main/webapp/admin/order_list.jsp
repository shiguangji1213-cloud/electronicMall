<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>订单管理 - 后台</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .admin-layout{display:flex;min-height:calc(100vh - 60px);}
        .sidebar{width:200px;background:#2c3e50;padding-top:20px;flex-shrink:0;}
        .sidebar a{display:block;padding:14px 24px;color:#bdc3c7;text-decoration:none;font-size:14px;border-left:3px solid transparent;}
        .sidebar a:hover,.sidebar a.active{background:#34495e;color:white;border-left-color:#e74c3c;}
        .admin-content{flex:1;padding:30px;}
    </style>
</head>
<body>
<div class="navbar">
    <a class="logo" href="${pageContext.request.contextPath}/product">🛒 电子商城</a>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/product">前台首页</a>
        <a href="${pageContext.request.contextPath}/user?action=logout">退出</a>
    </div>
</div>
<div class="admin-layout">
    <div class="sidebar">
        <a href="${pageContext.request.contextPath}/admin">📊 控制台</a>
        <a href="${pageContext.request.contextPath}/admin?action=productList">📦 商品管理</a>
        <a href="${pageContext.request.contextPath}/admin?action=orderList" class="active">📋 订单管理</a>
    </div>
    <div class="admin-content">
        <div class="section-title" style="margin-bottom:20px;">订单列表</div>
        <table class="cart-table">
            <thead>
                <tr><th>订单号</th><th>用户</th><th>金额</th><th>收货地址</th><th>状态</th><th>下单时间</th><th>操作</th></tr>
            </thead>
            <tbody>
                <c:forEach var="o" items="${orderList}">
                    <tr>
                        <td>${o.orderId}</td>
                        <td>${o.username}</td>
                        <td style="color:#e74c3c;font-weight:bold;">
                            ¥<fmt:formatNumber value="${o.totalPrice}" pattern="#,##0.00"/>
                        </td>
                        <td style="max-width:180px;font-size:13px;">${o.address}</td>
                        <td>
                            <span class="order-status status-${o.status}">${o.statusText}</span>
                        </td>
                        <td style="font-size:13px;">
                            <fmt:formatDate value="${o.createTime}" pattern="MM-dd HH:mm"/>
                        </td>
                        <td>
                            <c:if test="${o.status < 3}">
                                <a class="btn btn-success btn-sm"
                                   href="${pageContext.request.contextPath}/admin?action=orderStatus&orderId=${o.orderId}&status=${o.status+1}"
                                   onclick="return confirm('确认更新订单状态？')">
                                    下一步
                                </a>
                            </c:if>
                            <c:if test="${o.status == 3}">
                                <span style="color:#999;font-size:13px;">已完成</span>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
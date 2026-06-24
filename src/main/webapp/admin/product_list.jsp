<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>商品档案控盘中心 - 电子商城后台</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="navbar">
    <a class="logo" href="${pageContext.request.contextPath}/product">⚙️ 商城管理后台</a>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/product" target="_blank">🌐 预览前台首页</a>
        <a href="${pageContext.request.contextPath}/user?action=logout">退出</a>
    </div>
</div>

<div class="admin-layout">
    <div class="sidebar">
        <div class="menu-title">核心管理</div>
        <a href="${pageContext.request.contextPath}/admin">📊 控制台</a>
        <a href="${pageContext.request.contextPath}/admin?action=productList" class="active">📦 商品管理</a>
        <a href="${pageContext.request.contextPath}/admin?action=orderList">📋 订单管理</a>
    </div>
    
    <div class="admin-content">
        <div class="admin-header">
            <h2>📦 在售商品上架清册</h2>
            <a class="btn btn-success" href="${pageContext.request.contextPath}/admin?action=productAdd">➕ 录入新商品</a>
        </div>
        
        <table class="data-table">
            <thead>
                <tr>
                    <th style="width:50px;">ID</th>
                    <th style="width:70px;">商品外观</th>
                    <th>商品品名</th>
                    <th style="width:100px;">品类分类</th>
                    <th style="width:110px;">核心售卖价</th>
                    <th style="width:90px;">仓储库存</th>
                    <th style="width:80px;">营销状态</th>
                    <th style="width:130px;">盘点流转</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="p" items="${productList}">
                    <tr>
                        <td style="color:#999; font-size:12px;">${p.productId}</td>
                        <td>
                            <c:choose>
                                <c:when test="${fn:startsWith(p.imgUrl, 'http')}">
                                    <img src="${p.imgUrl}" style="width:45px; height:45px; object-fit:cover; border-radius:6px; border:1px solid #eee;" onerror="this.onerror=null;this.src='https://images.unsplash.com/photo-1472851294608-062f824d29cc?w=100';">
                                </c:when>
                                <c:otherwise>
                                    <img src="${pageContext.request.contextPath}${p.imgUrl}" style="width:45px; height:45px; object-fit:cover; border-radius:6px; border:1px solid #eee;" onerror="this.onerror=null;this.src='https://images.unsplash.com/photo-1472851294608-062f824d29cc?w=100';">
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align:left; font-weight:500;">${p.productName}</td>
                        <td>
                            <span style="background:#eef2f7; color:#555; padding:3px 10px; border-radius:12px; font-size:12px;">
                                ${cat.categoryName != null ? cat.categoryName : p.categoryName}
                            </span>
                        </td>
                        <td style="color:#e74c3c; font-weight:600;">¥<fmt:formatNumber value="${p.price}" pattern="#,##0.00"/></td>
                        <td>
                            <c:choose>
                                <c:when test="${p.stock <= 10}">
                                    <strong style="color:#e74c3c; background:#fff0f0; padding:2px 6px; border-radius:4px;">${p.stock} 件 ⚠️</strong>
                                </c:when>
                                <c:otherwise>${p.stock} 件</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${p.status == 1}">
                                    <span style="color:#27ae60; font-weight:600;">🟢 上架中</span>
                                </c:when>
                                <c:otherwise>
                                    <span style="color:#bbb;">🔴 已下架</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <a class="btn btn-info btn-sm" href="${pageContext.request.contextPath}/admin?action=productEdit&id=${p.productId}">编辑</a>
                            <a class="btn btn-danger btn-sm" href="${pageContext.request.contextPath}/admin?action=productDelete&id=${p.productId}" onclick="return confirm('确认物理删除该商品档案？')">删除</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
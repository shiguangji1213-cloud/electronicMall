<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>商品列表 - 电子商城</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="navbar">
    <a class="logo" href="${pageContext.request.contextPath}/product">🛒 电子商城</a>
    <form class="search-bar" action="${pageContext.request.contextPath}/product" method="get">
        <input type="hidden" name="action" value="search">
        <input type="text" name="keyword" placeholder="搜索手机、电脑、家电..." value="${keyword}">
        <button type="submit">🔍 搜索</button>
    </form>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/product">首页</a>
        <c:choose>
            <c:when test="${sessionScope.loginUser != null}">
                <a href="${pageContext.request.contextPath}/cart?action=list">🛒 购物车</a>
                <a href="${pageContext.request.contextPath}/order?action=myOrders">📋 我的订单</a>
                <a href="${pageContext.request.contextPath}/user?action=logout">退出</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/login.jsp">登录</a>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<div class="category-bar">
    <a href="${pageContext.request.contextPath}/product">全部商品</a>
    <c:forEach var="cat" items="${categories}">
        <a href="${pageContext.request.contextPath}/product?action=category&cid=${cat.categoryId}">
            ${cat.categoryName}
        </a>
    </c:forEach>
</div>

<div class="main-content">
    <div class="section-title">
        共找到 <span style="color:#e74c3c;">${productList.size()}</span> 件商品
        <c:if test="${keyword != null && keyword != ''}">
            &nbsp;·&nbsp; 关键词：<span style="color:#e74c3c;">"${keyword}"</span>
        </c:if>
    </div>
    <c:choose>
        <c:when test="${empty productList}">
            <div class="empty-state">
                <div class="empty-icon">🔍</div>
                <p>没有找到相关商品，换个关键词试试</p>
                <a class="btn btn-outline" href="${pageContext.request.contextPath}/product">浏览全部</a>
            </div>
        </c:when>
        <c:otherwise>
            <div class="product-grid">
                <c:forEach var="p" items="${productList}">
                    <a class="product-card" href="${pageContext.request.contextPath}/product?action=detail&id=${p.productId}">
                        <c:choose>
                            <c:when test="${fn:startsWith(p.imgUrl, 'http')}">
                                <img src="${p.imgUrl}" onerror="this.onerror=null;this.src='https://images.unsplash.com/photo-1472851294608-062f824d29cc?w=400';" alt="${p.productName}">
                            </c:when>
                            <c:otherwise>
                                <img src="${pageContext.request.contextPath}${p.imgUrl}" onerror="this.onerror=null;this.src='https://images.unsplash.com/photo-1472851294608-062f824d29cc?w=400';" alt="${p.productName}">
                            </c:otherwise>
                        </c:choose>
                        <div class="card-body">
                            <div class="card-title">${p.productName}</div>
                            <div class="card-price">
                                <span>¥</span><fmt:formatNumber value="${p.price}" pattern="#,##0.00"/>
                            </div>
                            <div class="card-stock">库存 ${p.stock} 件</div>
                        </div>
                    </a>
                </c:forEach>
            </div>
        </c:otherwise>    </c:choose>
</div>
<div class="footer">© 2026 <span>电子商城</span> electronicMall · JSP 实训项目</div>
</body>
</html>
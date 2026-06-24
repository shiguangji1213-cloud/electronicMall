<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>电子商城 - 首页</title>
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
        <c:choose>            
            <c:when test="${sessionScope.loginUser != null}">                
                <a href="${pageContext.request.contextPath}/cart?action=list">🛒 购物车</a>                
                <a href="${pageContext.request.contextPath}/order?action=myOrders">📋 我的订单</a>                
                <c:if test="${sessionScope.loginUser.role == 1}">                    
                    <a href="${pageContext.request.contextPath}/admin">⚙️ 后台</a>       
                </c:if>                
                <a href="${pageContext.request.contextPath}/user?action=logout">                    
                    👤 ${sessionScope.loginUser.username} 退出                
                </a>            
            </c:when>            
            <c:otherwise>            
                <a href="${pageContext.request.contextPath}/login.jsp">登录</a>                
                <a href="${pageContext.request.contextPath}/register.jsp">注册</a>            
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
        <c:choose>            
            <c:when test="${keyword != null && keyword != ''}">                
                搜索 "${keyword}" 的结果（共 ${productList.size()} 件）            
            </c:when>            
            <c:otherwise>热销好物</c:otherwise>        
        </c:choose>    
    </div>    
    <c:choose>        
        <c:when test="${empty productList}">      
            <div class="empty-state">                
                <div class="empty-icon">🔍</div>                
                <p>暂无相关商品</p>                
                <a class="btn btn-outline" href="${pageContext.request.contextPath}/product">浏览全部商品</a>            
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
        </c:otherwise>    
    </c:choose>
</div>
<div class="footer">© 2026 <span>电子商城</span> electronicMall · JSP 实训项目</div>
</body>
</html>
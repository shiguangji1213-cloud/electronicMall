<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${product.productName} - 电子商城</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="navbar">    
    <a class="logo" href="${pageContext.request.contextPath}/product">🛒 电子商城</a>    
    <form class="search-bar" action="${pageContext.request.contextPath}/product" method="get">        
        <input type="hidden" name="action" value="search">        
        <input type="text" name="keyword" placeholder="搜索商品...">        
        <button type="submit">🔍 搜索</button>    
    </form>    
    <div class="nav-links">        
        <a href="${pageContext.request.contextPath}/product">首页</a>        
        <c:choose>            
            <c:when test="${sessionScope.loginUser != null}">                
                <a href="${pageContext.request.contextPath}/cart?action=list">🛒 购物车</a>                
                <a href="${pageContext.request.contextPath}/user?action=logout">退出</a>            
            </c:when>            
            <c:otherwise>                
                <a href="${pageContext.request.contextPath}/login.jsp">登录</a>            
            </c:otherwise>        
        </c:choose>    
    </div>
</div>

<div class="main-content" style="margin-top:32px;">    
    <div class="breadcrumb">        
        <a href="${pageContext.request.contextPath}/product">首页</a>        
        <span>›</span>        
        <span>${product.categoryName}</span>        
        <span>›</span>        
        <span>${product.productName}</span>    
    </div>    
    <c:if test="${product != null}">        
        <div class="detail-wrap">        
            <c:choose>                
                <c:when test="${fn:startsWith(product.imgUrl, 'http')}">                    
                    <img class="detail-img" src="${product.imgUrl}" onerror="this.onerror=null;this.src='https://images.unsplash.com/photo-1472851294608-062f824d29cc?w=400';" alt="${product.productName}">                
                </c:when>                
                <c:otherwise>                    
                    <img class="detail-img" src="${pageContext.request.contextPath}${product.imgUrl}" onerror="this.onerror=null;this.src='https://images.unsplash.com/photo-1472851294608-062f824d29cc?w=400';" alt="${product.productName}">                
                </c:otherwise>            
            </c:choose>            
            <div class="detail-info">                
                <h1>${product.productName}</h1>                
                <div class="detail-price">                    
                    <small>¥</small><fmt:formatNumber value="${product.price}" pattern="#,##0.00"/>                
                </div>                
                <div class="detail-meta">                    
                    <span>📦 库存：${product.stock} 件</span>                    
                    <span>🏷️ ${product.categoryName}</span>                
                </div>                
                <div class="detail-desc-title">商品描述</div>                
                <div class="detail-desc">${product.description}</div>                
                <c:choose>                    
                    <c:when test="${sessionScope.loginUser == null}">                        
                        <a class="btn btn-outline btn-lg" href="${pageContext.request.contextPath}/login.jsp">登录后购买</a>                    
                    </c:when>                    
                    <c:when test="${product.stock > 0}">                        
                        <a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/cart?action=add&pid=${product.productId}">🛒 加入购物车</a>                    
                    </c:when>                    
                    <c:otherwise>                        
                        <span class="out-of-stock">😔 库存不足，暂时无法购买</span>                    
                    </c:otherwise>                
                </c:choose>            
            </div>        
        </div>    
    </c:if>
</div>
<div class="footer">© 2026 <span>电子商城</span> electronicMall · JSP 实训项目</div>
</body>
</html>
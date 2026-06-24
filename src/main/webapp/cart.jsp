<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>购物车 - 电子商城</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="navbar">    
    <a class="logo" href="${pageContext.request.contextPath}/product">🛒 电子商城</a>    
    <div class="nav-links">        
        <a href="${pageContext.request.contextPath}/product">首页</a>        
        <a href="${pageContext.request.contextPath}/order?action=myOrders">我的订单</a>        
        <a href="${pageContext.request.contextPath}/user?action=logout">退出(${sessionScope.loginUser.username})</a>    
    </div>
</div>

<div class="main-content" style="margin-top:32px;">    
    <div class="section-title">🛒 我的购物车</div>    
    <c:choose>        
        <c:when test="${empty cartList}">            
            <div class="empty-state">                
                <div class="empty-icon">🛒</div>                
                <p>购物车空空如也，快去挑选商品吧～</p>                
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/product">去逛逛</a>            
            </div>        
        </c:when>        
        <c:otherwise>            
            <table class="cart-table">                
                <thead>                    
                    <tr>                        
                        <th style="width:90px;">图片</th>                        
                        <th>商品名称</th>                        
                        <th>单价</th>                        
                        <th style="width:130px;">数量</th>                        
                        <th>小计</th>                        
                        <th style="width:80px;">操作</th>                    
                    </tr>                
                </thead>                
                <tbody>                    
                    <c:forEach var="item" items="${cartList}">                        
                        <tr>                            
                            <td>                                
                                <c:choose>                                    
                                    <c:when test="${fn:startsWith(item.imgUrl, 'http')}">                                        
                                        <img src="${item.imgUrl}" onerror="this.onerror=null;this.src='https://images.unsplash.com/photo-1472851294608-062f824d29cc?w=200';" alt="${item.productName}">                                    
                                    </c:when>                                    
                                    <c:otherwise>                                         
                                        <img src="${pageContext.request.contextPath}${item.imgUrl}" onerror="this.onerror=null;this.src='https://images.unsplash.com/photo-1472851294608-062f824d29cc?w=200';" alt="${item.productName}">                                    
                                    </c:otherwise>                                
                                </c:choose>                            
                            </td>                             
                            <td style="text-align:left;font-weight:500;">${item.productName}</td>                            
                            <td style="color:#e74c3c;font-weight:600;">                                
                                ¥<fmt:formatNumber value="${item.price}" pattern="#,##0.00"/>                            
                            </td>                            
                            <td>                                
                                <form action="${pageContext.request.contextPath}/cart" method="get">                                    
                                    <input type="hidden" name="action" value="update">                                    
                                    <input type="hidden" name="cartId" value="${item.cartId}">                                    
                                    <input class="qty-input" type="number" name="quantity" value="${item.quantity}" min="1" max="99" onchange="this.form.submit()">                                 
                                </form>                            
                            </td>                            
                            <td style="color:#e74c3c;font-weight:700;">                                
                                ¥<fmt:formatNumber value="${item.subtotal}" pattern="#,##0.00"/>                            
                            </td>                            
                            <td>                                
                                <a class="btn btn-danger btn-sm" href="${pageContext.request.contextPath}/cart?action=delete&cartId=${item.cartId}" onclick="return confirm('确认从购物车移除？')">移除</a>                            
                            </td>                        
                        </tr>                    
                    </c:forEach>                
                </tbody>            
            </table>            
            <div class="cart-summary">                
                <span class="total-label">已选商品合计：</span>                
                <span class="total-price">                    
                    ¥<fmt:formatNumber value="${totalPrice}" pattern="#,##0.00"/>                
                </span>                
                <a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/order?action=checkout">去结算 →</a>            
            </div>        
        </c:otherwise>    
    </c:choose>
</div>
<div class="footer">© 2026 <span>电子商城</span> electronicMall · JSP 实训项目</div>
</body>
</html>
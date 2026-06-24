<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>我的订单 - 电子商城</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .pay-btn {
            display: inline-flex; align-items: center; gap: 4px;
            padding: 6px 18px; background: linear-gradient(135deg, #f39c12, #e67e22);
            color: white; border-radius: 20px; font-size: 13px; font-weight: 600;
            box-shadow: 0 2px 8px rgba(243,156,18,0.4); transition: all 0.2s;
            text-decoration: none;
        }
        .pay-btn:hover { box-shadow: 0 4px 14px rgba(243,156,18,0.5); transform: translateY(-1px); }
    </style>
</head>
<body>
<div class="navbar">    
    <a class="logo" href="${pageContext.request.contextPath}/product">🛒 电子商城</a>    
    <div class="nav-links">        
        <a href="${pageContext.request.contextPath}/product">首页</a>        
        <a href="${pageContext.request.contextPath}/cart?action=list">购物车</a>        
        <a href="${pageContext.request.contextPath}/user?action=logout">退出(${sessionScope.loginUser.username})</a>    
    </div>
</div>

<div class="main-content" style="margin-top:32px;">    
    <div class="section-title">📋 我的订单</div>    
    
    <c:if test="${sessionScope.paySuccessOrderId != null}">
        <div style="background:#d4edda; border:1px solid #c3e6cb; border-radius:8px; padding:14px 20px; margin-bottom:20px; color:#155724; font-size:14px; font-weight:600;">
            🚀 尊贵的会员，您的订单 <strong>#${sessionScope.paySuccessOrderId}</strong> 线上付款成功！仓储中心正在加急配货！
        </div>
        <c:remove var="paySuccessOrderId" scope="session"/>
    </c:if>

    <c:choose>        
        <c:when test="${empty orderList}">            
            <div class="empty-state">                
                <div class="empty-icon">📋</div>                
                <p>还没有任何订单，快去选购吧～</p>                
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/product">去购物</a>            
            </div>        
        </c:when>        
        <c:otherwise>            
            <c:forEach var="order" items="${orderList}">                
                <div class="order-card">                    
                    <div class="order-header">                        
                        <span class="order-id">订单号：#${order.orderId}</span>                        
                        <span class="order-time">                            
                            <fmt:formatDate value="${order.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>                        
                        </span>                        
                        <span class="order-status status-${order.status}">${order.statusText}</span>                        
                        <span class="order-total">                            
                            合计：¥<fmt:formatNumber value="${order.totalPrice}" pattern="#,##0.00"/>                        
                        </span>                    
                    </div>                     
                    
                    <div class="order-body">
                        <c:choose>
                            <c:when test="${empty order.items}">
                                <div style="color:#bbb; font-size:13px; padding:15px 0; text-align:center;">⚠️ 历史早期测试订单，无商品明细快照</div>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="item" items="${order.items}">                            
                                    <div class="order-item-row">                                
                                        <c:choose>                                    
                                            <c:when test="${fn:startsWith(item.imgUrl, 'http')}">                                        
                                                <img src="${item.imgUrl}" onerror="this.onerror=null;this.src='https://images.unsplash.com/photo-1472851294608-062f824d29cc?w=200';" alt="${item.productName}">                                    
                                            </c:when>                                    
                                            <c:otherwise>                                         
                                                <img src="${pageContext.request.contextPath}${item.imgUrl}" onerror="this.onerror=null;this.src='https://images.unsplash.com/photo-1472851294608-062f824d29cc?w=200';" alt="${item.productName}">                                    
                                            </c:otherwise>                                
                                        </c:choose>                                
                                        <span class="order-item-name">${item.productName}</span>                                
                                        <span class="order-item-qty">× ${item.quantity}</span>                                
                                        <span class="order-item-price">                                    
                                            ¥<fmt:formatNumber value="${item.price}" pattern="#,##0.00"/>                                
                                        </span>                            
                                    </div>                        
                                </c:forEach>                     
                            </c:otherwise>
                        </c:choose>
                    </div>                    
                    
                    <div class="order-footer" style="display:flex; justify-content:space-between; align-items:center;">
                        <span>📍 收货地址：${order.address}</span>
                        <div style="display:flex; gap:10px; align-items:center;">
                            <c:if test="${order.status == 0}">
                                <a class="pay-btn" href="${pageContext.request.contextPath}/order?action=pay&orderId=${order.orderId}" onclick="return confirm('确认要模拟调起第三方支付网关进行付款吗？')">
                                    💳 去付款
                                </a>
                            </c:if>
                            <c:if test="${order.status == 1}">
                                <span style="font-size:13px; color:#27ae60; font-weight:600;">🟢 已付款，等待总仓出库发货</span>
                            </c:if>
                            <c:if test="${order.status == 2}">
                                <span style="font-size:13px; color:#1976d2; font-weight:600;">🚚 顺丰速运配送中</span>
                            </c:if>
                            <c:if test="${order.status == 3}">
                                <span style="font-size:13px; color:#7b1fa2; font-weight:600;">🎁 履约完毕，交易成功</span>
                            </c:if>
                        </div>
                    </div>                
                </div>            
            </c:forEach>        
        </c:otherwise>    
    </c:choose>
</div>
<div class="footer">© 2026 <span>电子商城</span> electronicMall · JSP 实训项目</div>
</body>
</html>
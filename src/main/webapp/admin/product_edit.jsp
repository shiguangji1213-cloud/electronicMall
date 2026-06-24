<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>编辑商品 - 后台</title>
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
        <a href="${pageContext.request.contextPath}/user?action=logout">退出</a>
    </div>
</div>
<div class="admin-layout">
    <div class="sidebar">
        <a href="${pageContext.request.contextPath}/admin">📊 控制台</a>
        <a href="${pageContext.request.contextPath}/admin?action=productList" class="active">📦 商品管理</a>
        <a href="${pageContext.request.contextPath}/admin?action=orderList">📋 订单管理</a>
    </div>
    <div class="admin-content">
        <div class="form-container" style="margin:0;max-width:600px;">
            <h2>编辑商品</h2>
            <form action="${pageContext.request.contextPath}/admin" method="post">
                <input type="hidden" name="action"    value="productEdit">
                <input type="hidden" name="productId" value="${product.productId}">
                <div class="form-group">
                    <label>商品名称 *</label>
                    <input type="text" name="productName" value="${product.productName}" required>
                </div>
                <div class="form-group">
                    <label>商品分类 *</label>
                    <select name="categoryId" required>
                        <c:forEach var="cat" items="${categories}">
                            <option value="${cat.categoryId}"
                                ${cat.categoryId == product.categoryId ? 'selected' : ''}>
                                ${cat.categoryName}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label>价格 *</label>
                    <input type="number" name="price" step="0.01" value="${product.price}" required>
                </div>
                <div class="form-group">
                    <label>库存 *</label>
                    <input type="number" name="stock" value="${product.stock}" required>
                </div>
                <div class="form-group">
                    <label>图片路径</label>
                    <input type="text" name="imgUrl" value="${product.imgUrl}">
                </div>
                <div class="form-group">
                    <label>状态</label>
                    <select name="status">
                        <option value="1" ${product.status==1?'selected':''}>上架</option>
                        <option value="0" ${product.status==0?'selected':''}>下架</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>商品描述</label>
                    <textarea name="description" rows="4">${product.description}</textarea>
                </div>
                <button class="form-submit" type="submit">保存修改</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
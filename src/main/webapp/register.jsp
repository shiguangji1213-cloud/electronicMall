<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>注册 - 电子商城</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body style="background:linear-gradient(135deg,#fff5f5 0%,#f0f2f5 100%);min-height:100vh;">
<div style="text-align:center;padding:40px 0 0;">
    <a href="${pageContext.request.contextPath}/product"
       style="font-size:26px;font-weight:800;color:#e74c3c;letter-spacing:1px;">
        🛒 电子商城
    </a>
</div>
<div class="form-container">
    <h2>创建账号</h2>
    <c:if test="${errorMsg != null}">
        <div class="msg-error">⚠️ ${errorMsg}</div>
    </c:if>
    <form action="${pageContext.request.contextPath}/user" method="post">
        <input type="hidden" name="action" value="register">
        <div class="form-group">
            <label>用户名 <span style="color:#e74c3c;">*</span></label>
            <input type="text" name="username" placeholder="4~20位字母或数字" required>
        </div>
        <div class="form-group">
            <label>密码 <span style="color:#e74c3c;">*</span></label>
            <input type="password" name="password" placeholder="至少6位" required>
        </div>
        <div class="form-group">
            <label>邮箱</label>
            <input type="email" name="email" placeholder="选填，用于找回密码">
        </div>
        <div class="form-group">
            <label>手机号</label>
            <input type="tel" name="phone" placeholder="选填">
        </div>
        <button class="form-submit" type="submit">注 册</button>
    </form>
    <div class="form-link">已有账号？<a href="${pageContext.request.contextPath}/login.jsp">立即登录</a></div>
</div>
</body>
</html>
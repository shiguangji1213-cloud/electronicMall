<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>登录 - 电子商城</title>
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
    <h2>欢迎回来</h2>
    <c:if test="${errorMsg != null}">
        <div class="msg-error">⚠️ ${errorMsg}</div>
    </c:if>
    <c:if test="${successMsg != null}">
        <div class="msg-success">✅ ${successMsg}</div>
    </c:if>
    <form action="${pageContext.request.contextPath}/user" method="post">
        <input type="hidden" name="action" value="login">
        <div class="form-group">
            <label>用户名</label>
            <input type="text" name="username" placeholder="请输入用户名" required autofocus>
        </div>
        <div class="form-group">
            <label>密码</label>
            <input type="password" name="password" placeholder="请输入密码" required>
        </div>
        <div class="form-group">
            <label>验证码</label>
            <div class="verify-row">
                <input type="text" name="verifyCode" placeholder="请输入验证码" required maxlength="4">
                <img src="${pageContext.request.contextPath}/verifyCode"
                     title="点击刷新"
                     onclick="this.src='${pageContext.request.contextPath}/verifyCode?t='+new Date().getTime()">
            </div>
        </div>
        <button class="form-submit" type="submit">登 录</button>
    </form>
    <div class="form-link">还没有账号？<a href="${pageContext.request.contextPath}/register.jsp">立即注册</a></div>
</div>
</body>
</html>
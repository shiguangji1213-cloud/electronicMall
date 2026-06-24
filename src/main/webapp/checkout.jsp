<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>确认订单 - 电子商城</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="navbar">
    <a class="logo" href="${pageContext.request.contextPath}/product">🛒 电子商城</a>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/cart?action=list">← 返回购物车</a>
    </div>
</div>

<div class="checkout-wrap">
    <div class="checkout-card">
        <h2>📦 填写收货信息</h2>
        <form action="${pageContext.request.contextPath}/order" method="post">
            <input type="hidden" name="action" value="submit">
            <div class="form-group">
                <label>收货人姓名 <span style="color:#e74c3c;">*</span></label>
                <input type="text" name="receiverName" placeholder="请输入收货人真实姓名" required>
            </div>
            <div class="form-group">
                <label>联系电话 <span style="color:#e74c3c;">*</span></label>
                <input type="tel" name="phone" placeholder="请输入手机号码" required>
            </div>
            <div class="form-group">
                <label>详细地址 <span style="color:#e74c3c;">*</span></label>
                <input type="text" name="address"
                       placeholder="省 / 市 / 区 / 街道 / 门牌号" required>
            </div>
            <div class="form-group">
                <label>支付方式</label>
                <select name="payType">
                    <option value="1">💳 在线支付</option>
                    <option value="2">💵 货到付款</option>
                </select>
            </div>
            <div style="margin-top:8px;">
                <button class="form-submit" type="submit">✅ 确认提交订单</button>
            </div>
        </form>
    </div>
</div>

<div class="footer">© 2026 <span>电子商城</span> electronicMall · JSP 实训项目</div>
</body>
</html>
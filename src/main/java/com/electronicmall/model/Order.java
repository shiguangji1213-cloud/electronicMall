package com.electronicmall.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Order {
    private int orderId;
    private int userId;
    private BigDecimal totalPrice;
    private int status;   // 0待付款 1已付款 2已发货 3已完成
    private String address;
    private Date createTime;

    // 关联查询用
    private List<OrderItem> items;
    private String username;

    public Order() {}

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    // 状态文字描述（方便JSP用EL直接显示）
    public String getStatusText() {
        switch (status) {
            case 0: return "待付款";
            case 1: return "已付款";
            case 2: return "已发货";
            case 3: return "已完成";
            default: return "未知";
        }
    }
}
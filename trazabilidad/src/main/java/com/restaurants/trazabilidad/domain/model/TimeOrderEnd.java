package com.restaurants.trazabilidad.domain.model;

public class TimeOrderEnd {
    private Integer orderId;
    private String elapsedTime;
    private OrderStatus status;

    public TimeOrderEnd() {
    }

    public TimeOrderEnd(Integer orderId, String elapsedTime, OrderStatus status) {
        this.orderId = orderId;
        this.elapsedTime = elapsedTime;
        this.status = status;
    }

    public String getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(String elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}

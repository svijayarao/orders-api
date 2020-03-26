package com.hmrc.ecom.orders.model;

import java.util.Objects;

public class Order {

    private String orderId;
    private String orderDesc;
    private Double orderTotal;
    private String itemSku;
    private int numberOfUnits;

    public Order() {
    }

    // This constructor is created for ease of adding data
    public Order(String orderId, String orderDesc, double orderTotal, String itemSku, int numberOfUnits) {
        this.orderId = orderId;
        this.orderDesc = orderDesc;
        this.orderTotal = orderTotal;
        this.itemSku = itemSku;
        this.numberOfUnits = numberOfUnits;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public Double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getItemSku() {
        return itemSku;
    }

    public void setItemSku(String itemSku) {
        this.itemSku = itemSku;
    }

    public int getNumberOfUnits() {
        return numberOfUnits;
    }

    public void setNumberOfUnits(int numberOfUnits) {
        this.numberOfUnits = numberOfUnits;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("orderId=").append(orderId);
        sb.append(", orderDesc='").append(orderDesc).append('\'');
        sb.append(", orderTotal=").append(orderTotal);
        sb.append(", itemSku='").append(itemSku).append('\'');
        sb.append(", numberOfUnits=").append(numberOfUnits);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId &&
                itemSku == order.itemSku &&
                numberOfUnits == order.numberOfUnits &&
                Objects.equals(orderDesc, order.orderDesc) &&
                Objects.equals(orderTotal, order.orderTotal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, orderDesc, orderTotal, itemSku, numberOfUnits);
    }
}

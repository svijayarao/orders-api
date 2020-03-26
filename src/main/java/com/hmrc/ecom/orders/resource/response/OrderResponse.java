package com.hmrc.ecom.orders.resource.response;

public class OrderResponse {
    private String orderId;
    private String orderDesc;
    private Double orderTotal;
    private String itemSku;
    private int numberOfUnits;

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
        final StringBuilder sb = new StringBuilder("OrderResponse{");
        sb.append("orderId='").append(orderId).append('\'');
        sb.append(", orderDesc='").append(orderDesc).append('\'');
        sb.append(", orderTotal=").append(orderTotal);
        sb.append(", itemSku='").append(itemSku).append('\'');
        sb.append(", numberOfUnits=").append(numberOfUnits);
        sb.append('}');
        return sb.toString();
    }
}

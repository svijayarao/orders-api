package com.hmrc.ecom.orders.resource.request;

public class OrderRequest {

    private String orderDesc;
    private String itemSku;
    private int numberOfUnits;

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
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
        final StringBuilder sb = new StringBuilder("OrderRequest{");
        sb.append("orderDesc='").append(orderDesc).append('\'');
        sb.append(", itemSku='").append(itemSku).append('\'');
        sb.append(", numberOfUnits=").append(numberOfUnits);
        sb.append('}');
        return sb.toString();
    }
}

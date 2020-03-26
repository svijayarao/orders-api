package com.hmrc.ecom.orders.remote;

public class Item {

    private Long id;
    private String sku;
    private String itemDescription;
    private double price;

    public Item() {
    }

    public Item(Long id, String sku, String itemDescription, double price) {
        this.id = id;
        this.sku = sku;
        this.itemDescription = itemDescription;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

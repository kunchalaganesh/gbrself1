package com.futureproducts.gbrself.models;

public class myordersmodel {
    String order_no, order_total, ret_name, product_name, sku, brand, qty, price, item_total;

    public myordersmodel() {
    }

    public myordersmodel(String order_no, String order_total, String ret_name, String product_name, String sku, String brand, String qty, String price, String item_total) {
        this.order_no = order_no;
        this.order_total = order_total;
        this.ret_name = ret_name;
        this.product_name = product_name;
        this.sku = sku;
        this.brand = brand;
        this.qty = qty;
        this.price = price;
        this.item_total = item_total;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getOrder_total() {
        return order_total;
    }

    public void setOrder_total(String order_total) {
        this.order_total = order_total;
    }

    public String getRet_name() {
        return ret_name;
    }

    public void setRet_name(String ret_name) {
        this.ret_name = ret_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getItem_total() {
        return item_total;
    }

    public void setItem_total(String item_total) {
        this.item_total = item_total;
    }
}

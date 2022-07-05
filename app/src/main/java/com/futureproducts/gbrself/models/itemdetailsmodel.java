package com.futureproducts.gbrself.models;

public class itemdetailsmodel {
    String smodel ,scolor ,simei1, simei2 ,item_price ,srno ,sku , brand , seconday_direct, tertialry_direct, qty, value;

    public itemdetailsmodel() {
    }

    public String getSmodel() {
        return smodel;
    }

    public itemdetailsmodel(String smodel, String scolor, String simei1, String simei2, String item_price, String srno, String sku, String brand,
                            String seconday_direct, String tertialry_direct, String qty, String value) {
        this.smodel = smodel;
        this.scolor = scolor;
        this.simei1 = simei1;
        this.simei2 = simei2;
        this.item_price = item_price;
        this.srno = srno;
        this.sku = sku;
        this.brand = brand;
        this.seconday_direct = seconday_direct;
        this.tertialry_direct = tertialry_direct;
        this.qty = qty;
        this.value = value;
    }

    public void setSmodel(String smodel) {
        this.smodel = smodel;
    }

    public String getScolor() {
        return scolor;
    }

    public void setScolor(String scolor) {
        this.scolor = scolor;
    }

    public String getSimei1() {
        return simei1;
    }

    public void setSimei1(String simei1) {
        this.simei1 = simei1;
    }

    public String getSimei2() {
        return simei2;
    }

    public void setSimei2(String simei2) {
        this.simei2 = simei2;
    }

    public String getItem_price() {
        return item_price;
    }

    public void setItem_price(String item_price) {
        this.item_price = item_price;
    }

    public String getSrno() {
        return srno;
    }

    public void setSrno(String srno) {
        this.srno = srno;
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

    public String getSeconday_direct() {
        return seconday_direct;
    }

    public void setSeconday_direct(String seconday_direct) {
        this.seconday_direct = seconday_direct;
    }

    public String getTertialry_direct() {
        return tertialry_direct;
    }

    public void setTertialry_direct(String tertialry_direct) {
        this.tertialry_direct = tertialry_direct;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

package com.futureproducts.gbrself.models;

public class imeimodel {
    String imei1, imei2;

    public imeimodel() {
    }

    public imeimodel(String imei1, String imei2) {
        this.imei1 = imei1;
        this.imei2 = imei2;
    }

    public String getImei1() {
        return imei1;
    }

    public void setImei1(String imei1) {
        this.imei1 = imei1;
    }

    public String getImei2() {
        return imei2;
    }

    public void setImei2(String imei2) {
        this.imei2 = imei2;
    }
}

package com.example.administrator.myapplication13.bean;

public class EstablishBean {


    private int commodityId;
    private int amount;

    public EstablishBean(int commodityId, int amount) {
        this.commodityId = commodityId;
        this.amount = amount;
    }

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}

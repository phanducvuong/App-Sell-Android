package com.example.sellapp.Model.OrderModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Order {

    @SerializedName("data")
    @Expose
    private List<ListOrder> mOrder = null;

    @SerializedName("message")
    @Expose
    private String mMessage;

    @SerializedName("order_id")
    @Expose
    private String mOrder_id;

    public String getmOrder_id() {
        return mOrder_id;
    }

    public void setmOrder_id(String mOrder_id) {
        this.mOrder_id = mOrder_id;
    }

    public List<ListOrder> getmOrder() {
        return mOrder;
    }

    public void setmOrder(List<ListOrder> mOrder) {
        this.mOrder = mOrder;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }
}

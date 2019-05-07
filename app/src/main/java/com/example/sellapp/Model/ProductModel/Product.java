package com.example.sellapp.Model.ProductModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {

    @SerializedName("data")
    @Expose
    private List<ListProduct> mProduct = null;

    @SerializedName("message")
    @Expose
    private String mMessage;

    public List<ListProduct> getmProduct() {
        return mProduct;
    }

    public void setmProduct(List<ListProduct> mProduct) {
        this.mProduct = mProduct;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }
}

package com.example.sellapp.Model.ProductModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductDetail {

    @SerializedName("data")
    @Expose
    private ListProduct ProductById;

    public ListProduct getProductById() {
        return ProductById;
    }

    public void setProductById(ListProduct productById) {
        ProductById = productById;
    }
}

package com.example.sellapp.Model.PromoModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListProductPromo {

    @SerializedName("_id")
    @Expose
    private String mId;

    @SerializedName("product_name")
    @Expose
    private String productName;

    @SerializedName("product_price")
    @Expose
    private Integer productPrice;

    @SerializedName("product_pic_big")
    @Expose
    private String productPicBig;

    @SerializedName("product_detail")
    @Expose
    private String productDetail;

    @SerializedName("product_quantity")
    @Expose
    private Integer productQuantity;

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductPicBig() {
        return productPicBig;
    }

    public void setProductPicBig(String productPicBig) {
        this.productPicBig = productPicBig;
    }

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }
}

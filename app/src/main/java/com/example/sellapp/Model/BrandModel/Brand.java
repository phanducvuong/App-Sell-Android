package com.example.sellapp.Model.BrandModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Brand {

    @SerializedName("data")
    @Expose
    private List<ListBrand> mBrand = null;

    @SerializedName("message")
    @Expose
    private String mMessage;

    public List<ListBrand> getmBrand() {
        return mBrand;
    }

    public void setmBrand(List<ListBrand> mBrand) {
        this.mBrand = mBrand;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }
}

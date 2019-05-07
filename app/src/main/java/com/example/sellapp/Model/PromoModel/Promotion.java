package com.example.sellapp.Model.PromoModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Promotion {

    @SerializedName("data")
    @Expose
    private List<ListPromotion> mPromotion = null;

    @SerializedName("message")
    @Expose
    private String mMessage;

    public List<ListPromotion> getmPromotion() {
        return mPromotion;
    }

    public void setmPromotion(List<ListPromotion> mPromotion) {
        this.mPromotion = mPromotion;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }
}

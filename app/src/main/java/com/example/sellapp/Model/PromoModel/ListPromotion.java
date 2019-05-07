package com.example.sellapp.Model.PromoModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListPromotion {

    @SerializedName("name")
    @Expose
    private String mName;

    @SerializedName("date_start")
    @Expose
    private String mDateStart;

    @SerializedName("date_end")
    @Expose
    private String mDateEnd;

    @SerializedName("precent")
    @Expose
    private Integer mPrecent;

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmDateStart() {
        return mDateStart;
    }

    public void setmDateStart(String mDateStart) {
        this.mDateStart = mDateStart;
    }

    public String getmDateEnd() {
        return mDateEnd;
    }

    public void setmDateEnd(String mDateEnd) {
        this.mDateEnd = mDateEnd;
    }

    public Integer getmPrecent() {
        return mPrecent;
    }

    public void setmPrecent(Integer mPrecent) {
        this.mPrecent = mPrecent;
    }
}

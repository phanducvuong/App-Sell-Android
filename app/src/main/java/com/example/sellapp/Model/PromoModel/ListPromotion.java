package com.example.sellapp.Model.PromoModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListPromotion {

    @SerializedName("_id")
    @Expose
    private String mId;

    @SerializedName("name")
    @Expose
    private String mName;

    @SerializedName("date_start")
    @Expose
    private String mDateStart;

    @SerializedName("date_end")
    @Expose
    private String mDateEnd;

    @SerializedName("percent")
    @Expose
    private Integer mPrecent;

    @SerializedName("product_id")
    @Expose
    private List<ListProductPromo> mListProductPromo;

    public List<ListProductPromo> getmListProductPromo() {
        return mListProductPromo;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public void setmListProductPromo(List<ListProductPromo> mListProductPromo) {
        this.mListProductPromo = mListProductPromo;
    }

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

package com.example.sellapp.Model.ProductModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CateId {

    @SerializedName("_id")
    @Expose
    private String mId;

    @SerializedName("cate_name")
    @Expose
    private String mCateName;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getmCateName() {
        return mCateName;
    }

    public void setmCateName(String mCateName) {
        this.mCateName = mCateName;
    }
}

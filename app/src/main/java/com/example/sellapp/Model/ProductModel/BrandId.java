package com.example.sellapp.Model.ProductModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BrandId {

    @SerializedName("_id")
    @Expose
    private String mId;

    @SerializedName("brand_name")
    @Expose
    private String mBrandName;

    @SerializedName("brand_pic")
    @Expose
    private String mBrandPic;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getmBrandName() {
        return mBrandName;
    }

    public void setmBrandName(String mBrandName) {
        this.mBrandName = mBrandName;
    }

    public String getmBrandPic() {
        return mBrandPic;
    }

    public void setmBrandPic(String mBrandPic) {
        this.mBrandPic = mBrandPic;
    }
}

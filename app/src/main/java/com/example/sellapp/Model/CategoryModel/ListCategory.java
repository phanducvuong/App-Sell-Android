package com.example.sellapp.Model.CategoryModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListCategory {

    @SerializedName("_id")
    @Expose
    private String mId;

    @SerializedName("cate_name")
    @Expose
    private String mCateName;

    @SerializedName("cate_pic")
    @Expose
    private String mCatePic;

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

    public String getmCatePic() {
        return mCatePic;
    }

    public void setmCatePic(String mCatePic) {
        this.mCatePic = mCatePic;
    }
}

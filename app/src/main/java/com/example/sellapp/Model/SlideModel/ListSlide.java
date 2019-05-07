package com.example.sellapp.Model.SlideModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListSlide {

    @SerializedName("_id")
    @Expose
    private String mId;

    @SerializedName("slide_name")
    @Expose
    private String mSlideName;

    @SerializedName("slide_img")
    @Expose
    private String mSlideImg;

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmSlideName() {
        return mSlideName;
    }

    public void setmSlideName(String mSlideName) {
        this.mSlideName = mSlideName;
    }

    public String getmSlideImg() {
        return mSlideImg;
    }

    public void setmSlideImg(String mSlideImg) {
        this.mSlideImg = mSlideImg;
    }
}

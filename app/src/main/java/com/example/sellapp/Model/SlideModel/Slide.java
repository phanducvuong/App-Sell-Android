package com.example.sellapp.Model.SlideModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Slide {

    @SerializedName("data")
    @Expose
    private List<ListSlide> mListSlide;

    @SerializedName("message")
    @Expose
    private String mMessage;

    public List<ListSlide> getmListSlide() {
        return mListSlide;
    }

    public void setmListSlide(List<ListSlide> mListSlide) {
        this.mListSlide = mListSlide;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }
}

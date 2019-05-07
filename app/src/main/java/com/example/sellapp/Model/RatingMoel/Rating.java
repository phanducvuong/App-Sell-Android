package com.example.sellapp.Model.RatingMoel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Rating {

    @SerializedName("data")
    @Expose
    private List<ListRating> mRating = null;

    @SerializedName("message")
    @Expose
    private String mMessage;

    public List<ListRating> getmRating() {
        return mRating;
    }

    public void setmRating(List<ListRating> mRating) {
        this.mRating = mRating;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }
}

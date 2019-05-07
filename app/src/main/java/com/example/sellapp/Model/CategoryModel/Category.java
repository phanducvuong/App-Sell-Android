package com.example.sellapp.Model.CategoryModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Category {

    @SerializedName("data")
    @Expose
    private List<ListCategory> mCategory = null;

    @SerializedName("message")
    @Expose
    private String mMessage;

    public List<ListCategory> getmCategory() {
        return mCategory;
    }

    public void setmCategory(List<ListCategory> mCategory) {
        this.mCategory = mCategory;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }
}

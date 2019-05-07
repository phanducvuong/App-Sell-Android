package com.example.sellapp.Model.CommentModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Comment {

    @SerializedName("data")
    @Expose
    private List<ListComment> mComment = null;

    @SerializedName("message")
    @Expose
    private String mMessage;

    public List<ListComment> getmComment() {
        return mComment;
    }

    public void setmComment(List<ListComment> mComment) {
        this.mComment = mComment;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }
}

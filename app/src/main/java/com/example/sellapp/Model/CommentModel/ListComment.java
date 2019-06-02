package com.example.sellapp.Model.CommentModel;

import com.example.sellapp.Model.EmployeeModel.ListEmployee;
import com.example.sellapp.Model.ProductModel.ListProduct;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListComment {

    @SerializedName("_id")
    @Expose
    private String mId;

    @SerializedName("comment_id")
    @Expose
    private String mCommentId;

    @SerializedName("product_id")
    @Expose
    private ListProduct mProduct;

    @SerializedName("device_name")
    @Expose
    private String mDeviceName;

    @SerializedName("title")
    @Expose
    private String mTitle;

    @SerializedName("content")
    @Expose
    private String mContent;

    @SerializedName("number_star")
    @Expose
    private String mNumberStar;

    @SerializedName("date_comment")
    @Expose
    private String mDateComment;

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmCommentId() {
        return mCommentId;
    }

    public void setmCommentId(String mCommentId) {
        this.mCommentId = mCommentId;
    }

    public ListProduct getmProduct() {
        return mProduct;
    }

    public void setmProduct(ListProduct mProduct) {
        this.mProduct = mProduct;
    }

    public String getmDeviceName() {
        return mDeviceName;
    }

    public void setmDeviceName(String mDeviceName) {
        this.mDeviceName = mDeviceName;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public String getmNumberStar() {
        return mNumberStar;
    }

    public void setmNumberStar(String mNumberStar) {
        this.mNumberStar = mNumberStar;
    }

    public String getmDateComment() {
        return mDateComment;
    }

    public void setmDateComment(String mDateComment) {
        this.mDateComment = mDateComment;
    }
}

package com.example.sellapp.Model.CommentModel;

import com.example.sellapp.Model.EmployeeModel.ListEmployee;
import com.example.sellapp.Model.ProductModel.ListProduct;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListComment {

    @SerializedName("_id")
    @Expose
    private String mId;

    @SerializedName("employee_id")
    @Expose
    private ListEmployee mEmployee;

    @SerializedName("product_id")
    @Expose
    private ListProduct mProduct;

    @SerializedName("content")
    @Expose
    private String mContent;

    @SerializedName("date_comment")
    @Expose
    private String mDateComment;

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public ListEmployee getmEmployee() {
        return mEmployee;
    }

    public void setmEmployee(ListEmployee mEmployee) {
        this.mEmployee = mEmployee;
    }

    public ListProduct getmProduct() {
        return mProduct;
    }

    public void setmProduct(ListProduct mProduct) {
        this.mProduct = mProduct;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public String getmDateComment() {
        return mDateComment;
    }

    public void setmDateComment(String mDateComment) {
        this.mDateComment = mDateComment;
    }
}

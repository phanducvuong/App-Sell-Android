package com.example.sellapp.Model.RatingMoel;

import com.example.sellapp.Model.EmployeeModel.ListEmployee;
import com.example.sellapp.Model.ProductModel.ListProduct;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListRating {

    @SerializedName("product_id")
    @Expose
    private ListProduct mProduct;

    @SerializedName("employee_id")
    @Expose
    private ListEmployee mEmployee;

    @SerializedName("content")
    @Expose
    private String mContent;

    @SerializedName("number_star")
    @Expose
    private String mNumberStar;

    public ListProduct getmProduct() {
        return mProduct;
    }

    public void setmProduct(ListProduct mProduct) {
        this.mProduct = mProduct;
    }

    public ListEmployee getmEmployee() {
        return mEmployee;
    }

    public void setmEmployee(ListEmployee mEmployee) {
        this.mEmployee = mEmployee;
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
}

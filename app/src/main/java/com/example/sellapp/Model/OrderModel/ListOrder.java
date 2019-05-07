package com.example.sellapp.Model.OrderModel;

import com.example.sellapp.Model.EmployeeModel.ListEmployee;
import com.example.sellapp.Model.ProductModel.ListProduct;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListOrder {

    @SerializedName("employee_id")
    @Expose
    private ListEmployee mEmployeeId;

    @SerializedName("product_id")
    @Expose
    private ListProduct mProduct;

    @SerializedName("status")
    @Expose
    private Boolean mStatus;

    @SerializedName("address_reci")
    @Expose
    private String mAddressReci;

    @SerializedName("order_date")
    @Expose
    private String mOrderDate;

    @SerializedName("delivery_date")
    @Expose
    private String mDeliveryDate;

    @SerializedName("quantity")
    @Expose
    private Integer mQuantity;

    @SerializedName("description")
    @Expose
    private String mDescription;

    public ListEmployee getmEmployeeId() {
        return mEmployeeId;
    }

    public void setmEmployeeId(ListEmployee mEmployeeId) {
        this.mEmployeeId = mEmployeeId;
    }

    public ListProduct getmProduct() {
        return mProduct;
    }

    public void setmProduct(ListProduct mProduct) {
        this.mProduct = mProduct;
    }

    public Boolean getmStatus() {
        return mStatus;
    }

    public void setmStatus(Boolean mStatus) {
        this.mStatus = mStatus;
    }

    public String getmAddressReci() {
        return mAddressReci;
    }

    public void setmAddressReci(String mAddressReci) {
        this.mAddressReci = mAddressReci;
    }

    public String getmOrderDate() {
        return mOrderDate;
    }

    public void setmOrderDate(String mOrderDate) {
        this.mOrderDate = mOrderDate;
    }

    public String getmDeliveryDate() {
        return mDeliveryDate;
    }

    public void setmDeliveryDate(String mDeliveryDate) {
        this.mDeliveryDate = mDeliveryDate;
    }

    public Integer getmQuantity() {
        return mQuantity;
    }

    public void setmQuantity(Integer mQuantity) {
        this.mQuantity = mQuantity;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }
}

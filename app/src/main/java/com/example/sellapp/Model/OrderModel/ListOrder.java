package com.example.sellapp.Model.OrderModel;

import com.example.sellapp.Model.EmployeeModel.ListEmployee;
import com.example.sellapp.Model.ProductModel.ListProduct;
import com.example.sellapp.Model.ProductModel.Product;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListOrder {

    @SerializedName("employee_id")
    @Expose
    private ListEmployee mEmployeeId;

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

    @SerializedName("description")
    @Expose
    private String mDescription;

    @SerializedName("phone_number")
    @Expose
    private String mPhoneNumber;

    private List<ProductOrder> mListProductOrder;

    private String mReceiver;

    private String mNote;

    public String getmNote() {
        return mNote;
    }

    public void setmNote(String mNote) {
        this.mNote = mNote;
    }

    public String getmReceiver() {
        return mReceiver;
    }

    public void setmReceiver(String mReceiver) {
        this.mReceiver = mReceiver;
    }

    public List<ProductOrder> getmListProductOrder() {
        return mListProductOrder;
    }

    public void setmListProductOrder(List<ProductOrder> mListProductOrder) {
        this.mListProductOrder = mListProductOrder;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public ListEmployee getmEmployeeId() {
        return mEmployeeId;
    }

    public void setmEmployeeId(ListEmployee mEmployeeId) {
        this.mEmployeeId = mEmployeeId;
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

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }
}

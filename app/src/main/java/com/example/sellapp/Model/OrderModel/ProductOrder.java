package com.example.sellapp.Model.OrderModel;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductOrder implements Parcelable {

    private String mProductId;
    private int mAmount;
    private int mPercent;

    public ProductOrder(Parcel in) {
        mProductId = in.readString();
        mAmount = in.readInt();
        mPercent = in.readInt();
    }

    public static final Creator<ProductOrder> CREATOR = new Creator<ProductOrder>() {
        @Override
        public ProductOrder createFromParcel(Parcel in) {
            return new ProductOrder(in);
        }

        @Override
        public ProductOrder[] newArray(int size) {
            return new ProductOrder[size];
        }
    };

    public ProductOrder() {

    }

    public int getmPercent() {
        return mPercent;
    }

    public void setmPercent(int mPercent) {
        this.mPercent = mPercent;
    }

    public String getmProductId() {
        return mProductId;
    }

    public void setmProductId(String mProductId) {
        this.mProductId = mProductId;
    }

    public int getmAmount() {
        return mAmount;
    }

    public void setmAmount(int mAmount) {
        this.mAmount = mAmount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mProductId);
        dest.writeInt(mAmount);
        dest.writeInt(mPercent);
    }
}

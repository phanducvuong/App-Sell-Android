package com.example.sellapp.Model.EmployeeModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListEmployee {

    @SerializedName("_id")
    @Expose
    private String mId;

    @SerializedName("username")
    @Expose
    private String mUsername;

    @SerializedName("password")
    @Expose
    private String mPassword;

    @SerializedName("address")
    @Expose
    private String mAddress;

    @SerializedName("birdthday")
    @Expose
    private String mBirdthday;

    @SerializedName("phone")
    @Expose
    private String mPhone;

    @SerializedName("sex")
    @Expose
    private String mSex;

    @SerializedName("identity_card")
    @Expose
    private String mIdentityCard;

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmBirdthday() {
        return mBirdthday;
    }

    public void setmBirdthday(String mBirdthday) {
        this.mBirdthday = mBirdthday;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getmSex() {
        return mSex;
    }

    public void setmSex(String mSex) {
        this.mSex = mSex;
    }

    public String getmIdentityCard() {
        return mIdentityCard;
    }

    public void setmIdentityCard(String mIdentityCard) {
        this.mIdentityCard = mIdentityCard;
    }
}

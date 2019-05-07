package com.example.sellapp.Model.EmployeeModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Employee {

    @SerializedName("data")
    @Expose
    private List<ListEmployee> mEmployee = null;

    @SerializedName("message")
    @Expose
    private String mMessage;

    public List<ListEmployee> getmEmployee() {
        return mEmployee;
    }

    public void setmEmployee(List<ListEmployee> mEmployee) {
        this.mEmployee = mEmployee;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }
}

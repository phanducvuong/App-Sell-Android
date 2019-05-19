package com.example.sellapp.Model.ProductModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuId {

    @SerializedName("_id")
    @Expose
    private String Id;

    @SerializedName("cate_name")
    @Expose
    private String CateMenuName;

    @SerializedName("cate_pic")
    @Expose
    private String CateMenuPic;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCateMenuName() {
        return CateMenuName;
    }

    public void setCateMenuName(String cateMenuName) {
        CateMenuName = cateMenuName;
    }

    public String getCateMenuPic() {
        return CateMenuPic;
    }

    public void setCateMenuPic(String cateMenuPic) {
        CateMenuPic = cateMenuPic;
    }
}

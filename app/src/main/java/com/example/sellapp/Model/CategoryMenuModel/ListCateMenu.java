package com.example.sellapp.Model.CategoryMenuModel;

import com.example.sellapp.Model.CategoryModel.ListCategory;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListCateMenu {

    @SerializedName("_id")
    @Expose
    private String mId;

    @SerializedName("cate_menu_name")
    @Expose
    private String mCateMenuName;

    @SerializedName("cate_menu_pic")
    @Expose
    private String mCateMenuPic;

    @SerializedName("cate_id")
    @Expose
    private ListCategory mListCategory;

    public ListCategory getmListCategory() {
        return mListCategory;
    }

    public void setmListCategory(ListCategory mListCategory) {
        this.mListCategory = mListCategory;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmCateMenuName() {
        return mCateMenuName;
    }

    public void setmCateMenuName(String mCateMenuName) {
        this.mCateMenuName = mCateMenuName;
    }

    public String getmCateMenuPic() {
        return mCateMenuPic;
    }

    public void setmCateMenuPic(String mCateMenuPic) {
        this.mCateMenuPic = mCateMenuPic;
    }
}

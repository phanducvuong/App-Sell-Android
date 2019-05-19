package com.example.sellapp.Model.CategoryMenuModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryMenu {

    @SerializedName("data")
    @Expose
    private List<ListCateMenu> mListCateMenu = null;

    public List<ListCateMenu> getmListCateMenu() {
        return mListCateMenu;
    }

    public void setmListCateMenu(List<ListCateMenu> mListCateMenu) {
        this.mListCateMenu = mListCateMenu;
    }
}

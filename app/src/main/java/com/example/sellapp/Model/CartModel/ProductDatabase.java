package com.example.sellapp.Model.CartModel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProductDatabase extends SQLiteOpenHelper {

    public static String TB_CART = "CART";
    public static String CART_PRODUCT_ID = "ID";
    public static String CART_PRODUCT_NAME = "NAME";
    public static String CART_PRODUCT_PRICE = "PRICE";
    public static String CART_PRODUCT_IMAGE = "IMAGE";
    public static String CART_PRODUCT_AMOUNT = "AMOUNT";

    public static String TB_FAVORITE = "FAVORITE";
    public static String FAVORITE_PRODUCT_ID = "ID";
    public static String FAVORITE_PRODUCT_NAME = "NAME";
    public static String FAVORITE_PRODUCT_PRICE = "PRICE";
    public static String FAVORITE_PRODUCT_IMAGE = "IMAGE";

    public static String TB_ORDER = "TBORDER";
    public static String ORDER_ID = "ID";

    public ProductDatabase(Context context) {
        super(context, "SQLPRODUCT", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tbCart = "CREATE TABLE " + TB_CART + " (" + CART_PRODUCT_ID + " STRING PRIMARY KEY, " +
                CART_PRODUCT_NAME + " TEXT, " + CART_PRODUCT_PRICE + " REAL, " + CART_PRODUCT_IMAGE + " BLOB, " + CART_PRODUCT_AMOUNT + " INTEGER);";

        String tbFavorite = "CREATE TABLE " + TB_FAVORITE + " (" + FAVORITE_PRODUCT_ID + " STRING PRIMARY KEY, " +
                FAVORITE_PRODUCT_NAME + " TEXT, " + FAVORITE_PRODUCT_PRICE + " REAL, " + FAVORITE_PRODUCT_IMAGE + " BLOB);";

        String tbOrder = "CREATE TABLE " + TB_ORDER + " (" + ORDER_ID + " STRING PRIMARY KEY);";

        db.execSQL(tbCart);
        db.execSQL(tbFavorite);
        db.execSQL(tbOrder);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

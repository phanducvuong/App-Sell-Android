package com.example.sellapp.Model.CartModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.sellapp.Model.ProductModel.ListProduct;

import java.util.ArrayList;
import java.util.List;

public class Connect {

    SQLiteDatabase database;

    public void Connection(Context context) {
        ProductDatabase productDatabase = new ProductDatabase(context);
        database = productDatabase.getWritableDatabase();
    }

    public boolean AddToCart(ListProduct product) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(ProductDatabase.CART_PRODUCT_ID, product.getId());
        contentValues.put(ProductDatabase.CART_PRODUCT_NAME, product.getProductName());
        contentValues.put(ProductDatabase.CART_PRODUCT_PRICE, product.getProductPrice());
        contentValues.put(ProductDatabase.CART_PRODUCT_IMAGE, product.getImage());

        long id = database.insert(ProductDatabase.TB_CART, null, contentValues);
        if (id > 0) return true;
        return false;
    }

    public List<ListProduct> GetProductInCart() {
        List<ListProduct> listProducts = new ArrayList<>();

        String query = "SELECT * FROM " + ProductDatabase.TB_CART;
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            String product_id = cursor.getString(cursor.getColumnIndex(ProductDatabase.CART_PRODUCT_ID));
            String product_name = cursor.getString(cursor.getColumnIndex(ProductDatabase.CART_PRODUCT_NAME));
            int product_price = cursor.getInt(cursor.getColumnIndex(ProductDatabase.CART_PRODUCT_PRICE));
            byte[] image = cursor.getBlob(cursor.getColumnIndex(ProductDatabase.CART_PRODUCT_IMAGE));

            ListProduct product = new ListProduct();
            product.setId(product_id);
            product.setProductName(product_name);
            product.setProductPrice(product_price);
            product.setImage(image);

            listProducts.add(product);
            cursor.moveToNext();
        }

        return listProducts;
    }

    public boolean DeleteItemCart(String product_id) {
        int check = database.delete(ProductDatabase.TB_CART, ProductDatabase.CART_PRODUCT_ID + " = " + "'" + product_id + "'", null);
        return check > 0;
    }
}

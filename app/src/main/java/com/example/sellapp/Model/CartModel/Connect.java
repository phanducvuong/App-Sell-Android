package com.example.sellapp.Model.CartModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.sellapp.Model.OrderModel.Order;
import com.example.sellapp.Model.ProductModel.ListProduct;

import java.util.ArrayList;
import java.util.List;

public class Connect {

    SQLiteDatabase database;
    int mAmount;

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

        boolean check = CheckCartId(product.getId());

        if (check) {
            contentValues.put(ProductDatabase.CART_PRODUCT_AMOUNT, 1);
            long id = database.insert(ProductDatabase.TB_CART, null, contentValues);
            if (id > 0) return true;
        }
        mAmount++;
        contentValues.put(ProductDatabase.CART_PRODUCT_AMOUNT, mAmount);
        long _id = database.update(ProductDatabase.TB_CART, contentValues, ProductDatabase.CART_PRODUCT_ID + "=" + "'" + product.getId() + "'", null);
        return _id > 0;
    }

    public boolean AddToOrderId(Order order) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(ProductDatabase.ORDER_ID, order.getmOrder_id());

        long id = database.insert(ProductDatabase.TB_ORDER, null, contentValues);
        return id > 0;
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
            int product_amount = cursor.getInt(cursor.getColumnIndex(ProductDatabase.CART_PRODUCT_AMOUNT));
            byte[] image = cursor.getBlob(cursor.getColumnIndex(ProductDatabase.CART_PRODUCT_IMAGE));

            ListProduct product = new ListProduct();
            product.setId(product_id);
            product.setProductName(product_name);
            product.setProductPrice(product_price);
            product.setAmount(product_amount);
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

    public boolean DeleteAllItemInCart() {
        int check = database.delete(ProductDatabase.TB_CART, null, null);
        database.close();
        return check > 0;
    }

    public boolean CheckCartId(String id) {
        String query = "SELECT * FROM " + ProductDatabase.TB_CART + " WHERE " + ProductDatabase.CART_PRODUCT_ID + " = " + "'" + id + "'";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.getCount() == 0)
            return true;
        mAmount = cursor.getInt(cursor.getColumnIndex(ProductDatabase.CART_PRODUCT_AMOUNT));
        return false;
    }

    public void updateAmountInCart(String product_id, int amount) {
        String query = "SELECT * FROM " + ProductDatabase.TB_CART + " WHERE " + ProductDatabase.CART_PRODUCT_ID + " = " + "'" + product_id + "'";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.getCount() != 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ProductDatabase.CART_PRODUCT_AMOUNT, amount);
            database.update(ProductDatabase.TB_CART, contentValues, ProductDatabase.CART_PRODUCT_ID + "=" + "'" + product_id + "'", null);
        }
    }
}

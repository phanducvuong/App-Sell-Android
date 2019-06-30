package com.example.sellapp.View;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sellapp.Adapter.CartDetailAdapter;
import com.example.sellapp.Model.CartModel.Connect;
import com.example.sellapp.Model.CartModel.ProductDatabase;
import com.example.sellapp.Model.OrderModel.ProductOrder;
import com.example.sellapp.Model.ProductModel.ListProduct;
import com.example.sellapp.Model.ProductModel.Product;
import com.example.sellapp.R;
import com.example.sellapp.Util.OnCheckChange;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Util;

public class CartDetailActivity extends AppCompatActivity {

    Connect mDatabaseConnect;
    RecyclerView mRcvCartDetail;
    CartDetailAdapter mCartDetailAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    ImageView mImgBackCartDetail;
    ImageButton mImgBtnCartDetail;
    TextView mTxtTotalPrice;
    Button mBtnBuyNow;
    int mTotalPrice = 0;
    boolean isCheckAll = false;
    List<String> mListProduct;
    List<ProductOrder> mListProductOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_detail);

        mRcvCartDetail = findViewById(R.id.rcv_cart_detail);
        mImgBackCartDetail = findViewById(R.id.img_btn_back_cart_detail);
        mImgBtnCartDetail = findViewById(R.id.img_all_cart_detail);
        mTxtTotalPrice = findViewById(R.id.txt_total_price_cart_detail);
        mBtnBuyNow = findViewById(R.id.btn_buy_now_cart_detail);

        mTxtTotalPrice.setText("0");

        mDatabaseConnect = new Connect();

        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRcvCartDetail.setLayoutManager(mLayoutManager);
        mRcvCartDetail.setHasFixedSize(true);

        mImgBackCartDetail.setOnClickListener(this::OnClick);
        mImgBtnCartDetail.setOnClickListener(this::OnClick);
        mBtnBuyNow.setOnClickListener(this::OnClick);

        GetCartDetail();
    }

    private void OnClick(View v) {
        switch (v.getId()) {
            case R.id.img_btn_back_cart_detail:
                finish();
                break;
            case R.id.img_all_cart_detail:
                if (isCheckAll) {
                    mImgBtnCartDetail.setImageResource(R.drawable.icon_uncheck_cart);
                    mListProduct = new ArrayList<>();
                    mCartDetailAdapter = new CartDetailAdapter(getListCart(), this, false, 0, mDatabaseConnect, mListProduct);
                    mCartDetailAdapter.setOnCheckChange(this::CheckChange);
                    mRcvCartDetail.setAdapter(mCartDetailAdapter);
                    mCartDetailAdapter.notifyDataSetChanged();
                    isCheckAll = false;
                }else {
                    mImgBtnCartDetail.setImageResource(R.drawable.icon_check_cart);
                    mListProduct = new ArrayList<>();
                    mCartDetailAdapter = new CartDetailAdapter(getListCart(), this, true, 0, mDatabaseConnect, mListProduct);
                    mCartDetailAdapter.setOnCheckChange(this::CheckChange);
                    mRcvCartDetail.setAdapter(mCartDetailAdapter);
                    mCartDetailAdapter.notifyDataSetChanged();
                    isCheckAll = true;
                }
                break;
            case R.id.btn_buy_now_cart_detail:

                //Send list product_id and amount in Cart
                if (this.mListProduct.size() < 1) {
                    this.mListProductOrder = null;
                }else {
                    this.mListProductOrder = new ArrayList<>();
                    for (int i = 0; i < this.mListProduct.size(); i++){
                        for (int j = 0; j < getListCart().size(); j++) {
                            if (this.mListProduct.get(i).equals(getListCart().get(j).getId())) {
                                ProductOrder tempProductOrder = new ProductOrder();
                                tempProductOrder.setmProductId(this.mListProduct.get(i));
                                tempProductOrder.setmAmount(getListCart().get(j).getAmount());
                                this.mListProductOrder.add(tempProductOrder);
                            }
                        }
                    }
                }

                Intent intentBuyNow = new Intent(CartDetailActivity.this, PaymentActivity.class);
                intentBuyNow.putParcelableArrayListExtra("ListProduct", (ArrayList<? extends Parcelable>) this.mListProductOrder);
                startActivity(intentBuyNow);
                break;
        }
    }

    private void GetCartDetail() {
        mCartDetailAdapter = new CartDetailAdapter(getListCart(), this, false, this.mTotalPrice, mDatabaseConnect, mListProduct);
        mCartDetailAdapter.setOnCheckChange(this::CheckChange);
        mRcvCartDetail.setAdapter(mCartDetailAdapter);
        mCartDetailAdapter.notifyDataSetChanged();
    }

    /////////////////////////////////////////////////////////////////////////////
    private List<ListProduct> getListCart() {
        mDatabaseConnect.Connection(this);
        return mDatabaseConnect.GetProductInCart();
    }

    //Callback in adapter
    public void CheckChange(boolean temp, int total_price, List<String> list_product) {
        mTxtTotalPrice.setText(FortmartPrice(total_price));
        this.mTotalPrice = total_price;
        isCheckAll = temp;
        this.mListProduct = list_product;
        isCheckBuyNow(total_price);
        if (temp)
            mImgBtnCartDetail.setImageResource(R.drawable.icon_check_cart);
        else
            mImgBtnCartDetail.setImageResource(R.drawable.icon_uncheck_cart);
    }

    private void isCheckBuyNow(int total_price) {
        if (total_price > 0)
            mBtnBuyNow.setBackground(getDrawable(R.color.colorRedOrange));
        else
            mBtnBuyNow.setBackground(getDrawable(R.color.colorGray));
    }

    //FORTMART PRICE
    private String FortmartPrice(Integer Price) {

        DecimalFormat mDecimalFormat = new DecimalFormat("###,###,###");
        String mPrice = mDecimalFormat.format(Price);

        return mPrice + " VND";
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mCartDetailAdapter = new CartDetailAdapter(getListCart(), this, false, this.mTotalPrice, mDatabaseConnect, mListProduct);
        mCartDetailAdapter.setOnCheckChange(this::CheckChange);
        mRcvCartDetail.setAdapter(mCartDetailAdapter);
        mCartDetailAdapter.notifyDataSetChanged();
        mTxtTotalPrice.setText("0 VND");
    }
}

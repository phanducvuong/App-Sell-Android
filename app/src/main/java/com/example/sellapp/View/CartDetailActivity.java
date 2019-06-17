package com.example.sellapp.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sellapp.Adapter.CartDetailAdapter;
import com.example.sellapp.Model.CartModel.Connect;
import com.example.sellapp.Model.CartModel.ProductDatabase;
import com.example.sellapp.Model.ProductModel.ListProduct;
import com.example.sellapp.R;
import com.example.sellapp.Util.OnCheckChange;

import java.text.DecimalFormat;
import java.util.List;

import okhttp3.internal.Util;

public class CartDetailActivity extends AppCompatActivity {

    Connect mDatabaseConnect;
    RecyclerView mRcvCartDetail;
    CartDetailAdapter mCartDetailAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    ImageView mImgBackCartDetail;
    CheckBox mCbAll;
    TextView mTxtTotalPrice;
    Button mBtnBuyNow;
    int mTotalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_detail);

        mRcvCartDetail = findViewById(R.id.rcv_cart_detail);
        mImgBackCartDetail = findViewById(R.id.img_btn_back_cart_detail);
        mCbAll = findViewById(R.id.cb_choose_all_cart_detail);
        mTxtTotalPrice = findViewById(R.id.txt_total_price_cart_detail);
        mBtnBuyNow = findViewById(R.id.btn_buy_now_cart_detail);

        mDatabaseConnect = new Connect();

        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRcvCartDetail.setLayoutManager(mLayoutManager);
        mRcvCartDetail.setHasFixedSize(true);

        mImgBackCartDetail.setOnClickListener(this::OnClick);
        mCbAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                this.mTotalPrice = 0;
                mCartDetailAdapter = new CartDetailAdapter(getListCart(), this, true, this.mTotalPrice);
                mCartDetailAdapter.setOnCheckChange(this::CheckChange);
                mRcvCartDetail.setAdapter(mCartDetailAdapter);
                mCartDetailAdapter.notifyDataSetChanged();
            }else {
                this.mTotalPrice = 0;
                mCartDetailAdapter = new CartDetailAdapter(getListCart(), this, false, this.mTotalPrice);
                mCartDetailAdapter.setOnCheckChange(this::CheckChange);
                mRcvCartDetail.setAdapter(mCartDetailAdapter);
                mCartDetailAdapter.notifyDataSetChanged();
            }
        });

        GetCartDetail();
    }

    private void OnClick(View v) {
        finish();
    }

    private void GetCartDetail() {
        mCartDetailAdapter = new CartDetailAdapter(getListCart(), this, false, this.mTotalPrice);
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
    public void CheckChange(boolean temp, int total_price) {
        Log.d("BBB", "CheckChange: " + total_price);
        mTxtTotalPrice.setText(FortmartPrice(total_price));
        isCheckBuyNow(total_price);
        if (temp)
            mCbAll.setChecked(true);
        else
            mCbAll.setChecked(false);
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

        return mPrice;
    }
}
package com.example.sellapp.View.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sellapp.Adapter.CartDetailAdapter;
import com.example.sellapp.Model.CartModel.Connect;
import com.example.sellapp.Model.ProductModel.ListProduct;
import com.example.sellapp.R;

import java.text.DecimalFormat;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {

    Connect mDatabaseConnect;
    RecyclerView mRcvCartDetail;
    CartDetailAdapter mCartDetailAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    CheckBox CbItemCart;
    TextView mTxtTotalPrice;
    Button mBtnBuyNow;
    int mTotalPrice = 0;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cart, container, false);

        mRcvCartDetail = v.findViewById(R.id.rcv_cart_detail);
        CbItemCart = v.findViewById(R.id.cb_choose_all_cart_detail);
        mTxtTotalPrice = v.findViewById(R.id.txt_total_price_cart_detail);
        mBtnBuyNow = v.findViewById(R.id.btn_buy_now_cart_detail);

        mDatabaseConnect = new Connect();

        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRcvCartDetail.setLayoutManager(mLayoutManager);
        mRcvCartDetail.setHasFixedSize(true);

        GetCartDetail();

        CbItemCart.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                this.mTotalPrice = 0;
                mCartDetailAdapter = new CartDetailAdapter(getListCart(), getContext(), true, this.mTotalPrice);
                mCartDetailAdapter.setOnCheckChange(this::CheckChange);
                mRcvCartDetail.setAdapter(mCartDetailAdapter);
                mCartDetailAdapter.notifyDataSetChanged();
            }else {
                this.mTotalPrice = 0;
                mCartDetailAdapter = new CartDetailAdapter(getListCart(), getContext(), false, this.mTotalPrice);
                mCartDetailAdapter.setOnCheckChange(this::CheckChange);
                mRcvCartDetail.setAdapter(mCartDetailAdapter);
                mCartDetailAdapter.notifyDataSetChanged();
            }
        });

        return v;
    }

    private void GetCartDetail() {
        mCartDetailAdapter = new CartDetailAdapter(getListCart(), getContext(), false, this.mTotalPrice);
        mCartDetailAdapter.setOnCheckChange(this::CheckChange);
        mRcvCartDetail.setAdapter(mCartDetailAdapter);
        mCartDetailAdapter.notifyDataSetChanged();
    }

    /////////////////////////////////////////////////////////////////////////////
    private List<ListProduct> getListCart() {
        mDatabaseConnect.Connection(getContext());
        return mDatabaseConnect.GetProductInCart();
    }

    //Callback in adapter
    public void CheckChange(boolean temp, int total_price) {
        mTxtTotalPrice.setText(FortmartPrice(total_price));
        isCheckBuyNow(total_price);
        if (temp)
        {
            CbItemCart.setChecked(true);
        }
        else{
            CbItemCart.setChecked(false);
        }
    }

    private void isCheckBuyNow(int total_price) {
        if (total_price > 0)
            mBtnBuyNow.setBackground(getActivity().getDrawable(R.color.colorRedOrange));
        else
            mBtnBuyNow.setBackground(getActivity().getDrawable(R.color.colorGray));
    }

    //FORTMART PRICE
    private String FortmartPrice(Integer Price) {

        DecimalFormat mDecimalFormat = new DecimalFormat("###,###,###");
        String mPrice = mDecimalFormat.format(Price);

        return mPrice;
    }
}

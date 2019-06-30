package com.example.sellapp.View.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.example.sellapp.Model.OrderModel.ProductOrder;
import com.example.sellapp.Model.ProductModel.ListProduct;
import com.example.sellapp.R;
import com.example.sellapp.View.CartDetailActivity;
import com.example.sellapp.View.PaymentActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {

    Connect mDatabaseConnect;
    RecyclerView mRcvCartDetail;
    CartDetailAdapter mCartDetailAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    TextView mTxtTotalPrice;
    ImageButton mImgBtnCartDetail;
    Button mBtnBuyNow;
    int mTotalPrice = 0;
    boolean isCheckAll = false;
    List<String> mListProduct;
    List<ProductOrder> mListProductOrder;
    boolean isCheckPause = false;

    public CartFragment() {
        // Required empty public constructor
        mListProduct = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cart, container, false);

        mRcvCartDetail = v.findViewById(R.id.rcv_cart_detail);
        mImgBtnCartDetail = v.findViewById(R.id.img_all_cart_detail);
        mTxtTotalPrice = v.findViewById(R.id.txt_total_price_cart_detail);
        mBtnBuyNow = v.findViewById(R.id.btn_buy_now_cart_detail);

        mDatabaseConnect = new Connect();

        mTxtTotalPrice.setText("0");

        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRcvCartDetail.setLayoutManager(mLayoutManager);
        mRcvCartDetail.setHasFixedSize(true);

        GetCartDetail();

        mImgBtnCartDetail.setOnClickListener(this::OnClick);
        mBtnBuyNow.setOnClickListener(this::OnClick);

        return v;
    }

    private void OnClick(View v) {
        switch (v.getId()) {
            case R.id.img_all_cart_detail:
                if (isCheckAll) {
                    mImgBtnCartDetail.setImageResource(R.drawable.icon_uncheck_cart);
                    mCartDetailAdapter = new CartDetailAdapter(getListCart(), getContext(), false, 0, mDatabaseConnect, mListProduct);
                    mCartDetailAdapter.setOnCheckChange(this::CheckChange);
                    mRcvCartDetail.setAdapter(mCartDetailAdapter);
                    mCartDetailAdapter.notifyDataSetChanged();
                    isCheckAll = false;
                }else {
                    mImgBtnCartDetail.setImageResource(R.drawable.icon_check_cart);
                    mCartDetailAdapter = new CartDetailAdapter(getListCart(), getContext(), true, 0, mDatabaseConnect, mListProduct);
                    mCartDetailAdapter.setOnCheckChange(this::CheckChange);
                    mRcvCartDetail.setAdapter(mCartDetailAdapter);
                    mCartDetailAdapter.notifyDataSetChanged();
                    isCheckAll = true;
                }
                break;
            case R.id.btn_buy_now_cart_detail:
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

                Intent intentBuyNow = new Intent(getActivity(), PaymentActivity.class);
                intentBuyNow.putParcelableArrayListExtra("ListProduct", (ArrayList<? extends Parcelable>) this.mListProductOrder);
                startActivity(intentBuyNow);
                break;
        }
    }

    private void GetCartDetail() {
        mCartDetailAdapter = new CartDetailAdapter(getListCart(), getContext(), false, this.mTotalPrice, mDatabaseConnect, mListProduct);
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
            mBtnBuyNow.setBackground(getActivity().getDrawable(R.color.colorRedOrange));
        else
            mBtnBuyNow.setBackground(getActivity().getDrawable(R.color.colorGray));
    }

    //FORTMART PRICE
    private String FortmartPrice(Integer Price) {

        DecimalFormat mDecimalFormat = new DecimalFormat("###,###,###");
        String mPrice = mDecimalFormat.format(Price);

        return mPrice + " VND";
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isCheckPause) {
            mCartDetailAdapter = new CartDetailAdapter(getListCart(), getContext(), false, this.mTotalPrice, mDatabaseConnect, mListProduct);
            mCartDetailAdapter.setOnCheckChange(this::CheckChange);
            mRcvCartDetail.setAdapter(mCartDetailAdapter);
            mCartDetailAdapter.notifyDataSetChanged();
            isCheckPause = false;
        }
        mTxtTotalPrice.setText("0 VND");
    }

    @Override
    public void onPause() {
        super.onPause();
        isCheckPause = true;
    }
}

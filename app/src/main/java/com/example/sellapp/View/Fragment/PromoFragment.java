package com.example.sellapp.View.Fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.sellapp.Adapter.MenuPromoAdapter;
import com.example.sellapp.Adapter.ProductPromoAdapter;
import com.example.sellapp.Model.PromoModel.ListProductPromo;
import com.example.sellapp.Model.PromoModel.ListPromotion;
import com.example.sellapp.R;
import com.example.sellapp.Retrofit.RetrofitClient;
import com.example.sellapp.Retrofit.RetrofitService;

import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class PromoFragment extends Fragment {

    ViewFlipper mViewFlipper;
    RecyclerView mRcvMenuPromotion, mRcvProductPromotion;
    Animation mIn, mOut;

    CompositeDisposable compositeDisposable;
    Retrofit mRetrofitClient;
    RetrofitService mRetrofitService;

    MenuPromoAdapter mMenuPromoAdapter;
    ProductPromoAdapter mProductPromoAdapter;

    private List<ListPromotion> mListPromo;
    private List<ListProductPromo> mListProductPromos;
    private int percent;

    public PromoFragment() {
        compositeDisposable = new CompositeDisposable();
        mRetrofitClient = RetrofitClient.getInstance();
        mRetrofitService = mRetrofitClient.create(RetrofitService.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_promo, container, false);

        mViewFlipper = v.findViewById(R.id.view_flipper_promo);
        mRcvMenuPromotion = v.findViewById(R.id.rcv_menu_promotion);
        mRcvProductPromotion = v.findViewById(R.id.rcv_product_promotion);

        RecyclerView.LayoutManager mLayoutManagerMenu = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRcvMenuPromotion.setLayoutManager(mLayoutManagerMenu);
        mRcvMenuPromotion.setHasFixedSize(true);
        mMenuPromoAdapter = new MenuPromoAdapter(getActivity(), null);
        mRcvMenuPromotion.setAdapter(mMenuPromoAdapter);

        RecyclerView.LayoutManager mLayoutManagerProduct = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
        mRcvProductPromotion.setLayoutManager(mLayoutManagerProduct);
        mRcvProductPromotion.setHasFixedSize(true);

        showSlideProduct();
        GetProductPromo();

        return v;
    }

    private void GetProductPromo() {
        compositeDisposable.add(mRetrofitService.getAllPromo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(promotion -> {
                this.mListPromo = promotion.getmPromotion();
                getAllProductPromo(promotion.getmPromotion());
            }));
    }

    private void getAllProductPromo(List<ListPromotion> listPromotions) {
        mMenuPromoAdapter = new MenuPromoAdapter(getContext(), listPromotions);
        mMenuPromoAdapter.setOnChangePromo(this::ChangePromo);
        mRcvMenuPromotion.setAdapter(mMenuPromoAdapter);
        mMenuPromoAdapter.notifyDataSetChanged();

        this.percent = listPromotions.get(0).getmPrecent();
        this.mListProductPromos = listPromotions.get(0).getmListProductPromo();
        getAllProductInPromo(percent, mListProductPromos);
    }

    public void ChangePromo(int position) {
        ChangeColorPromoName(position);
        percent = this.mListPromo.get(position).getmPrecent();
        mListProductPromos = this.mListPromo.get(position).getmListProductPromo();
        getAllProductInPromo(percent, mListProductPromos);
    }

    private void getAllProductInPromo(int percent, List<ListProductPromo> listProductPromos) {
        mProductPromoAdapter = new ProductPromoAdapter(getActivity(), listProductPromos, percent);
        mRcvProductPromotion.setAdapter(mProductPromoAdapter);
        mProductPromoAdapter.notifyDataSetChanged();
    }

    private void ChangeColorPromoName(int position) {
        for (int i = 0; i < this.mListPromo.size(); i++)
            if (mRcvMenuPromotion.findViewHolderForAdapterPosition(i) != null) {
                TextView temp = mRcvMenuPromotion.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.txt_promotion_name);
                temp.setTextColor(Color.BLACK);
            }

        TextView t = mRcvMenuPromotion.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.txt_promotion_name);
        t.setTextColor(Color.RED);
    }

    public void showSlideProduct() {

        int a[] = {R.drawable.slide_men1, R.drawable.slide_men2, R.drawable.slide_men3};
        mIn = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_right);
        mOut = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_left);

        for (int i = 0; i < a.length; i++) {

            ImageView mImageSlide = new ImageView(getContext());
            mImageSlide.setScaleType(ImageView.ScaleType.CENTER_CROP);

            mImageSlide.setImageResource(a[i]);

            mViewFlipper.addView(mImageSlide);
            mViewFlipper.startFlipping();
            mViewFlipper.setInAnimation(mIn);
            mViewFlipper.setOutAnimation(mOut);
            mViewFlipper.setFlipInterval(3000);
            mViewFlipper.setAutoStart(true);
        }
    }
}

package com.example.sellapp.View.Fragment;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
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
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.sellapp.Adapter.CategoryAdapter;
import com.example.sellapp.Adapter.ProductHighlightAdapter;
import com.example.sellapp.Config;
import com.example.sellapp.Model.SlideModel.ListSlide;
import com.example.sellapp.R;
import com.example.sellapp.R.anim;
import com.example.sellapp.Retrofit.RetrofitClient;
import com.example.sellapp.Retrofit.RetrofitService;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    //RECYCLEVIEW CATEGORY
    private RecyclerView mCateRecycleView;
    private CategoryAdapter mCateAdapter;

    //RETROFIT
    private CompositeDisposable compositeDisposable;
    private Retrofit mRetrofitClient;
    private RetrofitService mRetrofitService;

    //SLIDE SHOW
    ViewFlipper mViewFlipperSlide;
    Animation mIn, mOut;

    //PRODUCT HIGHLIGHT
    private RecyclerView mProductHighLightRecy;
    private ProductHighlightAdapter mProductHLAdapter;

    NestedScrollView mNestScroll;

    public HomeFragment() {
        // Required empty public constructor
        compositeDisposable = new CompositeDisposable();

        mRetrofitClient = RetrofitClient.getInstance();
        mRetrofitService = mRetrofitClient.create(RetrofitService.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        //Find view
        mCateRecycleView = v.findViewById(R.id.mCateRecycle);
        mViewFlipperSlide = v.findViewById(R.id.view_flipper);
        mProductHighLightRecy = v.findViewById(R.id.product_recycle);

        mNestScroll = v.findViewById(R.id.nested_scroll);

        //Add animation for viewflipper
        mIn = AnimationUtils.loadAnimation(getContext(), anim.slide_in_right);
        mOut = AnimationUtils.loadAnimation(getContext(), anim.slide_out_left);

        //RecycleView category
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.HORIZONTAL, false);
        mCateRecycleView.setLayoutManager(mLayoutManager);
        mCateRecycleView.setHasFixedSize(true);

        //RecycleView Product
        RecyclerView.LayoutManager mProductLayout = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
        mProductHighLightRecy.setLayoutManager(mProductLayout);
        mProductHighLightRecy.setHasFixedSize(true);

        SlideShow();
        getDataCate();
        getDataProductHighLight();

        return v;
    }

    //GET DATA CATEGORY
    private void getDataCate() {

        compositeDisposable.add(mRetrofitService.getCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(category -> {

                    mCateAdapter = new CategoryAdapter(category.getmCategory(), getActivity());
                    mCateRecycleView.setAdapter(mCateAdapter);
                    mCateAdapter.notifyDataSetChanged();
                }));
    }

    //SLIDE SHOW
    private void SlideShow() {

        compositeDisposable.add(mRetrofitService.getSlide()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(slide -> CustomViewFlipper(slide.getmListSlide())));
    }

    //CUSTOM ViewFLIPPER
    private void CustomViewFlipper(List<ListSlide> mListSlide) {

        for (int i = 0; i < mListSlide.size(); i++) {

            ImageView mImageSlide = new ImageView(getContext());
            mImageSlide.setScaleType(ImageView.ScaleType.CENTER_CROP);

            Picasso.get()
                    .load(Config.URL + mListSlide.get(i).getmSlideImg())
                    .into(mImageSlide);

            mViewFlipperSlide.addView(mImageSlide);
            mViewFlipperSlide.startFlipping();
            mViewFlipperSlide.setInAnimation(mIn);
            mViewFlipperSlide.setOutAnimation(mOut);
            mViewFlipperSlide.setFlipInterval(3000);
            mViewFlipperSlide.setAutoStart(true);

        }
    }

    //GET DATA PRODUCT HIGHLIGHT
    private void getDataProductHighLight() {

        compositeDisposable.add(mRetrofitService.getProductHighlight()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(product -> {

                mProductHLAdapter = new ProductHighlightAdapter(product.getmProduct(), getActivity());
                mProductHighLightRecy.setAdapter(mProductHLAdapter);
                mProductHLAdapter.notifyDataSetChanged();
            }));
    }
}

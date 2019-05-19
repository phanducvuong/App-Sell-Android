package com.example.sellapp.View.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

import com.example.sellapp.Adapter.CategoryAdapter;
import com.example.sellapp.Adapter.ProductHighlightAdapter;
import com.example.sellapp.R;
import com.example.sellapp.Retrofit.RetrofitClient;
import com.example.sellapp.Retrofit.RetrofitService;
import com.example.sellapp.Util.SlideShow;

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
    private CategoryAdapter mCategoryAdapter;

    //RETROFIT
    private CompositeDisposable compositeDisposable;
    private Retrofit mRetrofitClient;
    private RetrofitService mRetrofitService;

    //SLIDE SHOW
    ViewFlipper mViewFlipperSlide;

    //PRODUCT HIGHLIGHT
    private RecyclerView mProductHighLightRecy;
    private ProductHighlightAdapter mProductHLAdapter;

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

        //RecycleView category
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.HORIZONTAL, false);
        mCateRecycleView.setLayoutManager(mLayoutManager);
        mCateRecycleView.setHasFixedSize(true);

        //RecycleView Product
        RecyclerView.LayoutManager mProductLayout = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
        mProductHighLightRecy.setLayoutManager(mProductLayout);
        mProductHighLightRecy.setHasFixedSize(true);

        SlideShow();
        getDataMenuCate();
        getDataProductHighLight();

        return v;
    }

    //GET DATA CATEGORY
    private void getDataMenuCate() {

        compositeDisposable.add(mRetrofitService.getCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(category -> {

                    mCategoryAdapter = new CategoryAdapter(category.getmCategory(), getActivity());
                    mCateRecycleView.setAdapter(mCategoryAdapter);
                    mCategoryAdapter.notifyDataSetChanged();
                }));
    }

    //SLIDE SHOW
    private void SlideShow() {

        compositeDisposable.add(mRetrofitService.getSlide()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(slide -> {
                    SlideShow mSlideShow = new SlideShow(slide.getmListSlide(), getActivity(), mViewFlipperSlide);
                    mSlideShow.showSlide();
                }));
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

package com.example.sellapp.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.example.sellapp.Adapter.CateMenuAdapter;
import com.example.sellapp.Adapter.ProductByCateIDAdapter;
import com.example.sellapp.R;
import com.example.sellapp.Retrofit.RetrofitClient;
import com.example.sellapp.Retrofit.RetrofitService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class FashionByMenuActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mTxtMenuName;
    private Button mBtnBack;

    private ViewFlipper mViewFlipper;
    private Animation mIn, mOut;

    private ProductByCateIDAdapter mProductByCateAdapter;
    private RecyclerView mRecycleProductByCate;

    private CateMenuAdapter mCateMenuAdapter;
    private RecyclerView mRecycleCateMenu;

    private Retrofit mRetrofitClient;
    private RetrofitService mRetrofitService;
    private CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_by_category);

        mTxtMenuName = findViewById(R.id.edt_menu_name);
        mBtnBack = findViewById(R.id.btn_back);
        mRecycleProductByCate = findViewById(R.id.recycle_product_by_cate);
        mRecycleCateMenu = findViewById(R.id.CateMenuRecycle);
        mViewFlipper = findViewById(R.id.view_flipper_fashion_men);

        ConfigRetrofit();

        mBtnBack.setOnClickListener(this);

        Intent mIntent = this.getIntent();
        mTxtMenuName.setText(mIntent.getStringExtra("CateName"));

        RecyclerView.LayoutManager mProductByCateLayout = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        mRecycleProductByCate.setLayoutManager(mProductByCateLayout);
        mRecycleProductByCate.setHasFixedSize(true);

        mProductByCateLayout = new GridLayoutManager(this, 2, LinearLayoutManager.HORIZONTAL, false);
        mRecycleCateMenu.setLayoutManager(mProductByCateLayout);
        mRecycleCateMenu.setHasFixedSize(true);

        showSlideProduct();
        LoadCateMenu(mIntent.getStringExtra("ID"));
        LoadProductByCateId(mIntent.getStringExtra("ID"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.dispose();
    }

    @Override
    public void onClick(View v) {
        this.finish();
    }

    //Config Retrofit
    private void ConfigRetrofit() {
        mCompositeDisposable = new CompositeDisposable();
        mRetrofitClient = RetrofitClient.getInstance();
        mRetrofitService = mRetrofitClient.create(RetrofitService.class);
    }

    //Load product by cate id
    private void LoadProductByCateId(String menu_id) {

        mCompositeDisposable.add(mRetrofitService.getProductByMenuId(menu_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(product -> {

                    mProductByCateAdapter = new ProductByCateIDAdapter(this, product.getmProduct());
                    mRecycleProductByCate.setAdapter(mProductByCateAdapter);
                    mProductByCateAdapter.notifyDataSetChanged();
                })
        );
    }

    //Load category menu for
    private void LoadCateMenu(String CateId) {

        mCompositeDisposable.add(mRetrofitService.getCateMenu(CateId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(catemenu -> {
                mCateMenuAdapter = new CateMenuAdapter(this, catemenu.getmListCateMenu());
                mRecycleCateMenu.setAdapter(mCateMenuAdapter);
                mCateMenuAdapter.notifyDataSetChanged();
            }));
    }

    //Slide show product
    public void showSlideProduct() {

        int a[] = {R.drawable.slide_men1, R.drawable.slide_men2, R.drawable.slide_men3};
        mIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        mOut = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);

        for (int i = 0; i < a.length; i++) {

            ImageView mImageSlide = new ImageView(this);
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

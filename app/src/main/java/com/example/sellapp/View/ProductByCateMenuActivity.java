package com.example.sellapp.View;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sellapp.Adapter.ProductByCateMenuIdAdapter;
import com.example.sellapp.R;
import com.example.sellapp.Retrofit.RetrofitClient;
import com.example.sellapp.Retrofit.RetrofitService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

@SuppressWarnings("deprecation")
public class ProductByCateMenuActivity extends AppCompatActivity {

    EditText mEditSearch;
    Button mBtnNewest;
    Button mBtnBestSell;
    Button mBtnSortPrice;
    Button mBtnReturn;

    boolean mChange = false;
    String mCateId;

    int icon_fliter_up;
    int icon_filter_down;
    int icon_filter;

    RecyclerView.LayoutManager mLayout;
    ProductByCateMenuIdAdapter mProductByCateMenuAdapter;
    RecyclerView mRecyclerProductByCateMenu;

    CompositeDisposable mCompositeDisposable;
    RetrofitService mRetrofitService;
    Retrofit mRetrofitClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_by_cate_menu);

        mEditSearch = findViewById(R.id.edt_cate_menu_name);
        mBtnNewest = findViewById(R.id.btn_newest);
        mBtnBestSell = findViewById(R.id.btn_best_sell);
        mBtnSortPrice = findViewById(R.id.btn_price);
        mBtnReturn = findViewById(R.id.btn_back);
        mRecyclerProductByCateMenu = findViewById(R.id.recycle_product_by_cate_menu);

        CreateRetrofit();

        icon_fliter_up = R.drawable.icon_arrow_filter_up;
        icon_filter_down = R.drawable.icon_arrow_filter_down;
        icon_filter = R.drawable.icon_arrow_filter;

        //set layout for recycler product by cate menu
        mLayout = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        mRecyclerProductByCateMenu.setLayoutManager(mLayout);
        mRecyclerProductByCateMenu.setHasFixedSize(true);

        mBtnNewest.setOnClickListener(this::onClick);
        mBtnBestSell.setOnClickListener(this::onClick);
        mBtnSortPrice.setOnClickListener(this::onClick);
        mBtnReturn.setOnClickListener(this::onClick);

        Intent mCateMenuIntent = this.getIntent();
        mCateId = mCateMenuIntent.getStringExtra("ID");
        mEditSearch.setText(mCateMenuIntent.getStringExtra("CateMenuName"));
        LoadProductByMenuCate(mCateId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.dispose();
    }

    private void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_newest:
                setBackground(mBtnNewest);
                LoadProductNewest(mCateId);
                unSetBackground(mBtnBestSell, mBtnSortPrice);
                unSetResource(icon_filter);
                break;

            case R.id.btn_best_sell:
                setBackground(mBtnBestSell);
                unSetBackground(mBtnNewest, mBtnSortPrice);
                unSetResource(icon_filter);
                break;

            case R.id.btn_price:
                setBackground(mBtnSortPrice);

                setBackgroundResource(icon_fliter_up, icon_filter_down);

                unSetBackground(mBtnNewest, mBtnBestSell);
                break;

            case R.id.btn_back:
                this.finish();
        }
    }

    //Create compositeDipoable
    public void CreateRetrofit() {
        mCompositeDisposable = new CompositeDisposable();
        mRetrofitClient = RetrofitClient.getInstance();
        mRetrofitService = mRetrofitClient.create(RetrofitService.class);
    }

    //Load product
    public void LoadProductByMenuCate(String cate_menu_id) {
        mCompositeDisposable.add(mRetrofitService.getProductByCateMenuId(cate_menu_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(product -> {
                mProductByCateMenuAdapter = new ProductByCateMenuIdAdapter(this, product.getmProduct());
                mRecyclerProductByCateMenu.setAdapter(mProductByCateMenuAdapter);
                mProductByCateMenuAdapter.notifyDataSetChanged();
            }));
    }

    //Load product by asc
    public void LoadProductByAsc(String cate_id) {
        mCompositeDisposable.add(mRetrofitService.getProductAsc(cate_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(product -> {
                mProductByCateMenuAdapter = new ProductByCateMenuIdAdapter(this, product.getmProduct());
                mRecyclerProductByCateMenu.setAdapter(mProductByCateMenuAdapter);
                mProductByCateMenuAdapter.notifyDataSetChanged();
            }));
    }

    //Load product by des
    public void LoadProductByDes(String cate_id) {
        mCompositeDisposable.add(mRetrofitService.getProductDes(cate_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(product -> {
                    mProductByCateMenuAdapter = new ProductByCateMenuIdAdapter(this, product.getmProduct());
                    mRecyclerProductByCateMenu.setAdapter(mProductByCateMenuAdapter);
                    mProductByCateMenuAdapter.notifyDataSetChanged();
                }));
    }

    //Load product newest
    public void LoadProductNewest(String cate_id) {
        mCompositeDisposable.add(mRetrofitService.getProductNewest(cate_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(product -> {
                mProductByCateMenuAdapter = new ProductByCateMenuIdAdapter(this, product.getmProduct());
                mRecyclerProductByCateMenu.setAdapter(mProductByCateMenuAdapter);
                mProductByCateMenuAdapter.notifyDataSetChanged();
            }));
    }

    //SET BACKGROUND
    public void setBackground(Button btn) {
        btn.setTextColor(getResources().getColor(R.color.colorOrange));
        btn.setBackground(getDrawable(R.drawable.button_shadow));
    }

    public void unSetBackground(Button btn1, Button btn2) {
        btn1.setBackground(getResources().getDrawable(R.color.white));
        btn1.setTextColor(getResources().getColor(R.color.colorBlack));
        btn2.setBackground(getResources().getDrawable(R.color.white));
        btn2.setTextColor(getResources().getColor(R.color.colorBlack));
    }

    //set resource for btn price
    public  void setBackgroundResource(int icon_filter_up, int icon_filter_down) {
        if (mChange) {
            mBtnSortPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, icon_filter_up, 0);
            LoadProductByAsc(mCateId);
            mChange = false;
        } else {
            mBtnSortPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, icon_filter_down, 0);
            LoadProductByDes(mCateId);
            mChange = true;
        }
    }

    //unset resuorce for btn price
    public void unSetResource(int icon_filter) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            mBtnSortPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, icon_filter, 0);
        }
    }
}
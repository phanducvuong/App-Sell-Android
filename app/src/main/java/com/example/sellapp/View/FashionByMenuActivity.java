package com.example.sellapp.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sellapp.Adapter.ProductByCateIDAdapter;
import com.example.sellapp.Model.ProductModel.Product;
import com.example.sellapp.R;
import com.example.sellapp.Retrofit.RetrofitClient;
import com.example.sellapp.Retrofit.RetrofitService;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class FashionByMenuActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mTxtMenuName;
    private Button mBtnBack;

    private ProductByCateIDAdapter mProductByCateAdapter;
    private RecyclerView mRecycleProductByCate;

    private Retrofit mRetrofitClient;
    private RetrofitService mRetrofitService;
    private CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fashion_by_menu);

        mTxtMenuName = findViewById(R.id.edt_menu_name);
        mBtnBack = findViewById(R.id.btn_back);
        mRecycleProductByCate = findViewById(R.id.recycle_product_by_cate);

        ConfigRetrofit();
        mBtnBack.setOnClickListener(this);

        Intent mIntent = this.getIntent();
        mTxtMenuName.setText(mIntent.getStringExtra("CateName"));

        RecyclerView.LayoutManager mProductByCateLayout = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        mRecycleProductByCate.setLayoutManager(mProductByCateLayout);
        mRecycleProductByCate.setHasFixedSize(true);

        LoadProductByCateId(mIntent.getStringExtra("ID"));
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
    private void LoadProductByCateId(String cate_id) {

        mCompositeDisposable.add(mRetrofitService.getProductByCateId(cate_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(product -> {

                    mProductByCateAdapter = new ProductByCateIDAdapter(this, product.getmProduct());
                    mRecycleProductByCate.setAdapter(mProductByCateAdapter);
                    mProductByCateAdapter.notifyDataSetChanged();
                })
        );
    }
}

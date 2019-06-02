package com.example.sellapp.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sellapp.Config;
import com.example.sellapp.Model.ProductModel.ListProduct;
import com.example.sellapp.Model.ProductModel.ProductDetail;
import com.example.sellapp.R;
import com.example.sellapp.Retrofit.RetrofitClient;
import com.example.sellapp.Retrofit.RetrofitService;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ProductDetailActivity extends AppCompatActivity {

    Retrofit mRetrofitClient;
    RetrofitService mRetrofitService;
    CompositeDisposable mCompositeDisposable;

    TextView mTxtProductName;
    TextView mTxtProductPrice;
    TextView mTxtComment;
    RatingBar mRatingBar;
    TextView mTxtRating;
    TextView mTxtProductDescription;
    Button mBtnCommentDetail;
    Button mBtnBuyNow;
    Button mBtnAddToCart;
    ImageView mImgProductImage;
    ImageButton mImageButtonExpend;

    boolean flagImageExpend = true;
    String textDescription;

    String mProductId;

    RecyclerView mRcvComment;
    RecyclerView mRcvProductOther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        bindView();
        createRetrofit();

        Intent CateMenuItent = this.getIntent();
        mProductId = CateMenuItent.getStringExtra("ProductID");

        getProductById(mProductId);

        mTxtComment.setOnClickListener(this::OnClick);
    }

    public void OnClick(View v) {
        Intent mIntent = new Intent(ProductDetailActivity.this, CommentActivity.class);
        startActivity(mIntent);
    }

    private void getProductById(String id) {
        mCompositeDisposable.add(mRetrofitService.getProductById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(product -> bindViewByProduct(product)));
    }

    private void bindViewByProduct(ProductDetail product) {
        mTxtProductName.setText(product.getProductById().getProductName());
        mTxtProductPrice.setText(FormatPrice(product.getProductById().getProductPrice()) + " VND");
        mTxtRating.setText(String.valueOf(mRatingBar.getRating()));

        Picasso.get()
                .load(Config.URL + product.getProductById().getProductPicBig())
                .into(mImgProductImage);

        textDescription = product.getProductById().getProductDetail();

        if (textDescription.length() <= 100) {
            mTxtProductDescription.setText(textDescription);
        }else {
            mTxtProductDescription.setText(textDescription.substring(0, 100));
            mImageButtonExpend.setOnClickListener(this::ExpandeProductDescription);
        }
    }

    public void ExpandeProductDescription(View v) {
        if (flagImageExpend) {
            mImageButtonExpend.setImageResource(R.drawable.keyboard_up);
            mTxtProductDescription.setText(textDescription);
            flagImageExpend = false;
        }else {
            mImageButtonExpend.setImageResource(R.drawable.keyboard_down);
            mTxtProductDescription.setText(textDescription.substring(0, 100));
            flagImageExpend = true;
        }
    }

    private void createRetrofit() {
        mCompositeDisposable = new CompositeDisposable();
        mRetrofitClient = RetrofitClient.getInstance();
        mRetrofitService = mRetrofitClient.create(RetrofitService.class);
    }

    private void bindView() {
        mTxtProductName = findViewById(R.id.txt_product_name);
        mTxtProductPrice = findViewById(R.id.txt_price);
        mTxtComment = findViewById(R.id.txt_comment);
        mRatingBar = findViewById(R.id.rating_bar);
        mTxtRating = findViewById(R.id.txt_rating);
        mTxtProductDescription = findViewById(R.id.txt_product_description);
        mBtnCommentDetail = findViewById(R.id.btn_comment_detail);
        mBtnBuyNow = findViewById(R.id.btn_buy_now);
        mBtnAddToCart = findViewById(R.id.btn_add_to_cart);
        mRcvComment = findViewById(R.id.rcv_comment);
        mRcvProductOther = findViewById(R.id.rcv_product_other);
        mImgProductImage = findViewById(R.id.img_product_image);
        mImageButtonExpend = findViewById(R.id.img_btn_expande);
    }

    public String FormatPrice(Integer Price) {

        DecimalFormat mDecimalFormat = new DecimalFormat("###,###,###");
        String mPrice = mDecimalFormat.format(Price);

        return mPrice;
    }

}

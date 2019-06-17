package com.example.sellapp.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sellapp.Adapter.CommentAdapter;
import com.example.sellapp.Config;
import com.example.sellapp.Model.CartModel.Connect;
import com.example.sellapp.Model.ProductModel.ListProduct;
import com.example.sellapp.Model.ProductModel.ProductDetail;
import com.example.sellapp.R;
import com.example.sellapp.Retrofit.RetrofitClient;
import com.example.sellapp.Retrofit.RetrofitService;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
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
    TextView mTxtCardHeader;
    RatingBar mRatingBar;
    TextView mTxtRating;
    TextView mTxtProductDescription;
    Button mBtnCommentDetail;
    Button mBtnBuyNow;
    Button mBtnAddToCart;
    ImageView mImgProductImage;
    ImageView mImgCartHeader;
    ImageButton mImageButtonExpend;
    ImageButton mImageButtonBack;

    boolean flagImageExpend = true;
    boolean countCart = false;
    String textDescription;

    String mProductId;
    RecyclerView.LayoutManager mLayoutManager;

    RecyclerView mRcvComment;
    CommentAdapter mCommentAdapter;
    RecyclerView mRcvProductOther;

    Connect mConnectToDatabase;
    ListProduct mProductCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        bindView();
        createRetrofit();
        mConnectToDatabase = new Connect();

        Intent CateMenuItent = this.getIntent();
        mProductId = CateMenuItent.getStringExtra("ProductID");

        mTxtComment.setOnClickListener(this::OnClick);
        mBtnCommentDetail.setOnClickListener(this::OnClick);
        mImageButtonBack.setOnClickListener(this::OnClick);
        mBtnAddToCart.setOnClickListener(this::OnClick);
        mImgCartHeader.setOnClickListener(this::OnClick);

        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRcvComment.setLayoutManager(mLayoutManager);
        mRcvComment.setHasFixedSize(true);

        mTxtCardHeader.setText(String.valueOf(getItemCart()));

        getProductById(mProductId);
        GetAllProduct(mProductId);
    }

    private void GetAllProduct(String id) {
        mCompositeDisposable.add(mRetrofitService.getAllComment(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(comment -> {
                mCommentAdapter = new CommentAdapter(this, comment.getmComment(), false);
                mRcvComment.setAdapter(mCommentAdapter);
                mCommentAdapter.notifyDataSetChanged();
            }));
    }

    public void OnClick(View v) {
        switch (v.getId()){
            case R.id.txt_comment:
                Intent mIntent = new Intent(ProductDetailActivity.this, CommentActivity.class);
                mIntent.putExtra("ProductId", mProductId);
                startActivity(mIntent);
                break;
            case R.id.btn_comment_detail:
                Intent mIntentComment = new Intent(ProductDetailActivity.this, CommentDetailActivity.class);
                mIntentComment.putExtra("ProductId", mProductId);
                startActivity(mIntentComment);
                break;
            case R.id.img_btn_back:
                this.finish();
                break;

            case R.id.btn_add_to_cart:
                ImageView imageView = findViewById(R.id.img_product_image);
                Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();

                //convert bitmap to byte
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] image_cart = byteArrayOutputStream.toByteArray();
                mProductCart.setImage(image_cart);
                AddToCart(mProductCart);

                mTxtCardHeader.setText(String.valueOf(getItemCart()));
                break;

            case R.id.img_cart_header:
                Intent intent = new Intent(ProductDetailActivity.this, CartDetailActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        countCart = true;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (countCart) mTxtCardHeader.setText(String.valueOf(getItemCart()));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void AddToCart(ListProduct product) {
        mConnectToDatabase.Connection(this);
        boolean check = mConnectToDatabase.AddToCart(product);

        if (check) Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        else Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();
    }

    private int getItemCart() {
        mConnectToDatabase.Connection(this);
        return mConnectToDatabase.GetProductInCart().size();
    }

    private void getProductById(String id) {
        mCompositeDisposable.add(mRetrofitService.getProductById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(product -> bindViewByProduct(product)));
    }

    private void bindViewByProduct(ProductDetail product) {
        mProductCart = product.getProductById();

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
        mImageButtonBack = findViewById(R.id.img_btn_back);
        mTxtCardHeader = findViewById(R.id.txt_cart_header);
        mImgCartHeader = findViewById(R.id.img_cart_header);
    }

    public String FormatPrice(Integer Price) {

        DecimalFormat mDecimalFormat = new DecimalFormat("###,###,###");
        String mPrice = mDecimalFormat.format(Price);

        return mPrice;
    }

}

package com.example.sellapp.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.sellapp.R;
import com.example.sellapp.Retrofit.RetrofitClient;
import com.example.sellapp.Retrofit.RetrofitService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

@SuppressWarnings("deprecation")
public class CommentActivity extends AppCompatActivity {

    TextInputLayout mInputTitle, mInputContent;
    EditText mEdtTitle, mEdtContent;
    RatingBar mRtbComment;
    Button mBtnSubmit;
    String mProductId;

    CompositeDisposable mCompositeDisposable;
    Retrofit mRetrofitClient;
    RetrofitService mRetrofitService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        mInputTitle = findViewById(R.id.input_title);
        mInputContent = findViewById(R.id.input_content);
        mEdtTitle = findViewById(R.id.edt_title);
        mEdtContent = findViewById(R.id.edt_content);
        mRtbComment = findViewById(R.id.rtb_comment);
        mBtnSubmit = findViewById(R.id.btn_submit_comment);

        CreateRetrofit();

        Intent mIntent = this.getIntent();
        mProductId = mIntent.getStringExtra("ProductId");

        mBtnSubmit.setOnClickListener(this::OnClick);
    }

    public void OnClick(View v) {
        PostComment();
    }

    private void PostComment() {
        TelephonyManager mTelephone = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

        @SuppressLint("MissingPermission")
        String CommentId = mTelephone.getDeviceId();
        String DeviceName = Build.MODEL;
        String Title = mEdtTitle.getText().toString().trim();
        String Content = mEdtContent.getText().toString().trim();
        String NumberStar = String.valueOf(mRtbComment.getRating());

        DateFormat mDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String DateComment = String.valueOf(mDateFormat.format(date));

        if (Title.isEmpty()) {
            mEdtTitle.setError("Tiêu đề không được bỏ trống");

        }else if (Content.isEmpty()) {
            mEdtContent.setError("Nội dung không được bỏ trống");

        }else {
            mCompositeDisposable.add(mRetrofitService.postComment(CommentId, mProductId, DeviceName, Title, Content, NumberStar, DateComment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(message -> {
                    if (message.getMessage().contains("Dont")){
                        Toast.makeText(this, "Bạn không thể đánh giá sản phẩm này", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else {
                        Toast.makeText(this, message.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }));
        }
    }

    private void CreateRetrofit() {
        mCompositeDisposable = new CompositeDisposable();
        mRetrofitClient = RetrofitClient.getInstance();
        mRetrofitService = mRetrofitClient.create(RetrofitService.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.dispose();
    }
}

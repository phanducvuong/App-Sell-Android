package com.example.sellapp.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.sellapp.Adapter.CommentAdapter;
import com.example.sellapp.R;
import com.example.sellapp.Retrofit.RetrofitClient;
import com.example.sellapp.Retrofit.RetrofitService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class CommentDetailActivity extends AppCompatActivity {

    Button mBtnBack;
    RecyclerView mRcvCommentDetail;
    CommentAdapter mCommentAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    CompositeDisposable mCompositeDisposable;
    Retrofit mRetrofitClient;
    RetrofitService mRetrofitService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_detail_layout);

        mBtnBack = findViewById(R.id.btn_back_comment_detail);
        mRcvCommentDetail = findViewById(R.id.rcv_comment_detail);

        mBtnBack.setOnClickListener(this::OnClick);

        mCompositeDisposable = new CompositeDisposable();
        mRetrofitClient = RetrofitClient.getInstance();
        mRetrofitService = mRetrofitClient.create(RetrofitService.class);

        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRcvCommentDetail.setLayoutManager(mLayoutManager);
        mRcvCommentDetail.setHasFixedSize(true);

        Intent mIntent = this.getIntent();
        String ID = mIntent.getStringExtra("ProductId");
        GetAllCommentDetail(ID);
    }

    private void GetAllCommentDetail(String ID) {
        mCompositeDisposable.add(mRetrofitService.getAllComment(ID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(comment -> {
                mCommentAdapter = new CommentAdapter(this, comment.getmComment(), true);
                mRcvCommentDetail.setAdapter(mCommentAdapter);
                mCommentAdapter.notifyDataSetChanged();
            }));
    }

    private void OnClick(View v) {
        this.finish();
    }
}

package com.example.sellapp.View;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.sellapp.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommentActivity extends AppCompatActivity {

    TextInputLayout mInputTitle, mInputContent;
    EditText mEdtTitle, mEdtContent;
    RatingBar mRtbComment;
    Button mBtnSubmit;

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

        mBtnSubmit.setOnClickListener(this::OnClick);
    }

    public void OnClick(View v) {
        Toast.makeText(this, mRtbComment.getRating() + " ", Toast.LENGTH_SHORT).show();
    }

    private void PostComment() {
        TelephonyManager mTelephone = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

        @SuppressLint("MissingPermission")
        String CommentId = mTelephone.getDeviceId();
        String DeviceName = Build.MODEL;
        String Title = mEdtTitle.getText().toString();
        String Content = mEdtContent.getText().toString();

        DateFormat mDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String DateComment = String.valueOf(mDateFormat.format(date));
    }
}

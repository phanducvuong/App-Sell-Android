package com.example.sellapp.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sellapp.Model.CartModel.Connect;
import com.example.sellapp.Model.OrderModel.ListOrder;
import com.example.sellapp.Model.OrderModel.Order;
import com.example.sellapp.Model.OrderModel.ProductOrder;
import com.example.sellapp.R;
import com.example.sellapp.Retrofit.RetrofitClient;
import com.example.sellapp.Retrofit.RetrofitService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class PaymentActivity extends AppCompatActivity {

     EditText mEdtReceiver, mEdtPhoneNumber, mEdtAddress, mEdtNote;
     ImageView mImgAfterPay, mImgBeforePay;
     Button mBtnPayment;
     CheckBox mCbCommitment;
     private List<ProductOrder> mListProductOrder;

     CompositeDisposable compositeDisposable;
     Retrofit mRetrofitClient;
     RetrofitService mRetrofitService;

     Connect db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        _BindView();
        _CreateComposite();
        db = new Connect();

        Intent intent = getIntent();
        this.mListProductOrder = intent.getParcelableArrayListExtra("ListProduct");
        mBtnPayment.setOnClickListener(this::OnClick);
    }

    private void OnClick(View v) {
        if (mCbCommitment.isChecked())
            _PostOrder();
    }

    private void _BindView() {
        mEdtReceiver = findViewById(R.id.edt_receiver);
        mEdtPhoneNumber = findViewById(R.id.edt_phone_number);
        mEdtAddress = findViewById(R.id.edt_address);
        mEdtNote = findViewById(R.id.edt_note);
        mImgAfterPay = findViewById(R.id.img_after_pay);
        mImgBeforePay = findViewById(R.id.img_before_pay);
        mBtnPayment = findViewById(R.id.btn_payment);
        mCbCommitment = findViewById(R.id.cb_commitment);
    }

    private void _PostOrder() {
        String reciever = mEdtReceiver.getText().toString().trim();
        String phone_number = mEdtPhoneNumber.getText().toString().trim();
        String addresss = mEdtAddress.getText().toString().trim();
        String note = mEdtNote.getText().toString().trim();

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date_order = dateFormat.format(date);

        if (reciever.isEmpty())
            mEdtReceiver.setError("Người nhận không được để trống");
        else if (phone_number.isEmpty())
            mEdtPhoneNumber.setError("Số điện thoại không được để trống");
        else if (addresss.isEmpty())
            mEdtAddress.setError("Địa chỉ không được để trống");
        else {
            ListOrder listOrder = new ListOrder();
            listOrder.setmReceiver(reciever);
            listOrder.setmPhoneNumber(phone_number);
            listOrder.setmAddressReci(addresss);
            listOrder.setmOrderDate(date_order);
            listOrder.setmNote(note);
            listOrder.setmStatus(false);
            listOrder.setmListProductOrder(this.mListProductOrder);

            Gson jsonObject = new Gson();
            String json = jsonObject.toJson(listOrder);
            JsonObject jsonOj = new JsonObject();
            jsonOj.addProperty("list_product", json);

            compositeDisposable.add(mRetrofitService.postOrder(jsonOj)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(order -> {
                    Order order1 = new Order();
                    order1.setmOrder_id(order.getmOrder_id());
                    AddIdToOrder(order1);

                    if (DeleteAllItemInCart())
                        finish();
                    else
                        Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();
                }));
        }
    }

    private boolean DeleteAllItemInCart() {
        int count = 0;
        for (int i = 0; i < this.mListProductOrder.size(); i++)
            if (db.DeleteItemCart(this.mListProductOrder.get(i).getmProductId()))
                count++;

        return count == this.mListProductOrder.size();
    }

    private void AddIdToOrder(Order order) {
        db.Connection(this);
        boolean isCheck = db.AddToOrderId(order);
        if (isCheck) Toast.makeText(this, "Mua hàng thành công!", Toast.LENGTH_SHORT).show();
        else Toast.makeText(this, "Mua hàng thất bại!", Toast.LENGTH_SHORT).show();
    }

    private void _CreateComposite() {
        compositeDisposable = new CompositeDisposable();
        mRetrofitClient = RetrofitClient.getInstance();
        mRetrofitService = mRetrofitClient.create(RetrofitService.class);
    }
 }

package com.example.sellapp.View.ScrHello;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.sellapp.R;
import com.example.sellapp.View.SrcHome.HomeActivity;

public class HelloScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hello_screen_layout);

        Thread thread = new Thread(() -> { //new Runable(): use lambda expression

            try {
                Thread.sleep(3000);
            }catch (Exception e){

            }finally {
                Intent intent = new Intent(HelloScreenActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        thread.start();
    }
}

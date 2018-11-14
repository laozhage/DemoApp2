package com.demo.bs.demoapp2.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.demo.bs.demoapp2.R;
import com.demo.bs.demoapp2.ui.base.BaseActivity;

public class WelcomeActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Handler handler =new Handler();
        Runnable runnable =new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        };
        handler.postDelayed(runnable,3000);
    }


}

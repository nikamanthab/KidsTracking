package com.example.nitinnikamanth.kidstracking;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

public class SplashActivity extends Activity {

    SharedPreferences s;
    SharedPreferences.Editor ed;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        s = getSharedPreferences("kidstracker",0);
        ed = s.edit();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this , WelcomeActivity.class);
                startActivity(i);
                SplashActivity.this.finish();
            }
        } , 2000);

    }
}

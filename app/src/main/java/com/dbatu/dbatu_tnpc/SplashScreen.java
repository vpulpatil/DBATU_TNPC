package com.dbatu.dbatu_tnpc;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

/**
 * Created by Keyur on 20-10-2016.
 */
public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        int SPLASH_TIME_OUT = 2500;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent welcomeIntent = new Intent(SplashScreen.this, WelcomeActivity.class);
                startActivity(welcomeIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
package com.dbatu.dbatu_tnpc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by Keyur on 20-10-2016.
 */
public class SplashScreen extends Activity {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Checking Internet Connection...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        WelcomeScreen();
    }

    private void WelcomeScreen() {
        int SPLASH_TIME_OUT = 2500;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ConnectivityManager connec =(ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
                if ( connec.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.CONNECTED ||
                        connec.getNetworkInfo(0).getState() ==
                                android.net.NetworkInfo.State.CONNECTING ||
                        connec.getNetworkInfo(1).getState() ==
                                android.net.NetworkInfo.State.CONNECTING ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
                    Intent welcomeIntent = new Intent(SplashScreen.this, WelcomeActivity.class);
                    startActivity(welcomeIntent);
                    finish();
                    progressDialog.dismiss();
                }else if (
                        connec.getNetworkInfo(0).getState() ==
                                android.net.NetworkInfo.State.DISCONNECTED ||
                                connec.getNetworkInfo(1).getState() ==
                                        android.net.NetworkInfo.State.DISCONNECTED  ) {
                    WelcomeScreen();
                }
            }
        }, SPLASH_TIME_OUT);
    }
}
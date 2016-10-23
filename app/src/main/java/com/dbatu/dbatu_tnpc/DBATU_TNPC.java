package com.dbatu.dbatu_tnpc;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Keyur on 20-10-2016.
 */

public class DBATU_TNPC extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}

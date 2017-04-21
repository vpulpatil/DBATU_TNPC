package com.dbatu.dbatu_tnpc;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Keyur on 14-04-2017.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        String refreshToken = FirebaseInstanceId.getInstance().getToken();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications");
        reference.child("token").setValue(refreshToken);
    }
}
package com.dbatu.dbatu_tnpc;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;


public class SendNotification extends AppCompatActivity {

    private Button send;
    private EditText title, desc;
    private LinearLayout linearLayout;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notification);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Send Notice");

        FirebaseMessaging.getInstance().subscribeToTopic("android");

        send = (Button)findViewById(R.id.sendNotification);
        title = (EditText)findViewById(R.id.notificationTitle);
        desc = (EditText)findViewById(R.id.notificationDescription);
        linearLayout = (LinearLayout) findViewById(R.id.linear);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
            }
        });
    }

    private void sendNotification() {
        String notificationTitle = title.getText().toString().trim();
        String notificationDescription = desc.getText().toString().trim();

        if (notificationTitle.equals("") && notificationDescription.equals("")){
            Snackbar snackbar = Snackbar.make(linearLayout, "Please enter title or message", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }else {
            DatabaseReference myRef = firebaseDatabase.getReference("Notifications").child(notificationTitle);
            Notifications notifications = new Notifications(title.getText().toString(),desc.getText().toString());
            myRef.setValue(notifications);
            title.setText("");
            desc.setText("");
            Toast.makeText(this, "News created successfully and is Live", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void onBackPressed(){
        finish();
    }
}
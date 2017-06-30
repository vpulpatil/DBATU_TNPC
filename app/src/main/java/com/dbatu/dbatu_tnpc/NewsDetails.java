package com.dbatu.dbatu_tnpc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NewsDetails extends AppCompatActivity {

    private TextView n_title, n_message;

    private DatabaseReference ref;
    private FirebaseDatabase mdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Notice");

        Intent intent = getIntent();
        String title = intent.getExtras().getString("title");

        mdata = FirebaseDatabase.getInstance();
        assert title != null;
        ref = mdata.getReference("Notifications").child(title).getRef();

        n_title = (TextView)findViewById(R.id.notifytitle);
        n_message = (TextView)findViewById(R.id.notifymessage);

        n_title.setText(title);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message = (String) dataSnapshot.child("message").getValue();
                n_message.setText(message);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
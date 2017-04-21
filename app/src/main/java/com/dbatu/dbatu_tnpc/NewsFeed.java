package com.dbatu.dbatu_tnpc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NewsFeed extends AppCompatActivity {

    private TextView empty;

    private DatabaseReference ref;
    private FirebaseDatabase mdata;

    private ArrayAdapter arrayAdapter;
    private ListView notificationList;
    private ArrayList<String> notifications = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("News");

        mdata = FirebaseDatabase.getInstance();
        ref = mdata.getReference("Notifications").getRef();

        empty = (TextView)findViewById(R.id.empty);

        notificationList = (ListView)findViewById(R.id.notificationList);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot notification : dataSnapshot.getChildren()) {
                    notifications.add(notification.getKey());

                }
                arrayAdapter = new ArrayAdapter(NewsFeed.this, android.R.layout.simple_list_item_1,notifications);
                notificationList.setAdapter(arrayAdapter);
                notificationList.setEmptyView(empty);
                notificationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String title =(String) parent.getItemAtPosition(position);
                        Intent intent = new Intent(NewsFeed.this, NewsDetails.class);
                        intent.putExtra("title", title);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}

package com.dbatu.dbatu_tnpc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListOfCompany extends AppCompatActivity {

    private ProgressDialog progressDialog;

    private TextView empty;
    private EditText search;

    private DatabaseReference ref;
    private FirebaseDatabase mdata;

    private ArrayAdapter arrayAdapter;
    private ListView companyList;
    private ArrayList<String> companies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_company);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("List of Companies");

        empty = (TextView)findViewById(R.id.empty_c);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("laoding...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        search = (EditText)findViewById(R.id.search);

        mdata = FirebaseDatabase.getInstance();
        ref = mdata.getReference("Company").getRef();

        companyList = (ListView)findViewById(R.id.companyList);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot company : dataSnapshot.getChildren()) {
                    companies.add(company.getKey());
                }
                arrayAdapter = new ArrayAdapter(ListOfCompany.this, android.R.layout.simple_list_item_1, companies);
                companyList.setAdapter(arrayAdapter);
                companyList.setEmptyView(empty);
                progressDialog.dismiss();
                companyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String companyName =(String) parent.getItemAtPosition(position);
                        Intent intent = new Intent(ListOfCompany.this, CompanyDetails.class);
                        intent.putExtra("companyName", companyName);
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
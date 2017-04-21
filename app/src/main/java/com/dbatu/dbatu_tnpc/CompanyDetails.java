package com.dbatu.dbatu_tnpc;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CompanyDetails extends AppCompatActivity {

    private EditText companyName, companyHR, companyContact, companyEmail, companyAddress;

    private Button update, edit;

    private LinearLayout linearLayout;

    private DatabaseReference ref;
    private FirebaseDatabase mdata;

    private Firebase dbatu_tnpc_firebase_reference;
    private String COMPANY_FIREBASE_URL = "https://dbatu-tnpc-79d12.firebaseio.com/Company";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Company Information");

        initialize();

        Intent intent = getIntent();
        String company_Name = intent.getExtras().getString("companyName");
        companyName.setText(company_Name);

        mdata = FirebaseDatabase.getInstance();
        assert company_Name != null;
        ref = mdata.getReference("Company").child(company_Name).getRef();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fetchAndSetCompanyDetailsToEditTexts(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInfo();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editInfo();
            }
        });
    }

    private void editInfo() {
        companyName.setEnabled(true);
        companyName.setClickable(true);
        companyHR.setEnabled(true);
        companyHR.setClickable(true);
        companyContact.setEnabled(true);
        companyContact.setClickable(true);
        companyEmail.setEnabled(true);
        companyEmail.setClickable(true);
        companyAddress.setEnabled(true);
        companyAddress.setClickable(true);
        update.setVisibility(View.VISIBLE);
        edit.setVisibility(View.GONE);
    }

    private void updateInfo() {
        String name = companyName.getText().toString().trim();
        String hr = companyHR.getText().toString().trim();
        String contact = companyContact.getText().toString().trim();
        String email = companyEmail.getText().toString().trim();
        String address = companyAddress.getText().toString().trim();

        if (name.equals(null) || hr.equals(null) || contact.equals(null) || email.equals(null) || address.equals(null)){
            Snackbar snackbar = Snackbar.make(linearLayout, "All fields are compulsary", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }else {
            dbatu_tnpc_firebase_reference = new Firebase(COMPANY_FIREBASE_URL + "/" + name);

            Firebase companyNameChildRef = dbatu_tnpc_firebase_reference.child("Company_Name");
            companyNameChildRef.setValue(name);
            Firebase companyHrChildRef = dbatu_tnpc_firebase_reference.child("HR");
            companyHrChildRef.setValue(hr);
            Firebase companyContactChildRef = dbatu_tnpc_firebase_reference.child("Contact_number");
            companyContactChildRef.setValue(contact);
            Firebase companyEmailChildRef = dbatu_tnpc_firebase_reference.child("Email");
            companyEmailChildRef.setValue(email);
            Firebase companyAddressChildRef = dbatu_tnpc_firebase_reference.child("Address");
            companyAddressChildRef.setValue(address);

            Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
            update.setVisibility(View.GONE);
            edit.setVisibility(View.VISIBLE);
            companyName.setEnabled(false);
            companyName.setClickable(false);
            companyHR.setEnabled(false);
            companyHR.setClickable(false);
            companyContact.setEnabled(false);
            companyContact.setClickable(false);
            companyEmail.setEnabled(false);
            companyEmail.setClickable(false);
            companyAddress.setEnabled(false);
            companyAddress.setClickable(false);
            linearLayout.setFocusable(true);
            linearLayout.setFocusableInTouchMode(true);
        }
    }

    private void fetchAndSetCompanyDetailsToEditTexts(DataSnapshot dataSnapshot) {
        String compHr = (String) dataSnapshot.child("HR").getValue();
        String compContact = (String) dataSnapshot.child("Contact_number").getValue();
        String compEmail = (String) dataSnapshot.child("Email").getValue();
        String compAddress = (String) dataSnapshot.child("Address").getValue();
        companyHR.setText(compHr);
        companyContact.setText(compContact);
        companyEmail.setText(compEmail);
        companyAddress.setText(compAddress);
    }

    private void initialize() {
        //EditText
        companyName = (EditText) findViewById(R.id.companyName);
        companyHR = (EditText) findViewById(R.id.companyHR);
        companyContact = (EditText) findViewById(R.id.companyContact);
        companyEmail = (EditText) findViewById(R.id.companyEmail);
        companyAddress = (EditText) findViewById(R.id.companyAddress);

        //Button
        update = (Button) findViewById(R.id.updateInfo);
        edit = (Button) findViewById(R.id.edit);

        //LinearLayout
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        update.setVisibility(View.GONE);
        companyName.setEnabled(false);
        companyName.setClickable(false);
        companyHR.setEnabled(false);
        companyHR.setClickable(false);
        companyContact.setEnabled(false);
        companyContact.setClickable(false);
        companyEmail.setEnabled(false);
        companyEmail.setClickable(false);
        companyAddress.setEnabled(false);
        companyAddress.setClickable(false);
    }
}
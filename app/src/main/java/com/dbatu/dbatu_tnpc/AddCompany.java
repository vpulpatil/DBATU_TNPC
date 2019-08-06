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

import com.firebase.client.Firebase;

public class AddCompany extends AppCompatActivity {

    private EditText companyName, companyHR, companyContact, companyEmail, companyAddress;
    private Button register;
    private LinearLayout linearLayout;

    private Firebase dbatu_tnpc_firebase_reference;
    private String COMPANY_FIREBASE_URL = "https://dbatu-tnpc-79d12.firebaseio.com/Company";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Add Company");

        initialize();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCompany();
            }
        });
    }

    private void initialize() {
        companyName = (EditText)findViewById(R.id.companyName);
        companyHR = (EditText)findViewById(R.id.companyHr);
        companyContact = (EditText)findViewById(R.id.companyContact);
        companyEmail = (EditText)findViewById(R.id.companyEmail);
        companyAddress = (EditText)findViewById(R.id.companyAddress);
        register = (Button)findViewById(R.id.registerCompany);
        linearLayout = (LinearLayout)findViewById(R.id.linearLayout);
    }

    private void addCompany() {
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

            Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();
        }
    }
}
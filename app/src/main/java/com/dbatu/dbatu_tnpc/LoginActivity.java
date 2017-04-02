package com.dbatu.dbatu_tnpc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.Map;

public class LoginActivity extends Activity {

    private EditText email, rollno, password;
    private TextInputLayout loginEmail, loginRoll, loginPassword;
    private Button login;
    private TextView clickToRegister;

    private DatabaseReference ref;
    private FirebaseAuth mAuth;

    private ProgressDialog mProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        //final String rollno1 = rollno.getText().toString().trim();
        //ref = FirebaseDatabase.getInstance().getReference().child("Student").child(rollno1);
        mProgress = new ProgressDialog(this);

        Instantiation();
        loginRoll.setVisibility(View.VISIBLE);
        loginEmail.setVisibility(View.GONE);
        loginPassword.setVisibility(View.GONE);
        login.setVisibility(View.VISIBLE);
        Selection();
        clickToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void checkUserExist(){

    }

    private void Selection() {
        final Spinner loginSpinner = (Spinner) findViewById(R.id.studentTnPCTPO);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.authority, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        loginSpinner.setAdapter(adapter);
        loginSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                parent.getItemAtPosition(position);
                if (position == 0){
                    loginRoll.setVisibility(View.VISIBLE);
                    loginEmail.setVisibility(View.GONE);
                    loginPassword.setVisibility(View.GONE);
                    login.setVisibility(View.VISIBLE);
                    login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String rollno1 = rollno.getText().toString().trim();
                            ref = FirebaseDatabase.getInstance().getReference().child("Student").child(rollno1);
                            if (!TextUtils.isEmpty(rollno1)) {
                                ref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Map<String, String> map = (Map)dataSnapshot.getValue();
                                        String id = map.get("Roll_Number");
                                        if (id == rollno1){
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        }else {
                                            Toast.makeText(getApplicationContext(), "You are not registered", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }else{
                                Toast.makeText(getApplicationContext(), "Please enter your roll number", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                if (position == 1){
                    loginRoll.setVisibility(View.GONE);
                    loginEmail.setVisibility(View.VISIBLE);
                    loginPassword.setVisibility(View.VISIBLE);
                    login.setVisibility(View.VISIBLE);
                    email.setText(null);
                    password.setText(null);
                    login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String email1 = email.getText().toString().trim();
                            final String password1 = password.getText().toString().trim();
                            if (!TextUtils.isEmpty(email1) && !TextUtils.isEmpty(password1)) {
                                mProgress.setMessage("logging in....");
                                mProgress.show();
                                mAuth.signInWithEmailAndPassword(email1, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()){
                                            Intent intent = new Intent(LoginActivity.this, TnPCActivity.class);
                                            startActivity(intent);
                                            mProgress.dismiss();
                                        }else {
                                            Toast.makeText(getApplicationContext(), "Error login", Toast.LENGTH_LONG).show();
                                            mProgress.dismiss();
                                        }
                                    }
                                });
                            }else{
                                Toast.makeText(getApplicationContext(), "Please enter your credentials", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                if (position == 2){
                    loginRoll.setVisibility(View.GONE);
                    loginEmail.setVisibility(View.VISIBLE);
                    loginPassword.setVisibility(View.VISIBLE);
                    login.setVisibility(View.VISIBLE);
                    email.setText(null);
                    password.setText(null);
                    login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String email1 = email.getText().toString().trim();
                            final String password1 = password.getText().toString().trim();
                            if (!TextUtils.isEmpty(email1) && !TextUtils.isEmpty(password1)) {
                                mProgress.setMessage("logging in....");
                                mProgress.show();
                                mAuth.signInWithEmailAndPassword(email1, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()){
                                            Intent intent = new Intent(LoginActivity.this, TPOActivity.class);
                                            startActivity(intent);
                                            mProgress.dismiss();
                                        }else {
                                            Toast.makeText(getApplicationContext(), "Error login", Toast.LENGTH_LONG).show();
                                            mProgress.dismiss();
                                        }
                                    }
                                });
                            }else{
                                Toast.makeText(getApplicationContext(), "Please enter your credentials", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //default
            }
        });
    }

    private void Instantiation() {
        //EditText
        email = (EditText)findViewById(R.id.loginEmail);
        password = (EditText)findViewById(R.id.loginPassword);
        rollno = (EditText)findViewById(R.id.loginRollno);

        //TextInputLayout
        loginEmail = (TextInputLayout)findViewById(R.id.loginEmailTextInputLayout);
        loginRoll = (TextInputLayout)findViewById(R.id.loginRollnoTextInputLayout);
        loginPassword = (TextInputLayout)findViewById(R.id.loginPasswordTextInputLayout);

        //Button
        login = (Button)findViewById(R.id.login);

        //TextView
        clickToRegister = (TextView)findViewById(R.id.clicktoregister);
    }
}
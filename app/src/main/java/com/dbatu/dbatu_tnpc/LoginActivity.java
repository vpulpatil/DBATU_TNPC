package com.dbatu.dbatu_tnpc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends Activity {

    public static EditText email, rollno, password;
    private TextInputLayout loginEmail, loginRoll, loginPassword;
    private Button login;
    private TextView clickToRegister;
    private LinearLayout linearLayout;

    private DatabaseReference ref;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mdata;

    private ProgressDialog mProgress;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        linearLayout = (LinearLayout)findViewById(R.id.loginActivity);

        mAuth = FirebaseAuth.getInstance();
        mdata = FirebaseDatabase.getInstance();
        ref = mdata.getReference("Student").getRef();
        ref.keepSynced(true);
        mProgress = new ProgressDialog(this);

        initialize();
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
            }
        });
    }

    private static long back_pressed;

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) super.onBackPressed();
        else {Snackbar snackbar = Snackbar.make(linearLayout, "Press once again to exit!", Snackbar.LENGTH_SHORT);
            snackbar.show();
            back_pressed = System.currentTimeMillis();
        }
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
                            if (!TextUtils.isEmpty(rollno1)) {
                                mProgress.setMessage("Logging in....");
                                mProgress.setCanceledOnTouchOutside(false);
                                mProgress.show();
                                ref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        boolean loginData = dataSnapshot.hasChild(rollno1);
                                        if (loginData) {
                                            Intent intent = new Intent(LoginActivity.this, StudentActivity.class);
                                            intent.putExtra("roll", rollno1);
                                            startActivity(intent);
                                            mProgress.dismiss();
                                            rollno.setText(null);
                                        }else {
                                            Snackbar snackbar = Snackbar.make(linearLayout, "You are not registered", Snackbar.LENGTH_SHORT);
                                            snackbar.show();
                                            mProgress.dismiss();
                                        }
                                        /*for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                            String logindata = String.valueOf(postSnapshot.child("Roll_Number").getValue());
                                            if (logindata.equals(rollno1)) {
                                                Intent intent = new Intent(LoginActivity.this, NewsDetails.class);
                                                startActivity(intent);
                                                finish();
                                                mProgress.dismiss();
                                                rollno.setText(null);
                                            } else {
                                                Toast.makeText(getApplicationContext(), "You are not registered", Toast.LENGTH_SHORT).show();
                                                mProgress.dismiss();
                                            }
                                        }*/
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }else{
                                Snackbar snackbar = Snackbar.make(linearLayout, "Please enter your roll number", Snackbar.LENGTH_SHORT);
                                snackbar.show();
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
                            final String emai11 = email.getText().toString().trim();
                            final String password1 = password.getText().toString().trim();
                            if (!TextUtils.isEmpty(emai11) && !TextUtils.isEmpty(password1)) {
                                mProgress.setMessage("Logging in....");
                                mProgress.setCanceledOnTouchOutside(false);
                                mProgress.show();
                                mAuth.signInWithEmailAndPassword(emai11, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()){
                                            checkIfTnpcUserExist();
                                            mProgress.dismiss();
                                        }else {
                                            Snackbar snackbar = Snackbar.make(linearLayout, "Invalid Email or Password", Snackbar.LENGTH_SHORT);
                                            snackbar.show();
                                            mProgress.dismiss();
                                        }
                                    }
                                });
                            }else{
                                Snackbar snackbar = Snackbar.make(linearLayout, "Please enter your credentials", Snackbar.LENGTH_SHORT);
                                snackbar.show();
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
                                mProgress.setMessage("Logging in....");
                                mProgress.setCanceledOnTouchOutside(false);
                                mProgress.show();
                                mAuth.signInWithEmailAndPassword(email1, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()){
                                            checkIfTPOUserExist();
                                            mProgress.dismiss();
                                        }else {
                                            Snackbar snackbar = Snackbar.make(linearLayout, "Invalid Email or Password", Snackbar.LENGTH_SHORT);
                                            snackbar.show();
                                            mProgress.dismiss();
                                        }
                                    }
                                });
                            }else{
                                Snackbar snackbar = Snackbar.make(linearLayout, "Please enter your credentials", Snackbar.LENGTH_SHORT);
                                snackbar.show();
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

    private void checkIfTPOUserExist() {
        final String user_id = mAuth.getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference().child("TPO");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(user_id)){
                    Intent intent = new Intent(LoginActivity.this, TPOActivity.class);
                    startActivity(intent);
                    mProgress.dismiss();
                    email.setText(null);
                    password.setText(null);
                } else {
                    Snackbar snackbar = Snackbar.make(linearLayout, "You are not a TPO", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    mProgress.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void checkIfTnpcUserExist() {
        final String user_id = mAuth.getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference().child("TnPC_Coordinator");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(user_id)){
                    Intent intent = new Intent(LoginActivity.this, TnPCActivity.class);
                    startActivity(intent);
                    mProgress.dismiss();
                    email.setText(null);
                    password.setText(null);
                } else {
                    Snackbar snackbar = Snackbar.make(linearLayout, "You are not a TnPC Coordinator", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    mProgress.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initialize() {
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
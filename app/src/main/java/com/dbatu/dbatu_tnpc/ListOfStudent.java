package com.dbatu.dbatu_tnpc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ListOfStudent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_student);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
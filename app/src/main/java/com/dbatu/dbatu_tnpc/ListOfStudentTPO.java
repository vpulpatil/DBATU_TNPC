package com.dbatu.dbatu_tnpc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListOfStudentTPO extends AppCompatActivity {

    private ProgressDialog progressDialog;

    private EditText search;

    private DatabaseReference ref;
    private FirebaseDatabase mdata;

    private ArrayAdapter arrayAdapter;
    private ListView studentList;
    private ArrayList<String> students = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_student_tpo);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("laoding...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        search = (EditText)findViewById(R.id.search);
        studentSearch();

        mdata = FirebaseDatabase.getInstance();
        ref = mdata.getReference("Student").getRef();

        studentList = (ListView)findViewById(R.id.studentList);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot student : dataSnapshot.getChildren()) {
                    students.add(student.getKey());
                    progressDialog.dismiss();
                }
                arrayAdapter = new ArrayAdapter(ListOfStudentTPO.this, android.R.layout.simple_list_item_1, students);
                studentList.setAdapter(arrayAdapter);
                studentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String rollno =(String) parent.getItemAtPosition(position);
                        Intent intent = new Intent(ListOfStudentTPO.this, StudentInfoTPO.class);
                        intent.putExtra("roll", rollno);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void studentSearch() {
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                arrayAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        students.clear();
        finish();
    }
}
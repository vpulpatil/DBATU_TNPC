package com.dbatu.dbatu_tnpc;

import android.content.Intent;
import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentInfoTnpc extends AppCompatActivity {

    private EditText studentName, studentGender, studentDOB, studentContact, studentEmail, studentAddress, studentAdmissionType,
            studentDept,studentRollno, studentSSC, studentHSC, studentDiploma, studentSGPA1, studentSGPA2, studentSGPA3,
            studentSGPA4, studentSGPA5, studentSGPA6, studentBacklog, studentExam;

    private Button update, edit;

    private DatabaseReference ref;
    private FirebaseDatabase mdata;
    private Firebase dbatu_tnpc_firebase_reference;
    private String STUDENT_FIREBASE_URL = "https://dbatu-tnpc-79d12.firebaseio.com/Student";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info_tnpc);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Student Information");

        initialize();

        Intent intent = getIntent();
        String rollNo = intent.getExtras().getString("roll");
        studentRollno.setText(rollNo);

        mdata = FirebaseDatabase.getInstance();
        assert rollNo != null;
        ref = mdata.getReference("Student").child(rollNo).getRef();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fetchAndSetDataToEditText(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editInfo();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInfo();
            }
        });
    }

    private void initialize() {
        studentName = (EditText) findViewById(R.id.studentName);
        studentGender = (EditText) findViewById(R.id.studentGender);
        studentDOB = (EditText) findViewById(R.id.studentDOB);
        studentContact = (EditText) findViewById(R.id.studentContactNumber);
        studentEmail = (EditText) findViewById(R.id.studentEmail);
        studentAddress = (EditText) findViewById(R.id.studentAddress);
        studentAdmissionType = (EditText) findViewById(R.id.studentAdmissionType);
        studentDept = (EditText) findViewById(R.id.studentDept);
        studentRollno = (EditText) findViewById(R.id.studentRollno);
        studentSSC = (EditText) findViewById(R.id.studentSSCPercentage);
        studentHSC = (EditText) findViewById(R.id.studentHSCPercentage);
        studentDiploma = (EditText) findViewById(R.id.studentDiplomaPercentage);
        studentSGPA1 = (EditText) findViewById(R.id.studentSGPA1);
        studentSGPA2 = (EditText) findViewById(R.id.studentSGPA2);
        studentSGPA3 = (EditText) findViewById(R.id.studentSGPA3);
        studentSGPA4 = (EditText) findViewById(R.id.studentSGPA4);
        studentSGPA5 = (EditText) findViewById(R.id.studentSGPA5);
        studentSGPA6 = (EditText) findViewById(R.id.studentSGPA6);
        studentBacklog = (EditText) findViewById(R.id.studentActiveBacklog);
        studentExam = (EditText) findViewById(R.id.interestedExams);

        //Button
        update = (Button) findViewById(R.id.updateInfo);
        edit = (Button) findViewById(R.id.edit_tnpc);
        update.setVisibility(View.GONE);

        studentName.setEnabled(false);
        studentName.setClickable(false);
        studentGender.setEnabled(false);
        studentGender.setClickable(false);
        studentDOB.setEnabled(false);
        studentDOB.setClickable(false);
        studentContact.setEnabled(false);
        studentContact.setClickable(false);
        studentEmail.setEnabled(false);
        studentEmail.setClickable(false);
        studentAddress.setEnabled(false);
        studentAddress.setClickable(false);
        studentAdmissionType.setEnabled(false);
        studentAdmissionType.setClickable(false);
        studentDept.setEnabled(false);
        studentDept.setClickable(false);
        studentRollno.setEnabled(false);
        studentRollno.setClickable(false);
        studentSSC.setEnabled(false);
        studentSSC.setClickable(false);
        studentHSC.setEnabled(false);
        studentHSC.setClickable(false);
        studentDiploma.setEnabled(false);
        studentDiploma.setClickable(false);
        studentSGPA1.setEnabled(false);
        studentSGPA1.setClickable(false);
        studentSGPA2.setEnabled(false);
        studentSGPA2.setClickable(false);
        studentSGPA3.setEnabled(false);
        studentSGPA3.setClickable(false);
        studentSGPA4.setEnabled(false);
        studentSGPA4.setClickable(false);
        studentSGPA5.setEnabled(false);
        studentSGPA5.setClickable(false);
        studentSGPA6.setEnabled(false);
        studentSGPA6.setClickable(false);
        studentBacklog.setEnabled(false);
        studentBacklog.setClickable(false);
        studentExam.setEnabled(false);
        studentExam.setClickable(false);
    }

    private void fetchAndSetDataToEditText(DataSnapshot dataSnapshot) {
        String name = (String) dataSnapshot.child("Full_Name").getValue();
        String gender = (String) dataSnapshot.child("Gender").getValue();
        String dob = (String) dataSnapshot.child("Date_Of_Birth").getValue();
        String contact = (String) dataSnapshot.child("Contact_Number").getValue();
        String email = (String) dataSnapshot.child("Email").getValue();
        String address = (String) dataSnapshot.child("Address").getValue();
        String type = (String) dataSnapshot.child("Admission_Type").getValue();
        String dept = (String) dataSnapshot.child("Department").getValue();
        String ssc = (String) dataSnapshot.child("SSC_Percentage").getValue();
        String hsc = (String) dataSnapshot.child("HSC_Percentage").getValue();
        String diploma = (String) dataSnapshot.child("Diploma_Percentage").getValue();
        String sgpa1 = (String) dataSnapshot.child("First_Semester").getValue();
        String sgpa2 = (String) dataSnapshot.child("Second_Semester").getValue();
        String sgpa3 = (String) dataSnapshot.child("Third_Semester").getValue();
        String sgpa4 = (String) dataSnapshot.child("Forth_Semester").getValue();
        String sgpa5 = (String) dataSnapshot.child("Fifth_Semester").getValue();
        String sgpa6 = (String) dataSnapshot.child("Sixth_Semester").getValue();
        String backlog = (String) dataSnapshot.child("Number_of_Active_Backlogs").getValue();
        String exam = (String) dataSnapshot.child("Appearing_for_Exam").getValue();

        studentName.setText(name);
        studentGender.setText(gender);
        studentDOB.setText(dob);
        studentContact.setText(contact);
        studentEmail.setText(email);
        studentAddress.setText(address);
        studentAdmissionType.setText(type);
        studentDept.setText(dept);
        studentSSC.setText(ssc);
        studentHSC.setText(hsc);
        studentDiploma.setText(diploma);
        studentSGPA1.setText(sgpa1);
        studentSGPA2.setText(sgpa2);
        studentSGPA3.setText(sgpa3);
        studentSGPA4.setText(sgpa4);
        studentSGPA5.setText(sgpa5);
        studentSGPA6.setText(sgpa6);
        studentBacklog.setText(backlog);
        studentExam.setText(exam);
    }

    private void editInfo() {
        studentName.setEnabled(true);
        studentName.setClickable(true);
        studentGender.setEnabled(true);
        studentGender.setClickable(true);
        studentDOB.setEnabled(true);
        studentDOB.setClickable(true);
        studentContact.setEnabled(true);
        studentContact.setClickable(true);
        studentEmail.setEnabled(true);
        studentEmail.setClickable(true);
        studentAddress.setEnabled(true);
        studentAddress.setClickable(true);
        studentAdmissionType.setEnabled(true);
        studentAdmissionType.setClickable(true);
        studentDept.setEnabled(true);
        studentDept.setClickable(true);
        studentRollno.setEnabled(true);
        studentRollno.setClickable(true);
        studentSSC.setEnabled(true);
        studentSSC.setClickable(true);
        studentHSC.setEnabled(true);
        studentHSC.setClickable(true);
        studentDiploma.setEnabled(true);
        studentDiploma.setClickable(true);
        studentSGPA1.setEnabled(true);
        studentSGPA1.setClickable(true);
        studentSGPA2.setEnabled(true);
        studentSGPA2.setClickable(true);
        studentSGPA3.setEnabled(true);
        studentSGPA3.setClickable(true);
        studentSGPA4.setEnabled(true);
        studentSGPA4.setClickable(true);
        studentSGPA5.setEnabled(true);
        studentSGPA5.setClickable(true);
        studentSGPA6.setEnabled(true);
        studentSGPA6.setClickable(true);
        studentBacklog.setEnabled(true);
        studentBacklog.setClickable(true);
        studentExam.setEnabled(true);
        studentExam.setClickable(true);
        edit.setVisibility(View.GONE);
        update.setVisibility(View.VISIBLE);
    }

    private void updateInfo() {
        String fullName = studentName.getText().toString().trim();
        String gender = studentGender.getText().toString().trim();
        String DOB = studentDOB.getText().toString().trim();
        String contactNumber = studentContact.getText().toString().trim();
        String email = studentEmail.getText().toString().trim();
        String address = studentAddress.getText().toString().trim();
        String admission = studentAdmissionType.getText().toString().trim();
        String department = studentDept.getText().toString().trim();
        String rollno = studentRollno.getText().toString().trim();
        String SSC = studentSSC.getText().toString().trim();
        String HSC = studentHSC.getText().toString().trim();
        String diplomaPercentage = studentDiploma.getText().toString().trim();
        String sgpa1 = studentSGPA1.getText().toString().trim();
        String sgpa2 = studentSGPA2.getText().toString().trim();
        String sgpa3 = studentSGPA3.getText().toString().trim();
        String sgpa4 = studentSGPA4.getText().toString().trim();
        String sgpa5 = studentSGPA5.getText().toString().trim();
        String sgpa6 = studentSGPA6.getText().toString().trim();
        String backlog = studentBacklog.getText().toString().trim();
        String exam = studentExam.getText().toString().trim();

        dbatu_tnpc_firebase_reference = new Firebase(STUDENT_FIREBASE_URL + "/" + rollno);

        //Updating data to firebase
        float sscf = Float.parseFloat(SSC);
        float hscf = Float.parseFloat(HSC);
        float sgpa1f = Float.parseFloat(sgpa1);
        float sgpa2f = Float.parseFloat(sgpa2);
        float sgpa3f = Float.parseFloat(sgpa3);
        float sgpa4f = Float.parseFloat(sgpa4);
        float sgpa5f = Float.parseFloat(sgpa5);
        float sgpa6f = Float.parseFloat(sgpa6);
        if (fullName.equals(null) || DOB.equals(null) || contactNumber.equals(null) || email.equals(null) ||
                address.equals(null) || rollno.equals(null) || SSC.equals(null) || HSC.equals(null) || sgpa1.equals(null) || sgpa2.equals(null)
                || sgpa3.equals(null) || sgpa4.equals(null) || sgpa5.equals(null) || sgpa6.equals(null)) {
            Toast.makeText(StudentInfoTnpc.this, "All * Fields are Compulsory", Toast.LENGTH_SHORT).show();
        }
        if (sscf < 0 || sscf > 100 || hscf < 0 || hscf > 100 || sgpa1f < 0 || sgpa1f > 10 || sgpa2f < 0 || sgpa2f > 10 || sgpa3f < 0 || sgpa3f > 10 || sgpa4f < 0 || sgpa4f > 10 || sgpa5f < 0 || sgpa5f > 10
                || sgpa6f < 0 || sgpa6f > 10) {
            Toast.makeText(getApplicationContext(), "Invalid Percentage or SGPA", Toast.LENGTH_SHORT).show();
        } else {
            Firebase fullNameChildRef = dbatu_tnpc_firebase_reference.child("Full_Name");
            fullNameChildRef.setValue(fullName);
            Firebase maleChildRef = dbatu_tnpc_firebase_reference.child("Gender");
            maleChildRef.setValue(gender);
            Firebase dobChildRef = dbatu_tnpc_firebase_reference.child("Date_Of_Birth");
            dobChildRef.setValue(DOB);
            Firebase contactNumberChildRef = dbatu_tnpc_firebase_reference.child("Contact_Number");
            contactNumberChildRef.setValue(contactNumber);
            Firebase emailChildRef = dbatu_tnpc_firebase_reference.child("Email");
            emailChildRef.setValue(email);
            Firebase addressChildRef = dbatu_tnpc_firebase_reference.child("Address");
            addressChildRef.setValue(address);
            Firebase departmentChildRef = dbatu_tnpc_firebase_reference.child("Department");
            departmentChildRef.setValue(department);
            Firebase rollnoChildRef = dbatu_tnpc_firebase_reference.child("Roll_Number");
            rollnoChildRef.setValue(rollno);
            Firebase sscChildRef = dbatu_tnpc_firebase_reference.child("SSC_Percentage");
            sscChildRef.setValue(SSC);
            if (admission.equals("First Year Admission")) {
                Firebase hscChildRef = dbatu_tnpc_firebase_reference.child("HSC_Percentage");
                hscChildRef.setValue(HSC);
                Firebase sgpa1ChildRef = dbatu_tnpc_firebase_reference.child("First_Semester");
                sgpa1ChildRef.setValue(sgpa1);
                Firebase sgpa2ChildRef = dbatu_tnpc_firebase_reference.child("Second_Semester");
                sgpa2ChildRef.setValue(sgpa2);
            } else {
                Firebase diplomaChildRef = dbatu_tnpc_firebase_reference.child("Diploma_Percentage");
                diplomaChildRef.setValue(diplomaPercentage);
            }
            Firebase sgpa3ChildRef = dbatu_tnpc_firebase_reference.child("Third_Semester");
            sgpa3ChildRef.setValue(sgpa3);
            Firebase sgpa4ChildRef = dbatu_tnpc_firebase_reference.child("Forth_Semester");
            sgpa4ChildRef.setValue(sgpa4);
            Firebase sgpa5ChildRef = dbatu_tnpc_firebase_reference.child("Fifth_Semester");
            sgpa5ChildRef.setValue(sgpa5);
            Firebase sgpa6ChildRef = dbatu_tnpc_firebase_reference.child("Sixth_Semester");
            sgpa6ChildRef.setValue(sgpa6);
            Firebase backlogChildRef = dbatu_tnpc_firebase_reference.child("Number_of_Active_Backlogs");
            backlogChildRef.setValue(backlog);

            Firebase examChildRef = dbatu_tnpc_firebase_reference.child("Appearing_for_Exam");
            examChildRef.setValue(exam);
            Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
            edit.setVisibility(View.VISIBLE);
            update.setVisibility(View.GONE);
            studentName.setEnabled(false);
            studentName.setClickable(false);
            studentGender.setEnabled(false);
            studentGender.setClickable(false);
            studentDOB.setEnabled(false);
            studentDOB.setClickable(false);
            studentContact.setEnabled(false);
            studentContact.setClickable(false);
            studentEmail.setEnabled(false);
            studentEmail.setClickable(false);
            studentAddress.setEnabled(false);
            studentAddress.setClickable(false);
            studentAdmissionType.setEnabled(false);
            studentAdmissionType.setClickable(false);
            studentDept.setEnabled(false);
            studentDept.setClickable(false);
            studentRollno.setEnabled(false);
            studentRollno.setClickable(false);
            studentSSC.setEnabled(false);
            studentSSC.setClickable(false);
            studentHSC.setEnabled(false);
            studentHSC.setClickable(false);
            studentDiploma.setEnabled(false);
            studentDiploma.setClickable(false);
            studentSGPA1.setEnabled(false);
            studentSGPA1.setClickable(false);
            studentSGPA2.setEnabled(false);
            studentSGPA2.setClickable(false);
            studentSGPA3.setEnabled(false);
            studentSGPA3.setClickable(false);
            studentSGPA4.setEnabled(false);
            studentSGPA4.setClickable(false);
            studentSGPA5.setEnabled(false);
            studentSGPA5.setClickable(false);
            studentSGPA6.setEnabled(false);
            studentSGPA6.setClickable(false);
            studentBacklog.setEnabled(false);
            studentBacklog.setClickable(false);
            studentExam.setEnabled(false);
            studentExam.setClickable(false);
        }
    }
}
package com.dbatu.dbatu_tnpc;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Activity;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Keyur on 20-10-2016.
 */

public class RegistrationActivity extends Activity {

    //various fields in registration form
    private EditText studentFirstName, studentMiddleName, studentLastName, studentContactNumber, studentEmail, studentAddress, studentRollno,
            studentSSCPercentage, studentHSCPercentage, studentDiplomaPercentage, students_sgpa1, students_sgpa2, students_sgpa3, students_sgpa4, students_sgpa5,
            students_sgpa6, specifyOtherExam;
    private RadioButton studentMale, studentFemale, firstYearAdmission, dplomaAdmission, exam_yes, exam_no;
    private LinearLayout academicDetails, examDetails;
    private TextView admissionDetails;
    private TextInputLayout sgpaFirstSem, sgpaSecondSem, hscPercentage, diplomaPercentage, specifyOtherExamDetails;
    private Button registerStudent;
    private CheckBox exam_other, exam_gate, exam_gre, exam_cat, exam_gmat, exam_cmat, exam_mpsc_upsc;
    private Spinner departmentDropdownList, backlogDropdownList;

    //for date of birth
    private TextView dateOfBirthView;
    private int year, month, day;

    //Firebase
    private Firebase dbatu_tnpc_firebase_reference;
    private String STUDENT_FIREBASE_URL = "https://dbatu-tnpc-79d12.firebaseio.com/Student";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        instantiation();
        getDateOfBirth();
        openAcademicDetailsLayout();
        getDepartment();
        getBacklogRecord();
        openOrCloseExamDetailsLayout();
        doStudentRegistration();
    }

    private void instantiation() {
        //EditText Fields
        studentFirstName = (EditText)findViewById(R.id.studentFirstName);
        studentMiddleName = (EditText)findViewById(R.id.studentMiddleName);
        studentLastName = (EditText)findViewById(R.id.studentLastName);
        studentContactNumber = (EditText)findViewById(R.id.studentContactNumber);
        studentEmail = (EditText)findViewById(R.id.studentEmail);
        studentAddress = (EditText)findViewById(R.id.studentAddress);
        studentRollno = (EditText)findViewById(R.id.studentRollno);
        studentSSCPercentage = (EditText)findViewById(R.id.studentSSCPercentage);
        studentHSCPercentage = (EditText)findViewById(R.id.studentHSCPercentage);
        studentDiplomaPercentage = (EditText)findViewById(R.id.studentDiplomaPercentage);
        students_sgpa1 = (EditText)findViewById(R.id.studentSGPA1);
        students_sgpa2 = (EditText)findViewById(R.id.studentSGPA2);
        students_sgpa3 = (EditText)findViewById(R.id.studentSGPA3);
        students_sgpa4 = (EditText)findViewById(R.id.studentSGPA4);
        students_sgpa5 = (EditText)findViewById(R.id.studentSGPA5);
        students_sgpa6 = (EditText)findViewById(R.id.studentSGPA6);
        specifyOtherExam = (EditText)findViewById(R.id.specifyOtherExamDetails);

        //TextInputLayout Fields
        hscPercentage = (TextInputLayout)findViewById(R.id.studentHSCTextInputLayout);
        diplomaPercentage = (TextInputLayout)findViewById(R.id.studentiDplomaTextInputLayout);
        sgpaFirstSem = (TextInputLayout)findViewById(R.id.studentSGPA1TextInputLayout);
        sgpaSecondSem = (TextInputLayout)findViewById(R.id.studentSGPA2TextInputLayout);
        specifyOtherExamDetails = (TextInputLayout)findViewById(R.id.specifyOtherExamDetailsTextInputLayout);

        //TextView Fields
        dateOfBirthView = (TextView) findViewById(R.id.dateofbirth);
        admissionDetails = (TextView)findViewById(R.id.typeOfAdmission);

        //Buttons
        registerStudent = (Button)findViewById(R.id.registerStudent);

        //Spinners
        departmentDropdownList = (Spinner)findViewById(R.id.studentDepartment);
        backlogDropdownList = (Spinner)findViewById(R.id.studentBacklogDropdownList);

        //RadioButtons
        studentMale = (RadioButton)findViewById(R.id.studentMale);
        studentFemale = (RadioButton)findViewById(R.id.studentFemale);
        firstYearAdmission = (RadioButton)findViewById(R.id.radioButtonfy);
        dplomaAdmission = (RadioButton)findViewById(R.id.radioButtonda);
        exam_yes = (RadioButton)findViewById(R.id.optionYes);
        exam_no = (RadioButton)findViewById(R.id.optionNo);

        //Checkbox
        exam_gate = (CheckBox)findViewById(R.id.exam_gate);
        exam_gre = (CheckBox)findViewById(R.id.exam_gre_toefl);
        exam_cat = (CheckBox)findViewById(R.id.exam_mba_mbacet);
        exam_gmat = (CheckBox)findViewById(R.id.exam_gmat);
        exam_cmat = (CheckBox)findViewById(R.id.exam_cmat);
        exam_mpsc_upsc = (CheckBox)findViewById(R.id.exam_mpsc_upsc);
        exam_other = (CheckBox)findViewById(R.id.exam_otherExams);

        //LinearLayout
        academicDetails = (LinearLayout)findViewById(R.id.academicDetails);
        examDetails = (LinearLayout)findViewById(R.id.examDetails);

        //Visibility of various fields
        registerStudent.setVisibility(View.GONE);
        academicDetails.setVisibility(View.GONE);
        examDetails.setVisibility(View.GONE);
        specifyOtherExamDetails.setVisibility(View.GONE);
    }

    private void getDateOfBirth() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);    //gets the Year
        month = calendar.get(Calendar.MONTH);      //gets the Month
        day = calendar.get(Calendar.DAY_OF_MONTH);  //gets the day
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            //arg1 = day
            //arg2+1 = month
            //arg3 = year
            showDate(arg1, arg2+1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        dateOfBirthView.setText(new StringBuilder().append(day).append("/").append(month).append("/").append(year));
    }

    private void openAcademicDetailsLayout() {
        firstYearAdmission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String validEmail = studentEmail.getText().toString().trim();
                if (!isValidEmail(validEmail)){
                    studentEmail.setError("Invalid Email");
                }else{
                    admissionDetails.setText("Academic Details (First Year Admission)");
                    hscPercentage.setVisibility(View.VISIBLE);
                    diplomaPercentage.setVisibility(View.GONE);
                    academicDetails.setVisibility(View.VISIBLE);
                    sgpaFirstSem.setVisibility(View.VISIBLE);
                    sgpaSecondSem.setVisibility(View.VISIBLE);
                }
            }
        });
        dplomaAdmission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String validEmail = studentEmail.getText().toString().trim();
                if (!isValidEmail(validEmail)){
                    studentEmail.setError("Invalid Email");
                }else {
                    admissionDetails.setText("Academic Details (Direct Second Year Admission)");
                    hscPercentage.setVisibility(View.GONE);
                    diplomaPercentage.setVisibility(View.VISIBLE);
                    sgpaFirstSem.setVisibility(View.GONE);
                    sgpaSecondSem.setVisibility(View.GONE);
                    academicDetails.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void getDepartment() {
        final Spinner deptspinner = (Spinner) findViewById(R.id.studentDepartment);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dept_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deptspinner.setAdapter(adapter);
        deptspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                parent.getItemAtPosition(position);
                if (position!=0)
                    Toast.makeText(RegistrationActivity.this, deptspinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getBacklogRecord() {
        final Spinner backLogSpinner = (Spinner) findViewById(R.id.studentBacklogDropdownList);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.back_log, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        backLogSpinner.setAdapter(adapter);
        backLogSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                parent.getItemAtPosition(position);
                if (position!=0)
                    Toast.makeText(RegistrationActivity.this, backLogSpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void openOrCloseExamDetailsLayout() {
        exam_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                examDetails.setVisibility(View.VISIBLE);
                registerStudent.setVisibility(View.VISIBLE);
                exam_other.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (exam_other.isChecked()) {
                            specifyOtherExamDetails.setVisibility(View.VISIBLE);
                        } else {
                            specifyOtherExamDetails.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        exam_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                examDetails.setVisibility(View.GONE);
                registerStudent.setVisibility(View.VISIBLE);
            }
        });
    }

    private void doStudentRegistration() {
        registerStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = studentFirstName.getText().toString().trim();
                String middleName = studentMiddleName.getText().toString().trim();
                String lastName = studentLastName.getText().toString().trim();
                String male = studentMale.getText().toString().trim();
                String female = studentFemale.getText().toString().trim();
                String DOB = dateOfBirthView.getText().toString().trim();
                String contactNumber = studentContactNumber.getText().toString().trim();
                String email = studentEmail.getText().toString().trim();
                String address = studentAddress.getText().toString().trim();
                String sFirstYearAdmission = firstYearAdmission.getText().toString().trim();
                String sDiplomaAdmission = dplomaAdmission.getText().toString().trim();
                String department = departmentDropdownList.getSelectedItem().toString().trim();
                String rollno = studentRollno.getText().toString().trim();
                String SSC = studentSSCPercentage.getText().toString().trim();
                String HSC = studentHSCPercentage.getText().toString().trim();
                String diplomaPercentage = studentDiplomaPercentage.getText().toString().trim();
                String sgpa1 = students_sgpa1.getText().toString().trim();
                String sgpa2 = students_sgpa2.getText().toString().trim();
                String sgpa3 = students_sgpa3.getText().toString().trim();
                String sgpa4 = students_sgpa4.getText().toString().trim();
                String sgpa5 = students_sgpa5.getText().toString().trim();
                String sgpa6 = students_sgpa6.getText().toString().trim();
                String backlog = backlogDropdownList.getSelectedItem().toString().trim();
                String yes_exam = exam_yes.getText().toString().trim();
                String no_exam = exam_no.getText().toString().trim();

                String exams = null;
                if (exam_gate.isChecked() || exam_gre.isChecked() || exam_cat.isChecked() || exam_gmat.isChecked() || exam_cmat.isChecked() || exam_mpsc_upsc.isChecked() || exam_other.isChecked()){
                    if (exam_gate.isChecked()) {
                        exams = exam_gate.getText().toString().trim();
                    }
                    if (exam_gre.isChecked()) {
                        String gre_exam = exam_gre.getText().toString().trim();
                        exams = exams+", "+gre_exam;
                    }
                    if (exam_cat.isChecked()) {
                        String cat_exam = exam_cat.getText().toString().trim();
                        exams = exams+", "+cat_exam;
                    }
                    if (exam_gmat.isChecked()) {
                        String gmat_exam = exam_gmat.getText().toString().trim();
                        exams = exams+", "+gmat_exam;
                    }
                    if (exam_cmat.isChecked()) {
                        String cmat_exam = exam_cmat.getText().toString().trim();
                        exams = exams+", "+cmat_exam;
                    }
                    if (exam_mpsc_upsc.isChecked()) {
                        String mpsc_upsc_exam = exam_mpsc_upsc.getText().toString().trim();
                        exams = exams+", "+mpsc_upsc_exam;
                    }
                    if (exam_other.isChecked()) {
                        String specify_other = specifyOtherExam.getText().toString().trim();
                        exams = exams+", "+specify_other;
                    }
                }

                dbatu_tnpc_firebase_reference = new Firebase(STUDENT_FIREBASE_URL + "/" + rollno);

                //Adding data to firebase
                float sscf = Float.parseFloat(SSC);
                float hscf = Float.parseFloat(HSC);
                float sgpa1f = Float.parseFloat(sgpa1);
                float sgpa2f = Float.parseFloat(sgpa2);
                float sgpa3f = Float.parseFloat(sgpa3);
                float sgpa4f = Float.parseFloat(sgpa4);
                float sgpa5f = Float.parseFloat(sgpa5);
                float sgpa6f = Float.parseFloat(sgpa6);
                if (firstName.equals(null)||lastName.equals(null)||DOB.equals(null)||contactNumber.equals(null)||email.equals(null)||
                        address.equals(null)||rollno.equals(null)||SSC.equals(null)||HSC.equals(null)||sgpa1.equals(null)||sgpa2.equals(null)
                        ||sgpa3.equals(null) ||sgpa4.equals(null)||sgpa5.equals(null)|| sgpa6.equals(null)){
                    Toast.makeText(RegistrationActivity.this, "All * Fields are Compulsory", Toast.LENGTH_SHORT).show();
                }if (sscf<0||sscf>100||hscf<0||hscf>100||sgpa1f<0||sgpa1f>10||sgpa2f<0||sgpa2f>10||sgpa3f<0||sgpa3f>10||sgpa4f<0||sgpa4f>10||sgpa5f<0||sgpa5f>10
                        ||sgpa6f<0||sgpa6f>10) {
                    Toast.makeText(getApplicationContext(), "Invalid Percentage or SGPA", Toast.LENGTH_SHORT).show();
                }else{
                    Firebase firstNameChildRef = dbatu_tnpc_firebase_reference.child("First Name");
                    firstNameChildRef.setValue(firstName);
                    Firebase middleNameChildRef = dbatu_tnpc_firebase_reference.child("Middle Name");
                    middleNameChildRef.setValue(middleName);
                    Firebase studentChildRef = dbatu_tnpc_firebase_reference.child("Last Name");
                    studentChildRef.setValue(lastName);
                    if (studentMale.isChecked()) {
                        Firebase maleChildRef = dbatu_tnpc_firebase_reference.child("Gender");
                        maleChildRef.setValue(male);
                    } if (studentFemale.isChecked()){
                        Firebase femaleChildRef = dbatu_tnpc_firebase_reference.child("Gender");
                        femaleChildRef.setValue(female);
                    }
                    Firebase dobChildRef = dbatu_tnpc_firebase_reference.child("Date Of Birth");
                    dobChildRef.setValue(DOB);
                    Firebase contactNumberChildRef = dbatu_tnpc_firebase_reference.child("Contact Number");
                    contactNumberChildRef.setValue(contactNumber);
                    Firebase emailChildRef = dbatu_tnpc_firebase_reference.child("Email");
                    emailChildRef.setValue(email);
                    Firebase addressChildRef = dbatu_tnpc_firebase_reference.child("Address");
                    addressChildRef.setValue(address);
                    if (firstYearAdmission.isChecked()) {
                        Firebase fyaChildRef = dbatu_tnpc_firebase_reference.child("Admission Type");
                        fyaChildRef.setValue(sFirstYearAdmission);
                        Firebase hscChildRef = dbatu_tnpc_firebase_reference.child("HSC Percentage");
                        hscChildRef.setValue(HSC);
                        Firebase sgpa1ChildRef = dbatu_tnpc_firebase_reference.child("First Semester");
                        sgpa1ChildRef.setValue(sgpa1);
                        Firebase sgpa2ChildRef = dbatu_tnpc_firebase_reference.child("Second Semester");
                        sgpa2ChildRef.setValue(sgpa2);
                    } else {
                        Firebase syaChildRef = dbatu_tnpc_firebase_reference.child("Admission Type");
                        syaChildRef.setValue(sDiplomaAdmission);
                        Firebase diplomaChildRef = dbatu_tnpc_firebase_reference.child("Diploma Percentage");
                        diplomaChildRef.setValue(diplomaPercentage);
                    }
                    Firebase departmentChildRef = dbatu_tnpc_firebase_reference.child("Department");
                    departmentChildRef.setValue(department);
                    Firebase rollnoChildRef = dbatu_tnpc_firebase_reference.child("Roll Number");
                    rollnoChildRef.setValue(rollno);
                    Firebase sscChildRef = dbatu_tnpc_firebase_reference.child("SSC Percentage");
                    sscChildRef.setValue(SSC);
                    Firebase sgpa3ChildRef = dbatu_tnpc_firebase_reference.child("Third Semester");
                    sgpa3ChildRef.setValue(sgpa3);
                    Firebase sgpa4ChildRef = dbatu_tnpc_firebase_reference.child("Forth Semester");
                    sgpa4ChildRef.setValue(sgpa4);
                    Firebase sgpa5ChildRef = dbatu_tnpc_firebase_reference.child("Fifth Semester");
                    sgpa5ChildRef.setValue(sgpa5);
                    Firebase sgpa6ChildRef = dbatu_tnpc_firebase_reference.child("Sixth Semester");
                    sgpa6ChildRef.setValue(sgpa6);
                    Firebase backlogChildRef = dbatu_tnpc_firebase_reference.child("Number of Active Backlogs");
                    backlogChildRef.setValue(backlog);
                    if (exam_yes.isChecked()) {
                        Firebase yesChildRef = dbatu_tnpc_firebase_reference.child("Interested In Higher Studies");
                        yesChildRef.setValue(yes_exam);
                        Firebase examChildRef = dbatu_tnpc_firebase_reference.child("Appearing for Exam");
                        examChildRef.setValue(exams);
                    } else {
                        Firebase noChildRef = dbatu_tnpc_firebase_reference.child("Interested In Higher Studies");
                        noChildRef.setValue(no_exam);
                    }
                    Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
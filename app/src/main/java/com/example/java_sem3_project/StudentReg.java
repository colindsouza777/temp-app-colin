package com.example.java_sem3_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentReg extends AppCompatActivity {

    EditText regemail , regpassword, regrollno, regname, regbatch, regphone;
    Button regbtn;
    RadioGroup yeargroup, branchgroup;
    RadioButton selectedyear, selectedbranch;


    FirebaseDatabase rootnode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_reg);

        regemail =(EditText) findViewById(R.id.sregemail);
        regpassword = (EditText)findViewById(R.id.sregpassword);
        regbtn = (Button) findViewById(R.id.sregbtn);
        regrollno = (EditText)findViewById(R.id.sregrollno);
        regname = (EditText)findViewById(R.id.sregname);
        regbatch = (EditText)findViewById(R.id.sregbatch);
        regphone = (EditText)findViewById(R.id.sregphone);
        yeargroup =  findViewById(R.id.sregyear);
        branchgroup =  findViewById(R.id.sregbranch);


        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootnode = FirebaseDatabase.getInstance();
                reference = rootnode.getReference("student");

                int selectedyearId = yeargroup.getCheckedRadioButtonId();
                int selectedbranchId = branchgroup.getCheckedRadioButtonId();

                selectedyear = findViewById(selectedyearId);
                selectedbranch = findViewById(selectedbranchId);

                String name = regname.getText().toString();
                String email = regemail.getText().toString();
                String rollno = regrollno.getText().toString();
                String batch = regbatch.getText().toString();
                String phone = regphone.getText().toString();
                String  year  = selectedyear.getText().toString();
                String  branch  = selectedbranch.getText().toString();
                String password = regpassword.getText().toString();

                Student student = new Student( name,  email,  phone,  rollno,  year,  branch, batch,  password);

                reference.child(rollno).setValue(student);

            }
        });
    }
}
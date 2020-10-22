package com.example.java_sem3_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity {

    EditText loginrollno , loginpasswrd;
    Button loginbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        loginrollno = (EditText) findViewById(R.id.loginrollno);
        loginpasswrd = (EditText)findViewById(R.id.loginpassword);
        loginbtn = findViewById(R.id.loginbtn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(loginrollno.length()<1){
                    loginrollno.setError("Please enter roll no");
                    return;
                }
                else if(loginpasswrd.length()<1){
                    loginpasswrd.setError("Please enter password");
                    return;
                }
                else{
                    isUser();
                }
            }

        });

    }

    public void isUser(){

        final String enteredrollno = loginrollno.getText().toString().trim();
        final String enteredpassword = loginpasswrd.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("student");
        Query checkUser = reference.orderByChild("username").equalTo(enteredrollno);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String passwordfromDB = dataSnapshot.child(enteredrollno).child("password").getValue(String.class);
                    loginrollno.setError(null);
//                    loginrollno.setErrorEnabled(false);

                    if(passwordfromDB.equals(enteredpassword)){
                        loginpasswrd.setError(null);

                        String namefromDB = dataSnapshot.child(enteredrollno).child("name").getValue(String.class);
                        String emailfromDB = dataSnapshot.child(enteredrollno).child("email").getValue(String.class);
                        String rollnofromDB = dataSnapshot.child(enteredrollno).child("rollno").getValue(String.class);
                        String yearfromDB = dataSnapshot.child(enteredrollno).child("year").getValue(String.class);
                        String branchfromDB = dataSnapshot.child(enteredrollno).child("branch").getValue(String.class);
                        String phonefromDB = dataSnapshot.child(enteredrollno).child("phone").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), StudentReg.class);

                        intent.putExtra("name",namefromDB);
                        intent.putExtra("email",emailfromDB);
                        intent.putExtra("rollno",rollnofromDB);
                        intent.putExtra("year",yearfromDB);
                        intent.putExtra("branch",branchfromDB);
                        intent.putExtra("phone",phonefromDB);

                        startActivity(intent);
                        System.out.println("Login successful");
                    }
                    else{
                        loginpasswrd.setError("Incorrect password");
                        loginpasswrd.requestFocus();
                    }
                }
                else{
                    loginrollno.setError("Student does not exist");
                    loginrollno.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}
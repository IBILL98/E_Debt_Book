package com.example.e_debt_book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class CostumerRegister extends AppCompatActivity {

    //Sign up attributes
    EditText costumerRegisterName ,costumerRegisterLastName,costumerRegisterPassword,costumerRegisterPhone,costumerRegisterEmail;
    Button costumerRegisterBackButton,costumerRegisterSignUpButtom;
    FirebaseAuth fAuth;
    ProgressBar costumerRegisterProgressBar;
    ConstraintLayout costumerRegister;



    //// Number verification attributes
    TextView textView;
    EditText verificationNumber;
    Button verificationBackButton,verificationButtom;
    ConstraintLayout numberVerificiation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costumer_register);


        //Sign up attributes
        costumerRegisterName = findViewById(R.id.costumerRegisterName);
        costumerRegisterLastName = findViewById(R.id.costumerRegisterLastName);
        costumerRegisterPassword = findViewById(R.id.costumerRegisterPassword);
        costumerRegisterPhone = findViewById(R.id.costumerRegisterPhone);
        costumerRegisterEmail = findViewById(R.id.costumerRegisterEmail);
        costumerRegisterBackButton = findViewById(R.id.costumerRegisterBackButton);
        costumerRegisterSignUpButtom = findViewById(R.id.costumerRegisterSignUpButtom);
        costumerRegister = findViewById(R.id.costumerRegister);



        //// Number verification attributes
        textView = findViewById(R.id.textView);
        verificationNumber = findViewById(R.id.verificationNumber);
        verificationBackButton = findViewById(R.id.verificationBackButton);
        verificationButtom = findViewById(R.id.verificationButtom);
        numberVerificiation = findViewById(R.id.numberVerificiation);


        fAuth = FirebaseAuth.getInstance();

        costumerRegisterProgressBar = findViewById(R.id.costumerRegisterProgressBar);

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        costumerRegisterSignUpButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = costumerRegisterEmail.getText().toString().trim();
                String password = costumerRegisterPassword.getText().toString().trim();
                final String phone = costumerRegisterPhone.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    costumerRegisterEmail.setError("Email is Required.");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    costumerRegisterPassword.setError("Password is Required.");
                    return;
                }
                if(password.length() < 7){
                    costumerRegisterPassword.setError("Password must be at least 8 characters");
                    return;
                }
                costumerRegisterProgressBar.setVisibility(View.VISIBLE);
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(CostumerRegister.this, "User Created ..", Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(getApplicationContext(),NumberVerification.class));

                            costumerRegister.setVisibility(View.GONE);
                            numberVerificiation.setVisibility(View.VISIBLE);

                            verificationButtom.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    textView.setText(textView.toString() + "\n" + phone);
                                }
                            });


                        }else{
                            Toast.makeText(CostumerRegister.this, "Error !! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        });
    }
}
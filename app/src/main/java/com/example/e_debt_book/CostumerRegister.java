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

import com.example.e_debt_book.model.Costumer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class CostumerRegister extends AppCompatActivity {

    //Sign up attributes
    EditText costumerRegisterName ,costumerRegisterLastName,costumerRegisterPassword,costumerRegisterPhone,costumerRegisterEmail;
    Button costumerRegisterBackButton,costumerRegisterSignUpButtom;
    ProgressBar costumerRegisterProgressBar;
    ConstraintLayout costumerRegister;


    //Firebase attributes
    FirebaseAuth fAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mRootRef,conditionRef;


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
                String phone = costumerRegisterPhone.getText().toString().trim();

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
                if(TextUtils.isEmpty(phone)){
                    costumerRegisterPhone.setError("Please Entere the Phone Number");
                    return;
                }

                costumerRegisterProgressBar.setVisibility(View.VISIBLE);


                ////Adding the info into Database
                mRootRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference conditionRef = mRootRef.child("Costumers");
                Map<String, Costumer> costumers = new HashMap<>();
                Costumer cos = new Costumer(costumerRegisterName.getText().toString(),
                                            costumerRegisterLastName.getText().toString(),
                                            costumerRegisterEmail.getText().toString(),0);
                cos.setPhone(null);
                costumers.put(costumerRegisterPhone.getText().toString(),cos);
                conditionRef.setValue(costumers);


                //Create the login informations
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(CostumerRegister.this, "User Created ..", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(getApplicationContext(),NumberVerification.class));

                        }else{
                            Toast.makeText(CostumerRegister.this, "Error !! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        });
    }
}
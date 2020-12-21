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
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class NumberVerification extends AppCompatActivity {


    private TextView textView,verificationPhone;
    private EditText verificationNumber;
    private Button verificationLaterButton,verificationButtom,sendCodeButton;
    private ConstraintLayout numberVerificiation;
    private FirebaseAuth mAuth ;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private ProgressBar verificationProgressBar;
    DatabaseReference mRootRef,conditionRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_verification);
        mRootRef = FirebaseDatabase.getInstance().getReference();

        conditionRef = mRootRef.child("Costumers");


        Costumer costumer = (Costumer) getIntent().getSerializableExtra("Costumer");
        System.out.println(costumer.toString());
        String phone = "+90" + costumer.getPhone();

        //// Number verification attributes
        textView = findViewById(R.id.textView);
        verificationNumber = findViewById(R.id.verificationNumber);
        verificationLaterButton = findViewById(R.id.verificationLaterButton);
        verificationButtom = findViewById(R.id.verificationButtom);
        numberVerificiation = findViewById(R.id.numberVerificiation);
        sendCodeButton = findViewById(R.id.sendCodeButton);
        verificationProgressBar = findViewById(R.id.verificationProgressBar);

        verificationPhone = findViewById(R.id.verificationPhone);

        mAuth = FirebaseAuth.getInstance();
        mAuth.useAppLanguage();

        verificationPhone.setText(phone);


        verificationButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vcode = verificationNumber.getText().toString();
                if (TextUtils.isEmpty(vcode)){
                    Toast.makeText(NumberVerification.this, "Please Entere your code", Toast.LENGTH_SHORT).show();
                }else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, vcode);
                    signInWithPhoneAuthCredential(credential ,costumer);

                }
            }
        });


        verificationLaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                startActivity(new Intent(getApplicationContext(),CostumerMain.class));
                finish();
            }
        });


        sendCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificationProgressBar.setVisibility(View.VISIBLE);
                sendCodeButton.setVisibility(View.GONE);
                PhoneAuthOptions options =
                        PhoneAuthOptions.newBuilder(mAuth)
                                .setPhoneNumber(phone)       // Phone number to verify
                                .setTimeout(120L, TimeUnit.SECONDS) // Timeout and unit
                                .setActivity(NumberVerification.this)                 // Activity (for callback binding)
                                .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                                .build();
                PhoneAuthProvider.verifyPhoneNumber(options);
            }
        });



        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                signInWithPhoneAuthCredential(phoneAuthCredential ,costumer);

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(NumberVerification.this, "The Number Couldnt be verified Try again please ..", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
                Toast.makeText(NumberVerification.this, "Code has been sent ,check your messages please", Toast.LENGTH_SHORT).show();
                verificationProgressBar.setVisibility(View.INVISIBLE);
            }
        };

    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential,Costumer costumer) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            costumer.setStatus(1);
                            conditionRef.child(costumer.getPhone()).child("status").setValue(costumer.getStatus());

                            FirebaseUser user = task.getResult().getUser();Toast.makeText(NumberVerification.this, "Done", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(getApplicationContext(),CostumerMain.class));


                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid

                                String message = task.getException().toString();
                                Toast.makeText(NumberVerification.this, "Error : " + message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

}
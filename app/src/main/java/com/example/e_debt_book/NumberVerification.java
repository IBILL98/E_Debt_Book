package com.example.e_debt_book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.media.session.IMediaControllerCallback;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class NumberVerification extends AppCompatActivity {


    private TextView textView;
    private EditText verificationNumber;
    private Button verificationLaterButton,verificationButtom,sendCodeButton;
    private ConstraintLayout numberVerificiation;
    private FirebaseAuth mAuth ;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_verification);


        //// Number verification attributes
        textView = findViewById(R.id.textView);
        verificationNumber = findViewById(R.id.verificationNumber);
        verificationLaterButton = findViewById(R.id.verificationLaterButton);
        verificationButtom = findViewById(R.id.verificationButtom);
        numberVerificiation = findViewById(R.id.numberVerificiation);
        sendCodeButton = findViewById(R.id.sendCodeButton);

        mAuth = FirebaseAuth.getInstance();
        mAuth.setLanguageCode("tr");







        verificationButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vcode = verificationNumber.getText().toString();
                if (TextUtils.isEmpty(vcode)){
                    Toast.makeText(NumberVerification.this, "Please Entere your code", Toast.LENGTH_SHORT).show();
                }else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, vcode);
                    signInWithPhoneAuthCredential(credential);

                }
            }
        });


        verificationLaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });


        sendCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //sendCodeButton.setVisibility(View.GONE);
                PhoneAuthOptions options =
                        PhoneAuthOptions.newBuilder(mAuth)
                                .setPhoneNumber("+905526262622")       // Phone number to verify
                                .setTimeout(10L, TimeUnit.SECONDS) // Timeout and unit
                                .setActivity(NumberVerification.this)                 // Activity (for callback binding)
                                .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                                .build();
                PhoneAuthProvider.verifyPhoneNumber(options);
            }
        });

        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
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

            }
        };

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(NumberVerification.this, "Done", Toast.LENGTH_SHORT).show();
                            ////change the verification statue in the data base and move to the main screen..
                        } else {
                            String message = task.getException().toString();
                            Toast.makeText(NumberVerification.this, "Error : " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
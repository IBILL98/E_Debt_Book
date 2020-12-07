package com.example.e_debt_book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";


    ////Main screen Choice attributes
    private ConstraintLayout mainChoice;
    private Button mainMarketButton;
    private Button mainCostumertButton;


    ////Main screen costumer login attribute
    private ConstraintLayout costumerLogin;
    private Button costumerBackButton;
    private Button costumertSignUpButton;
    private Button costumerLoginButton;
    private EditText costumerLoginEmail;
    private EditText costumerLoginPassword;


    ////Main screen Market login attribute
    private ConstraintLayout marketLogin;
    private Button MarketBackButton;


    private FirebaseAuth mAuth;

    private TextView textView;
    private Button who;
    private String text;
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference mRootRef,conditionRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();


        ////Main screen Choice attributes
        mainChoice = findViewById(R.id.mainChoice);
        mainMarketButton = findViewById(R.id.mainMarketButton);
        mainCostumertButton = findViewById(R.id.mainCostumertButton);

        ////Main screen costumer login attribute
        costumerLogin = findViewById(R.id.costumerLogin);
        costumerBackButton = findViewById(R.id.costumerBackButton);
        costumerLoginButton = findViewById(R.id.costumerLoginButton);
        costumertSignUpButton = findViewById(R.id.costumertSignUpButton);
        costumerLoginEmail = findViewById(R.id.costumerLoginEmail);
        costumerLoginPassword = findViewById(R.id.costumerLoginPassword);


        ////Main screen Market login attribute
        costumerLogin = findViewById(R.id.costumerLogin);
        marketLogin = findViewById(R.id.marketLogin);
        MarketBackButton = findViewById(R.id.MarketBackButton);



        mainCostumertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainChoice.setVisibility(View.GONE);
                costumerLogin.setVisibility(View.VISIBLE);
            }
        });


        mainMarketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainChoice.setVisibility(View.GONE);
                marketLogin.setVisibility(View.VISIBLE);
            }
        });


        MarketBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marketLogin.setVisibility(View.GONE);
                mainChoice.setVisibility(View.VISIBLE);
            }
        });

        costumerBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                costumerLogin.setVisibility(View.GONE);
                mainChoice.setVisibility(View.VISIBLE);
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

        costumertSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CostumerRegister.class));
            }
        });

        costumerLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = costumerLoginEmail.getText().toString().trim();
                String password = costumerLoginPassword.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    costumerLoginEmail.setError("Email is Required.");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    costumerLoginPassword.setError("Password is Required.");
                    return;
                }
                if(password.length() < 6){
                    costumerLoginPassword.setError("Password must be at least 7 characters");
                    return;
                }

                mRootRef = FirebaseDatabase.getInstance().getReference();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    // checking if the Costumer has verified his phone number before
                                    conditionRef = mRootRef.child("Costumers");
                                    conditionRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for(DataSnapshot data: dataSnapshot.getChildren()){
                                                String status = data.child("status").getValue().toString();
                                                if (status.equals("0")){
                                                    startActivity(new Intent(getApplicationContext(),NumberVerification.class));
                                                    finish();
                                                }

                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });



                                    // Sign in success, update UI with the signed-in user's information
                                    //Log.d(TAG, "signInWithEmail:success");

                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);

                                    startActivity(new Intent(getApplicationContext(),CostumerMain.class));

                                } else {
                                    // If sign in fails, display a message to the user.
                                    //Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                    // ...
                                }

                                // ...
                            }
                        });
            }
        });


/*
        firebaseDatabase = FirebaseDatabase.getInstance();
        mRootRef = FirebaseDatabase.getInstance().getReference();
        conditionRef = mRootRef.child("Costumers").child("456165").child("first_name");
        who = (Button)findViewById(R.id.who);
        textView = (TextView)findViewById(R.id.textView);*/
    }
/*
    @Override
    protected void onStart() {
        super.onStart();

        conditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                text = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        who.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(text);
            }
        });
    }*/


    //Change UI according to user data.
    public void updateUI(FirebaseUser account){

        if(account != null){
            Toast.makeText(this,"U Signed In successfully",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,CostumerMain.class));

        }else {
            Toast.makeText(this,"U Didnt signed in",Toast.LENGTH_LONG).show();
        }

    }
}
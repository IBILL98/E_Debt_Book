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
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_debt_book.model.Customer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private Button mainCustomertButton;


    ////Main screen customer login attribute
    private ConstraintLayout customerLogin;
    private Button customerBackButton;
    private Button customertSignUpButton;
    private Button customerLoginButton;
    private EditText customerLoginEmail;
    private EditText customerLoginPassword;


    ////Main screen Market login attribute
    private ConstraintLayout marketLogin;
    private Button marketBackButton;
    private Button marketSignUpButton;


    private FirebaseAuth mAuth;

    private TextView textView;
    private Button who;
    private String text;
    DatabaseReference mRootRef,conditionRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        final Customer loginUser1 = new Customer();


        ////Main screen Choice attributes
        mainChoice = findViewById(R.id.mainChoice);
        mainMarketButton = findViewById(R.id.mainMarketButton);
        mainCustomertButton = findViewById(R.id.mainCustomertButton);

        ////Main screen customer login attribute
        customerLogin = findViewById(R.id.customerLogin);
        customerBackButton = findViewById(R.id.customerBackButton);
        customerLoginButton = findViewById(R.id.customerLoginButton);
        customertSignUpButton = findViewById(R.id.customertSignUpButton);
        customerLoginEmail = findViewById(R.id.customerLoginEmail);
        customerLoginPassword = findViewById(R.id.customerLoginPassword);


        ////Main screen Market login attribute
        marketLogin = findViewById(R.id.marketLogin);
        marketBackButton = findViewById(R.id.marketBackButton);
        marketSignUpButton = findViewById(R.id.marketSignUpButton);



        mainCustomertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainChoice.setVisibility(View.GONE);
                customerLogin.setVisibility(View.VISIBLE);
            }
        });


        mainMarketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainChoice.setVisibility(View.GONE);
                marketLogin.setVisibility(View.VISIBLE);
            }
        });


        marketBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marketLogin.setVisibility(View.GONE);
                mainChoice.setVisibility(View.VISIBLE);
            }
        });

        customerBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customerLogin.setVisibility(View.GONE);
                mainChoice.setVisibility(View.VISIBLE);
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

        customertSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CustomerRegister.class));
            }
        });

        marketSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MarketRegister.class));
            }
        });


        customerLoginButton.setOnClickListener(new View.OnClickListener() {
            Customer customer = new Customer(customerLoginEmail.toString());
            @Override
            public void onClick(View v) {
                String email = customerLoginEmail.getText().toString().trim();
                String password = customerLoginPassword.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    customerLoginEmail.setError("Email is Required.");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    customerLoginPassword.setError("Password is Required.");
                    return;
                }
                if(password.length() < 6){
                    customerLoginPassword.setError("Password must be at least 7 characters");
                    return;
                }

                mRootRef = FirebaseDatabase.getInstance().getReference();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    // checking if the Customer has verified his phone number before
                                    conditionRef = mRootRef.child("Customers");
                                    conditionRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for(DataSnapshot data: dataSnapshot.getChildren()){
                                                String userId = data.getKey();
                                                Customer loginUser = data.getValue(Customer.class);
                                                loginUser.setPhone(userId);
                                                if (loginUser.getEmail().equals(email)){
                                                    loginUser1.setEmail(loginUser.getEmail());
                                                    loginUser1.setPhone(loginUser.getPhone());
                                                    loginUser1.setLastname(loginUser.getLastname());
                                                    loginUser1.setName(loginUser.getName());
                                                    loginUser1.setStatus(loginUser.getStatus());
                                                    if (loginUser.getStatus() == 0){
                                                        Intent i = new Intent(MainActivity.this,NumberVerification.class);
                                                        Bundle b = new Bundle();
                                                        b.putSerializable("Customer",loginUser);
                                                        i.putExtras(b);
                                                        startActivity(i);
                                                        finish();
                                                    }
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });



                                    // Sign in success, update UI with the signed-in user's information
                                    //Log.d(TAG, "signInWithEmail:success");

                                    //FirebaseUser user = mAuth.getCurrentUser();
                                    //updateUI(user);

                                    Intent i = new Intent(MainActivity.this, CustomerMain.class);
                                    Bundle b = new Bundle();
                                    b.putSerializable("Customer",loginUser1);
                                    i.putExtras(b);
                                    startActivity(i);
                                    finish();
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
        conditionRef = mRootRef.child("Custumers").child("456165").child("first_name");
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
            startActivity(new Intent(this, CustomerMain.class));

        }else {
            Toast.makeText(this,"U Didnt signed in",Toast.LENGTH_LONG).show();
        }

    }
}
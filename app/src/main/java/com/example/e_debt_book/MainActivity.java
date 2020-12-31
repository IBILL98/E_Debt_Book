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
import com.example.e_debt_book.model.Market;
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
    private Button marketLoginButton;
    private EditText marketLoginEmail;
    private EditText marketLoginPassword;
    static int usertype = 1;

    private FirebaseAuth mAuth;

    private TextView textView;
    private Button who;
    private String text;
    DatabaseReference mRootRef, conditionRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();


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
        marketLoginButton = findViewById(R.id.marketLoginButton);
        marketLoginEmail = findViewById(R.id.marketLoginEmail);
        marketLoginPassword = findViewById(R.id.marketLoginPassword);


        mainCustomertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainChoice.setVisibility(View.GONE);
                customerLogin.setVisibility(View.VISIBLE);
                usertype = 0;
                System.out.println(usertype);
            }
        });


        mainMarketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainChoice.setVisibility(View.GONE);
                marketLogin.setVisibility(View.VISIBLE);
                usertype = 1;
                System.out.println(usertype);
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
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
                startActivity(new Intent(getApplicationContext(), MarketRegister.class));
            }
        });


        customerLoginButton.setOnClickListener(new View.OnClickListener() {
            Customer customer = new Customer(customerLoginEmail.toString());

            @Override
            public void onClick(View v) {
                String email = customerLoginEmail.getText().toString().trim();
                String password = customerLoginPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    customerLoginEmail.setError("Email is Required.");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    customerLoginPassword.setError("Password is Required.");
                    return;
                }
                if (password.length() < 6) {
                    customerLoginPassword.setError("Password must be at least 7 characters");
                    return;
                }

                mRootRef = FirebaseDatabase.getInstance().getReference();


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Customer loginUser = new Customer();
                            getCustomer(email,new MyCallback() {
                                @Override
                                public void onCallback(Customer customer) {
                                    loginUser.setStatus(customer.getStatus());
                                    loginUser.setLastname(customer.getLastname());
                                    loginUser.setName(customer.getName());
                                    loginUser.setEmail(customer.getEmail());
                                    loginUser.setPhone(customer.getPhone());

                                    if (loginUser.getStatus() == 0) {
                                        Intent i = new Intent(MainActivity.this, NumberVerification.class);
                                        Bundle b = new Bundle();
                                        b.putSerializable("Customer", loginUser);
                                        i.putExtras(b);
                                        startActivity(i);
                                        finish();
                                    } else {
                                        Intent i = new Intent(MainActivity.this, CustomerMain.class);
                                        Bundle b = new Bundle();
                                        b.putSerializable("Customer", loginUser);
                                        i.putExtras(b);
                                        startActivity(i);
                                        finish();
                                    }

                                }
                            });


                            if (loginUser.getEmail() == null){
                                getMarket(email, new MyCallbackMarket() {
                                    @Override
                                    public void onCallback(Market market) {
                                        if (market.getEmail() != null){
                                            System.out.println("///////////////////////////////////////////////////////////////////////\n///////////////////////////////////////////////////////");
                                            System.out.println(market.getEmail());
                                            Toast.makeText(MainActivity.this, "Please Make Sure Of Your User Type",
                                                    Toast.LENGTH_SHORT).show();
                                            updateUI(null);
                                        }else{
                                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                                    Toast.LENGTH_SHORT).show();
                                            updateUI(null);
                                        }

                                    }
                                });
                            }


                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithEmail:success");

                            //FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);


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

        marketLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = marketLoginEmail.getText().toString().trim();
                String password = marketLoginPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    customerLoginEmail.setError("Email is Required.");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    customerLoginPassword.setError("Password is Required.");
                    return;
                }
                if (password.length() < 6) {
                    customerLoginPassword.setError("Password must be at least 7 characters");
                    return;
                }

                mRootRef = FirebaseDatabase.getInstance().getReference();

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Market loginUser = new Market();
                                    getMarket(email,new MyCallbackMarket() {
                                        @Override
                                        public void onCallback(Market market) {
                                            loginUser.setEmail(market.getEmail());
                                            loginUser.setPhone(market.getPhone());
                                            loginUser.setIban(market.getIban());
                                            loginUser.setName(market.getName());
                                            loginUser.setAdress(market.getAdress());
                                            loginUser.setPassword(market.getPassword());
                                            loginUser.setStatus(market.getStatus());

                                            if (loginUser.getStatus() == 0) {
                                                Intent i = new Intent(MainActivity.this, NumberVerification.class);
                                                Bundle b = new Bundle();
                                                b.putSerializable("Market", loginUser);
                                                i.putExtras(b);
                                                startActivity(i);
                                                finish();
                                            } else {
                                                Intent i = new Intent(MainActivity.this, MarketMain.class);
                                                Bundle b = new Bundle();
                                                b.putSerializable("Market", loginUser);
                                                i.putExtras(b);
                                                startActivity(i);
                                                finish();
                                            }

                                        }
                                    });


                                    if (loginUser.getEmail() == null){
                                        getCustomer(email, new MyCallback() {
                                            @Override
                                            public void onCallback(Customer customer) {
                                                if (customer.getEmail() != null){
                                                    System.out.println("///////////////////////////////////////////////////////////////////////\n///////////////////////////////////////////////////////");
                                                    System.out.println(customer.getEmail());
                                                    Toast.makeText(MainActivity.this, "Please Make Sure Of Your User Type",
                                                            Toast.LENGTH_SHORT).show();
                                                    updateUI(null);
                                                }else{
                                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                                            Toast.LENGTH_SHORT).show();
                                                    updateUI(null);
                                                }

                                            }
                                        });
                                    }


                                    // Sign in success, update UI with the signed-in user's information
                                    //Log.d(TAG, "signInWithEmail:success");

                                    //FirebaseUser user = mAuth.getCurrentUser();
                                    //updateUI(user);


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

    }

    //Change UI according to user data.
    public void updateUI(FirebaseUser account) {

        if (account != null) {
            Toast.makeText(this, "U Signed In successfully", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, CustomerMain.class));

        } else {
            Toast.makeText(this, "U Didnt signed in", Toast.LENGTH_LONG).show();
        }

    }

    public void getCustomer(String email,MyCallback myCallback) {

        Customer loginUser1 = new Customer();
        conditionRef = mRootRef.child("Customers");
        conditionRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String userId = data.getKey();
                    Customer loginUser = data.getValue(Customer.class);
                    loginUser.setPhone(userId);
                    if (loginUser.getEmail().equals(email)) {
                        loginUser1.setEmail(loginUser.getEmail());
                        loginUser1.setPhone(loginUser.getPhone());
                        loginUser1.setLastname(loginUser.getLastname());
                        loginUser1.setName(loginUser.getName());
                        loginUser1.setStatus(loginUser.getStatus());
                        myCallback.onCallback(loginUser1);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public Market getMarket(String email, MyCallbackMarket myCallbackMarket) {
        Market marketlogin = new Market();

        conditionRef = mRootRef.child("Markets");
        conditionRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String userId = data.getKey();
                    Market loginUser = data.getValue(Market.class);
                    loginUser.setPhone(userId);
                    if (loginUser.getEmail().equals(email)) {
                        marketlogin.setEmail(loginUser.getEmail());
                        marketlogin.setPhone(loginUser.getPhone());
                        marketlogin.setIban(loginUser.getIban());
                        marketlogin.setName(loginUser.getName());
                        marketlogin.setAdress(loginUser.getAdress());
                        marketlogin.setPassword(loginUser.getPassword());
                        marketlogin.setStatus(loginUser.getStatus());
                        myCallbackMarket.onCallback(marketlogin);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return marketlogin;
    }


    private interface MyCallback {
        void onCallback(Customer customer);
    }

    private interface MyCallbackMarket {
        void onCallback(Market market);
    }



}

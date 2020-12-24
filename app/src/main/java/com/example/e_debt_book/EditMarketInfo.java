package com.example.e_debt_book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.e_debt_book.model.Market;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
//Firebase attributes


public class EditMarketInfo extends AppCompatActivity {

    EditText editInfoName , editInfoEmail , editInfoPhone , editInfoPassword, editInfoIban , editInfoAdress;
    Button marketeditBackButton2, marketeditDoneButton2;


    FirebaseAuth fAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mRootRef,conditionRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_market_info);

        Market market = (Market) getIntent().getSerializableExtra("Market");


        marketeditBackButton2 = findViewById(R.id.marketeditBackButton2);
        marketeditDoneButton2 = findViewById(R.id.marketeditDoneButton2);
        editInfoName = findViewById(R.id.editInfoName);
        editInfoEmail = findViewById(R.id.editInfoEmail);
        editInfoPhone = findViewById(R.id.editInfoPhone);
        editInfoPassword = findViewById(R.id.editInfoPassword);
        editInfoIban = findViewById(R.id.editInfoIban);
        editInfoAdress = findViewById(R.id.editInfoAdress);





        editInfoName.setText(market.getName());
        editInfoEmail.setText(market.getEmail());
        editInfoPhone.setText(market.getPhone());
        editInfoPassword.setText(market.getPassword());
        editInfoIban.setText(market.getIban());
        editInfoAdress.setText(market.getAdress());


        marketeditDoneButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                String name = editInfoName.getText().toString().trim();
                String email = editInfoEmail.getText().toString().trim();
                String phone = editInfoPhone.getText().toString().trim();
                String password = editInfoPassword.getText().toString().trim();
                String iban = editInfoIban.getText().toString().trim();
                String adress = editInfoAdress.getText().toString().trim();


                    // every thins down this line is bullshitt
                conditionRef = mRootRef.child("Markets");
                Market market = new Market(name,password,phone,email,iban,adress,0);

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("server/saving-data/fireblog");

                DatabaseReference usersRef = ref.child("users");

                Map<String, Market> users = new HashMap<>();
                users.put("alanisawesome", new Market(name,password,phone,email,iban,adress,0));

                usersRef.setValue(users);




            }
        });











        marketeditBackButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                Intent i = new Intent(EditMarketInfo.this, MarketMain.class);
                Bundle b = new Bundle();
                b.putSerializable("Market",market);
                i.putExtras(b);
                startActivity(i);
                finish();

            }
        });

    }
}
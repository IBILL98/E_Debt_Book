package com.example.e_debt_book;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_debt_book.model.Debt;
import com.example.e_debt_book.model.Market;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class myCustomers extends AppCompatActivity {
    //sign up attributes
    ListView debtsList;
    Button addNewDebtButton;
    ArrayList<String> list;
    ArrayList<Debt> debtsArray;
    ArrayAdapter<String> adapter;
    Debt debt;
    //Firebase attributes
    /*FirebaseAuth kAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mRootRef,conditionRef;*/
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_customers);
        /*kAuth = FirebaseAuth.getInstance();
        if(kAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        mRootRef = FirebaseDatabase.getInstance().getReference();*/
        debtsList = findViewById(R.id.debtsList);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Debt");
        list = new ArrayList<>();
        debtsArray = new ArrayList<Debt>();
        debt = new Debt();
        Market market = (Market) getIntent().getSerializableExtra("Market");
        String marketPhone = market.getPhone();
        adapter = new ArrayAdapter<String>(this, R.layout.debts_infos_resource, R.id.debtsInfosText, list);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    debt = ds.getValue(Debt.class);
                    assert debt != null;
                    if (debt.getMarketPhone()==marketPhone) {
                        list.add(debt.getCustomerPhone() + "," + debt.getAmount());
                        debtsArray.add(debt);
                    }
                }
                debtsList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        debtsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), debtsDetails.class);
                intent.putExtra("market", market);
                intent.putExtra("debt", debtsArray.get(position));
                startActivity(intent);
            }
        });
    }
}
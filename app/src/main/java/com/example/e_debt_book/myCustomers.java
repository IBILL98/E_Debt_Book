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

import com.example.e_debt_book.model.Customer;
import com.example.e_debt_book.model.Debt;
import com.example.e_debt_book.model.Market;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class myCustomers extends AppCompatActivity {
    ListView debtsList;
    FloatingActionButton addNewDebtButton;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    ArrayList<Debt> debtsArray;
    Debt debt;
    Customer customer;

    FirebaseDatabase database;
    DatabaseReference reference;
    DatabaseReference reference2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_customers);
        debtsList = findViewById(R.id.debtsList);
        addNewDebtButton = findViewById(R.id.addNewDebtButton);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Debt");
        reference2 = database.getReference("Customer");
        list = new ArrayList<>();
        debt = new Debt();
        customer = new Customer();
        debtsArray = new ArrayList<>();
        Market market = (Market) getIntent().getSerializableExtra("Market");
        adapter = new ArrayAdapter<String>(this, R.layout.debts_infos_resource, R.id.debtsInfosText, list);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    debt = ds.getValue(Debt.class);
                    assert debt != null;
                    if(debt.getMarketPhone() == market.getPhone()) {
                        list.add(debt.getCustomerPhone()+ ", " + debt.getAmount());
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
                int i=position;
                Debt selectedDebt = debtsArray.get(i);
                Intent intent = new Intent(myCustomers.this, debtsDetails.class);
                Bundle bundle = new Bundle();
                reference2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds: snapshot.getChildren()) {
                            customer = ds.getValue(Customer.class);
                            assert customer != null;
                            if (customer.getPhone() == selectedDebt.getCustomerPhone()) {
                                bundle.putSerializable("Customer", customer);
                                intent.putExtras(bundle);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                //Intent s = new Intent(myCustomers.this,debtsDetails.class);
                //Bundle b = new Bundle();
                bundle.putSerializable("Market",market);
                bundle.putSerializable("Debt",selectedDebt);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

        addNewDebtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(myCustomers.this,addNewDebt.class);
                Bundle b = new Bundle();
                b.putSerializable("Market",market);
                i.putExtras(b);
                startActivity(i);
                finish();
            }
        });
    }
}
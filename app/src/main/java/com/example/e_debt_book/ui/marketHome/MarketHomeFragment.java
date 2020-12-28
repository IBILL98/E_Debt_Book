package com.example.e_debt_book.ui.marketHome;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.e_debt_book.R;
import com.example.e_debt_book.addNewDebt;
import com.example.e_debt_book.debtsDetails;
import com.example.e_debt_book.model.Customer;
import com.example.e_debt_book.model.Debt;
import com.example.e_debt_book.model.Market;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MarketHomeFragment extends Fragment {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container ,Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_market_home, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        debtsList = getView().findViewById(R.id.debtsList);
        addNewDebtButton = getView().findViewById(R.id.addNewDebtButton3);

        database = FirebaseDatabase.getInstance();

        reference = database.getReference("Debt");
        reference2 = database.getReference("Customer");

        list = new ArrayList<>();
        debt = new Debt();
        customer = new Customer();
        debtsArray = new ArrayList<>();

        Market market = (Market) getActivity().getIntent().getSerializableExtra("Market");

        adapter = new ArrayAdapter<String>(getActivity(), R.layout.debts_infos_resource, R.id.debtsInfosText, list);

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
                Intent intent = new Intent(getActivity(), debtsDetails.class);
                Bundle bundle = new Bundle();
                reference2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds: snapshot.getChildren()) {
                            customer = ds.getValue(Customer.class);
                            assert customer != null;
                            if (customer.getPhone() == selectedDebt.getCustomerPhone()) {
                                bundle.putSerializable("Customer", customer);
                                //intent.putExtras(bundle);
                                break;
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                bundle.putSerializable("Market",market);
                bundle.putSerializable("Debt",selectedDebt);
                intent.putExtras(bundle);
                startActivity(intent);
                getActivity().finish();
            }
        });

        addNewDebtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), addNewDebt.class);
                Bundle b = new Bundle();
                b.putSerializable("Market",market);
                i.putExtras(b);
                startActivity(i);
                getActivity().finish();
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("My Customers");
    }
}
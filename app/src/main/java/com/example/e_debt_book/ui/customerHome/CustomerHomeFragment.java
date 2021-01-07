package com.example.e_debt_book.ui.customerHome;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.e_debt_book.R;
import com.example.e_debt_book.model.Customer;
import com.example.e_debt_book.model.Debt;
import com.example.e_debt_book.model.Item;
import com.example.e_debt_book.model.Market;
import com.example.e_debt_book.ui.marketHome.MarketHomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerHomeFragment extends Fragment {
    // رح نجيب رقم التلقون تبع الماركت وبدور عالدين يلي فيو رقم التلفون هاد نفسو وبعدين بحطن كلن بالقائمة

    private CustomerHomeViewModel customerHomeViewModel;

    FloatingActionButton addNewDebtButton;
    DatabaseReference mRootRef, conditionRef;
    ListView debtsListOfaCustomer;
    ArrayList<Debt> arrayList = new ArrayList<>();
    float totaldebts;
    TextView TitleTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer_home, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();

        arrayList.clear();
        totaldebts = 0;
        mRootRef = FirebaseDatabase.getInstance().getReference();
        //attaching the listView from the fragment
        debtsListOfaCustomer = getActivity().findViewById(R.id.debtsListOfaCustomer);
        //a text view that writes: debts of a customer
        TitleTextView = getActivity().findViewById(R.id.textViewUpCustomerDebtsList);
        Customer customer = (Customer) getActivity().getIntent().getSerializableExtra("Customer");
        CustomerHomeFragment.MyAdapter arrayAdapter = new CustomerHomeFragment.MyAdapter(getActivity(),arrayList);
        debtsListOfaCustomer.setAdapter(arrayAdapter);
        conditionRef = mRootRef.child("Debts");
        Query query = conditionRef.orderByChild("marketPhone").equalTo(customer.getPhone());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    String debtId = data.getKey();
                    Debt debt = data.getValue(Debt.class);
                    totaldebts = Float.parseFloat(debt.getAmount()) + totaldebts;
                    getitems(debtId, new CustomerHomeFragment.MyCallback() {
                        @Override
                        public void onCallback(ArrayList<Item> itemArrayList) {
                            debt.setItemList(itemArrayList);
                            debt.setDebtID(debtId);
                            arrayList.add(debt);
                            arrayAdapter.notifyDataSetChanged();
                        }
                    });
                }
                TitleTextView.setText(TitleTextView.getText().toString() + " " + totaldebts);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public class MyAdapter extends ArrayAdapter<Debt> {
        public MyAdapter(Context context, ArrayList<Debt> debts){
            super(context, 0, debts);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Debt debt = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.my_listview, parent, false);
            }
            // Lookup view for data population
            TextView name = (TextView) convertView.findViewById(R.id.fullName);
            TextView amount = (TextView) convertView.findViewById(R.id.amount);
            TextView phone = (TextView) convertView.findViewById(R.id.phone);
            TextView date = (TextView) convertView.findViewById(R.id.date);
            // Populate the data into the template view using the data object
            phone.setText(debt.getCustomerPhone());
            amount.setText(debt.getAmount());
            date.setText(debt.getDateOfLoan());
            DatabaseReference CustomerHomeFragment = FirebaseDatabase.getInstance().getReference();
            // Return the completed view to render on screen
            return convertView;
        }
    }
    private interface MyCallback {
        void onCallback(ArrayList<Item> arrayList);
    }

    //getting the items of a specific debt
    public ArrayList<Item> getitems(String id, CustomerHomeFragment.MyCallback myCallback) {
        ArrayList<Item> itemlist = new ArrayList<>();
        DatabaseReference conditionRefitems = mRootRef.child("Debts").child(id).child("itemList");
        conditionRefitems.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    Item item = data.getValue(Item.class);
                    Item item1 = new Item(item.getName(), item.getPrice());
                    itemlist.add(item1);
                }
                myCallback.onCallback(itemlist);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return itemlist;
    }
}
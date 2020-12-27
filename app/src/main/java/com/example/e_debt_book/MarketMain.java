package com.example.e_debt_book;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.e_debt_book.model.Market;
import com.google.firebase.auth.FirebaseAuth;

public class MarketMain extends AppCompatActivity {

    Button mainMarketAddCustomertButton ,mainMarketeditinfoButton , mainMarketmyCutomertButton,mainMarketLogoutButton;
    ConstraintLayout passwordconfirmationmeny , mainmarketmenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_main1);

        mainMarketAddCustomertButton = findViewById(R.id.mainMarketAddCustomertButton);
        mainMarketeditinfoButton = findViewById(R.id.mainMarketeditinfoButton);
        mainMarketmyCutomertButton = findViewById(R.id.mainMarketmyCustomertButton);
        mainMarketLogoutButton = findViewById(R.id.mainMarketLogoutButton);

        Market market = (Market) getIntent().getSerializableExtra("Market");




        mainMarketAddCustomertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MarketMain.this,addCustomerFromMarket.class);
                Bundle b = new Bundle();
                b.putSerializable("Market",market);
                i.putExtras(b);
                startActivity(i);
                finish();
            }
        });

        mainMarketeditinfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MarketMain.this, EditMarketInfo.class);
                Bundle b = new Bundle();
                b.putSerializable("Market",market);
                i.putExtras(b);
                startActivity(i);
                finish();

            }
        });

        mainMarketmyCutomertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MarketMain.this, myCustomers.class);
                Bundle b = new Bundle();
                b.putSerializable("Market",market);
                i.putExtras(b);
                startActivity(i);
                finish();
            }
        });

        mainMarketLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });













    }
}
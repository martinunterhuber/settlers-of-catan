package com.example.settlersofcatan.ui.trade;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.settlersofcatan.R;
import com.example.settlersofcatan.game.trade.TradeOffer;
import com.google.android.material.tabs.TabLayout;

/**
 * Activity to facilitate trade with the bank/harbors and other players, opens as popup over the
 * game activity. Uses a tablayout with one tab for trading with players, and the other for trading with
 * the bank/harbors.
 */
public class TradeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_trade);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this);

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }
    
    public void waitForReply(TradeOffer tradeOffer) {
        Intent i = new Intent(getApplicationContext(), WaitForReplyActivity.class);
        i.putExtra("tradeoffer",(Parcelable) tradeOffer);
        startActivity(i);
        finish();
    }
}
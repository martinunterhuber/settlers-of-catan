package com.example.settlersofcatan.ui.trade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.settlersofcatan.R;
import com.example.settlersofcatan.game.Game;
import com.example.settlersofcatan.game.TradeOffer;
import com.example.settlersofcatan.server_client.GameClient;

public class WaitForReplyActivity extends AppCompatActivity {
    TradeOffer tradeOffer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_for_reply);
        Intent i = getIntent();
        tradeOffer = i.getParcelableExtra("tradeoffer");
        GameClient.getInstance().registerWaitForReplyActivity(this);
    }

    public void getReply(Boolean reply) {
        if (reply) {
            tradeOffer.getTo().acceptTradeOffer(tradeOffer);
            tradeOffer.getFrom().tradeOfferWasAccepted(tradeOffer);
        }
        finish();
    }
}
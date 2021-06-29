package com.example.settlersofcatan.ui.trade;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.settlersofcatan.R;
import com.example.settlersofcatan.game.Game;
import com.example.settlersofcatan.game.trade.TradeOffer;
import com.example.settlersofcatan.server_client.GameClient;

public class ReceiveTradeOfferActivity extends AppCompatActivity {
    private TradeOffer tradeOffer;

    private ReceiveTradeFragment receiveTradeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_trade_offer);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        //How big the popup is in relation to game activity screen
        getWindow().setLayout((int) (width * .8), (int) (height * .75));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        getWindow().setAttributes(params);



        receiveTradeFragment = (ReceiveTradeFragment) getSupportFragmentManager().findFragmentById(R.id.receiveTradeView);
        Button denyBtn = findViewById(R.id.denyBtn);
        Button acceptBtn = findViewById(R.id.acceptBtn);
        Button counterOfferBtn = findViewById(R.id.counterOfferBtn);
        TextView offerFromTxt = findViewById(R.id.offerFromTxt);

        Intent i = getIntent();
        tradeOffer = (TradeOffer) i.getParcelableExtra("tradeoffer");

        offerFromTxt.setText(getString(R.string.trade_offer_from, tradeOffer.getFrom().getName()));

        if (!Game.getInstance().getPlayerById(GameClient.getInstance().getId()).isEligibleForTradeOffer(tradeOffer)) {
            acceptBtn.setVisibility(View.GONE);
        }

        acceptBtn.setOnClickListener(v -> {
            Game.getInstance().sendTradeOfferReply(true);
            Game.getInstance().getPlayerById(GameClient.getInstance().getId()).acceptTradeOffer(tradeOffer);
            finish();
                }
        );

        denyBtn.setOnClickListener(v -> {
            Game.getInstance().sendTradeOfferReply(false);
            finish();
                }
        );

        counterOfferBtn.setOnClickListener(v -> {
            TradeOffer t = new TradeOffer(tradeOffer.getTo(), tradeOffer.getFrom());
            t.setGive(receiveTradeFragment.getGiveInventoryView().getContent());
            t.setReceive(receiveTradeFragment.getReceiveInventoryView().getContent());
            Game.getInstance().sendTradeOffer(t);
            Intent intent = new Intent(getApplicationContext(), WaitForReplyActivity.class);
            intent.putExtra("tradeoffer", t);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onPostCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        receiveTradeFragment.setResourceViews(tradeOffer);
    }
}
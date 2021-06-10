package com.example.settlersofcatan.ui.trade;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Button;

import com.example.settlersofcatan.R;
import com.example.settlersofcatan.game.Game;
import com.example.settlersofcatan.game.TradeOffer;
import com.example.settlersofcatan.server_client.GameClient;
import com.google.android.material.tabs.TabLayout;

public class ReceiveTradeOfferActivity extends AppCompatActivity {
    private TradeOffer tradeOffer;

    private ReceiveTradeFragment receiveTradeFragment;
    private Button denyBtn;
    private Button acceptBtn;

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
        denyBtn = findViewById(R.id.denyBtn);
        acceptBtn = findViewById(R.id.acceptBtn);

        Intent i = getIntent();
        tradeOffer = (TradeOffer) i.getParcelableExtra("tradeoffer");

        acceptBtn.setOnClickListener(v -> {
            Game.getInstance().sendTradeOfferReply(true);
            finish();
                }
        );
        denyBtn.setOnClickListener(v -> {
            Game.getInstance().sendTradeOfferReply(false);
            finish();
                }
        );


    }

    @Override
    protected void onPostCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        receiveTradeFragment.setResourceViews(tradeOffer);
    }
}
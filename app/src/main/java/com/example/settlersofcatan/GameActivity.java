package com.example.settlersofcatan;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.example.settlersofcatan.game.Game;
import com.example.settlersofcatan.game.Player;
import com.example.settlersofcatan.server_client.GameClient;

public class GameActivity extends AppCompatActivity {

    private MapView map;
    private PlayerView player;
    private ResourceView resources;
    private OpponentView opponent1;
    private OpponentView opponent2;
    private OpponentView opponent3;
    private Button endTurnButton;

    static final int[] playerColors = new int[]{
            Color.parseColor("#05A505"),
            Color.parseColor("#F44336"),
            Color.parseColor("#FF9800"),
            Color.parseColor("#2196F3"), };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        map=findViewById(R.id.mapView);
        player=findViewById(R.id.playerView);
        player.setHexGrid(map.getHexGrid());
        player.invalidate();
        resources=findViewById(R.id.resourceView);

        opponent1=findViewById(R.id.opponent1);

        opponent2=findViewById(R.id.opponent2);

        opponent3=findViewById(R.id.opponent3);

        endTurnButton = findViewById(R.id.endTurnButton);
        endTurnButton.setOnClickListener((v) -> Game.getInstance().endTurn(GameClient.getInstance().getId()));

        GameClient.getInstance().registerActivity(this);

        setButtonToPlayerColor();
    }

    private void setButtonToPlayerColor(){
        Player player = Game.getInstance().getPlayerById(GameClient.getInstance().getId());
        ((ImageView)findViewById(R.id.btn_city)).setImageResource(PlayerView.CITY_IDS[player.getId()]);
        ((ImageView)findViewById(R.id.btn_road)).setImageResource(PlayerView.ROAD_IDS[player.getId()]);
        ((ImageView)findViewById(R.id.btn_settlement)).setImageResource(PlayerView.SETTLEMENT_IDS[player.getId()]);
    }

    /**
     *  TODO onClick Event to build road, settlement and city
     */
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_road:
                player.buildRoad();
                break;
            case R.id.btn_settlement:
                player.buildSettlement();
                break;
            case R.id.btn_city:
                player.buildCity();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.quit_lobby_title)
                .setMessage(R.string.quit_lobby_confirmation)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    GameClient client = GameClient.getInstance();
                    if (client != null){
                        new Thread(client::disconnect).start();
                    }
                    finish();
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

}
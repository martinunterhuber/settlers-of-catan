package com.example.settlersofcatan;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GameActivity extends AppCompatActivity {

    private MapView map;
    private PlayerView player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        map=findViewById(R.id.mapView);
        player=findViewById(R.id.playerView);
        player.setHexGrid(map.getHexGrid());
        player.invalidate();
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

}
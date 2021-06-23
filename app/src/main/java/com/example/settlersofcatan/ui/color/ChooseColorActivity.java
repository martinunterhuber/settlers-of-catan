package com.example.settlersofcatan.ui.color;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.settlersofcatan.R;
import com.example.settlersofcatan.game.Game;
import com.example.settlersofcatan.server_client.GameClient;
import com.example.settlersofcatan.server_client.networking.dto.ColorMessage;
import com.example.settlersofcatan.ui.game.GameActivity;

import java.util.HashMap;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


public class ChooseColorActivity extends AppCompatActivity {

    private View red;
    private View orange;
    private View blue;
    private View green;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_color);

        red=findViewById(R.id.view_red);
        red.setOnClickListener(view -> {
            Game.getInstance().getPlayerById(GameClient.getInstance().getId()).setColor("RED");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                unselectColor();
                red.setForeground(getDrawable(R.drawable.current_player_border));
            }
            new Thread(()->
                    GameClient.getInstance().sendMessage(new ColorMessage(GameClient.getInstance().getId(),"RED"))
            ).start();

        });

        orange=findViewById(R.id.view_orange);
        orange.setOnClickListener(view -> {
            Game.getInstance().getPlayerById(GameClient.getInstance().getId()).setColor("ORANGE");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                unselectColor();
                orange.setForeground(getDrawable(R.drawable.current_player_border));
            }
            new Thread(()->
                    GameClient.getInstance().sendMessage(new ColorMessage(GameClient.getInstance().getId(),"ORANGE"))
            ).start();

        });

        blue=findViewById(R.id.view_blue);
        blue.setOnClickListener(view -> {
            Game.getInstance().getPlayerById(GameClient.getInstance().getId()).setColor("BLUE");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                unselectColor();
                blue.setForeground(getDrawable(R.drawable.current_player_border));
            }
            new Thread(()->
                    GameClient.getInstance().sendMessage(new ColorMessage(GameClient.getInstance().getId(),"BLUE"))
            ).start();

        });

        green=findViewById(R.id.view_green);
        green.setOnClickListener(view -> {
            Game.getInstance().getPlayerById(GameClient.getInstance().getId()).setColor("GREEN");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                unselectColor();
                green.setForeground(getDrawable(R.drawable.current_player_border));
            }
            new Thread(()->
                    GameClient.getInstance().sendMessage(new ColorMessage(GameClient.getInstance().getId(),"GREEN"))
            ).start();
        });

        GameClient.getInstance().registerActivity(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void unselectColor(){
        red.setForeground(null);
        green.setForeground(null);
        orange.setForeground(null);
        blue.setForeground(null);
    }

    @SuppressLint("ResourceAsColor")
    public void disableColor(){
        HashMap<Integer, String> playersColors = PlayerColors.getInstance().getPlayersColorMap();

        enableColors();

        for (int i = 0; i < Game.getInstance().getPlayers().size(); i++) {
            if (playersColors.get(i) != null && GameClient.getInstance().getId() != i) {
                switch (playersColors.get(i)) {
                    case "GREEN":
                        green.setBackgroundColor(R.color.material_on_background_disabled);
                        green.setClickable(false);
                        break;
                    case "RED":
                        red.setBackgroundColor(R.color.material_on_background_disabled);
                        red.setClickable(false);
                        break;
                    case "ORANGE":
                        orange.setBackgroundColor(R.color.material_on_background_disabled);
                        orange.setClickable(false);
                        break;
                    case "BLUE":
                        blue.setBackgroundColor(R.color.material_on_background_disabled);
                        blue.setClickable(false);
                        break;
                }
            }
        }

    }
    
    private void enableColors(){
        green.setBackgroundColor(Color.parseColor("#05A505"));
        green.setClickable(true);

        red.setBackgroundColor(Color.parseColor("#F44336"));
        red.setClickable(true);

        orange.setBackgroundColor(Color.parseColor("#FF9800"));
        orange.setClickable(true);

        blue.setBackgroundColor(Color.parseColor("#2196F3"));
        blue.setClickable(true);
    }



    public void startGame(){
        if (PlayerColors.getInstance().checkIfAllPlayersChose()) {
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
        }
    }
}
package com.example.settlersofcatan.ui.color;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.settlersofcatan.R;
import com.example.settlersofcatan.game.Game;
import com.example.settlersofcatan.game.PlayerColor;
import com.example.settlersofcatan.server_client.GameClient;
import com.example.settlersofcatan.server_client.networking.dto.ColorMessage;
import com.example.settlersofcatan.ui.game.GameActivity;

import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;


public class ChooseColorActivity extends AppCompatActivity {

    private FrameLayout red;
    private FrameLayout orange;
    private FrameLayout blue;
    private FrameLayout green;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_color);

        red=findViewById(R.id.view_red);
        initColorListener(red, PlayerColor.RED);

        orange=findViewById(R.id.view_orange);
        initColorListener(orange, PlayerColor.ORANGE);

        blue=findViewById(R.id.view_blue);
        initColorListener(blue, PlayerColor.BLUE);

        green=findViewById(R.id.view_green);
        initColorListener(green, PlayerColor.GREEN);

        GameClient.getInstance().registerActivity(this);
    }

    private void initColorListener(FrameLayout colorView, PlayerColor playerColor){
        colorView.setOnClickListener(view -> {
            Game.getInstance().getPlayerById(GameClient.getInstance().getId()).setColor(playerColor);
            unselectColor();
            colorView.setForeground(AppCompatResources.getDrawable(this, R.drawable.current_player_border));
            new Thread(()->
                    GameClient.getInstance().sendMessage(new ColorMessage(GameClient.getInstance().getId(), playerColor))
            ).start();
        });
    }

    private void unselectColor(){
        red.setForeground(null);
        green.setForeground(null);
        orange.setForeground(null);
        blue.setForeground(null);
    }

    public void disableColor(){
        Map<Integer, PlayerColor> playersColors = PlayerColors.getInstance().getPlayersColorMap();

        enableColors();

        for (int i = 0; i < Game.getInstance().getPlayers().size(); i++) {
            PlayerColor playerColor = playersColors.get(i);
            if (playerColor != null && GameClient.getInstance().getId() != i) {
                switch (playerColor) {
                    case GREEN:
                        green.setBackgroundColor(getResources().getColor(R.color.material_on_background_disabled));
                        green.setClickable(false);
                        break;
                    case RED:
                        red.setBackgroundColor(getResources().getColor(R.color.material_on_background_disabled));
                        red.setClickable(false);
                        break;
                    case ORANGE:
                        orange.setBackgroundColor(getResources().getColor(R.color.material_on_background_disabled));
                        orange.setClickable(false);
                        break;
                    case BLUE:
                        blue.setBackgroundColor(getResources().getColor(R.color.material_on_background_disabled));
                        blue.setClickable(false);
                        break;
                    default:
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
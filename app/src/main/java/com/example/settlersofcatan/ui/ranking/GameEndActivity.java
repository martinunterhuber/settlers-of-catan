package com.example.settlersofcatan.ui.ranking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.settlersofcatan.R;
import com.example.settlersofcatan.game.Game;
import com.example.settlersofcatan.server_client.GameClient;

public class GameEndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_end);

        TextView win = findViewById(R.id.txt_win);
        TextView first = findViewById(R.id.txt_first);
        TextView second = findViewById(R.id.txt_second);
        TextView third = findViewById(R.id.txt_third);
        TextView fourth = findViewById(R.id.txt_fourth);
        Button endGameButton = findViewById(R.id.btn_end);

        endGameButton.setOnClickListener(v -> finish());

        // Text if you've won or lost
        if (GameClient.getInstance().getId() == Ranking.getInstance().getPlayer(0).getId()){
            win.setText(R.string.win);
        }else {
            win.setText(R.string.lose);
        }

        //----- Ranking -------------------------
        first.setText(String.format(getResources().getString(R.string.first),
                Ranking.getInstance().getPlayer(0).getName()));

        if (Game.getInstance().getPlayers().size()>2){
            second.setText(String.format(getResources().getString(R.string.second),
                    Ranking.getInstance().getPlayer(1).getName()));

        }else{
            second.setVisibility(View.GONE);
        }


        if (Game.getInstance().getPlayers().size()>2){
            third.setText(String.format(getResources().getString(R.string.third),
                    Ranking.getInstance().getPlayer(2).getName()));
        }else{
            third.setVisibility(View.GONE);
        }

        if (Game.getInstance().getPlayers().size()>3){
            fourth.setText(String.format(getResources().getString(R.string.fourth),
                    Ranking.getInstance().getPlayer(3).getName()));
        }else{
            fourth.setVisibility(View.GONE);
        }

        Button resButton = findViewById(R.id.resranbtn);
        resButton.setOnClickListener(v -> startActivity(new Intent(GameEndActivity.this, ResourceRankingActivity.class)));
    }
}
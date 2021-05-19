package com.example.settlersofcatan;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.settlersofcatan.game.Game;
import com.example.settlersofcatan.game.Player;
import com.example.settlersofcatan.server_client.GameClient;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class OpponentView extends FrameLayout {
    private TextView textName;
    private TextView textPointCount;
    private ConstraintLayout opponentLayout;
    private TextView dice;
    private int rolled;

    private Player opponent;

    public OpponentView(@NonNull Context context) {
        super(context);
        initView();
    }

    public OpponentView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public OpponentView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        updatePoints();
    }

    private void initView() {
        inflate(getContext(), R.layout.opponent_view, this);
        setWillNotDraw(false);
        setOpponent();
        textName = findViewById(R.id.txt_name);
        textPointCount = findViewById(R.id.txt_countpoints);
        opponentLayout = findViewById(R.id.layout_opponent);
        dice = findViewById(R.id.txt_dice);
        rolled = 0;
        initPlayer();
    }

    private void initPlayer(){
        if (opponent != null){
            textName.setText(opponent.getName());
            textPointCount.setText(String.valueOf(opponent.getVictoryPoints()));
            opponentLayout.setBackgroundColor(GameActivity.playerColors[opponent.getId()]);
            dice.setText(String.valueOf(rolled));
        } else {
            textName.setText("--------");
            textPointCount.setText("0");
        }
    }

    private void updatePoints(){
        if (opponent != null){
            textPointCount.setText(String.valueOf(opponent.getVictoryPoints()));
        }
    }

    private void setOpponent(){
        List<Player> players = Game.getInstance().getPlayers();
        int playerId = GameClient.getInstance().getId();
        int opponentNumber = Integer.parseInt(this.getTag().toString());
        int count = 1;
        for (Player player : players){
            if (player.getId() == playerId){
                continue;
            } else if (opponentNumber == count) {
                opponent = player;
                break;
            } else {
                count++;
            }
        }
    }

    public String getPlayerName(){
        if (opponent != null){
        return opponent.getName();
        }

        return "---";
    }

    public void updateDice(int rolled){
        this.rolled = (rolled);
    }
}

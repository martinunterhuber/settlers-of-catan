package com.example.settlersofcatan.ui.game;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.settlersofcatan.game.PlayerColor;
import com.example.settlersofcatan.ui.color.PlayerColors;
import com.example.settlersofcatan.R;
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
    private ImageButton exposeCheater;

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
        update();
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
        exposeCheater = findViewById(R.id.report_cheater_button);
        initPlayer();
    }

    private void initPlayer(){
        if (opponent != null){
            textName.setText(opponent.getName());
            textPointCount.setText(String.valueOf(opponent.getVictoryPoints()));
            setBackgroundColor();
            dice.setText(String.valueOf(rolled));

            exposeCheater.setOnClickListener(view -> {
                Game game = Game.getInstance();

                if (game.hasCheated(opponent.getId())){
                    game.exposeCheater(opponent.getId());
                    Toast.makeText(getContext(), getContext().getString(R.string.has_cheated, opponent.getName()), Toast.LENGTH_SHORT).show();
                }else {
                    game.penaltyForFalseCharge(opponent.getId());
                    Toast.makeText(getContext(), getContext().getString(R.string.has_not_cheated, opponent.getName()), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            textName.setText("--------");
            textPointCount.setText("0");
        }
    }

    private void update(){
        if (opponent != null){
            textPointCount.setText(String.valueOf(opponent.getVictoryPoints()));
            dice.setText(String.valueOf(rolled));
        }
    }

    private void setOpponent(){
        List<Player> players = Game.getInstance().getPlayers();
        int playerId = GameClient.getInstance().getId();
        int opponentNumber = Integer.parseInt(this.getTag().toString());
        int count = 1;
        for (Player player : players){
            if (player.getId() != playerId) {
                if (opponentNumber == count) {
                    opponent = player;
                    break;
                } else {
                    count++;
                }
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

    public void setBackgroundColor(){
        PlayerColor playerColor = PlayerColors.getInstance().getSinglePlayerColor(opponent.getId());
        opponentLayout.setBackgroundColor(GameActivity.playerColors[playerColor.getId()]);
    }
}

package com.example.settlersofcatan;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.app.AlertDialog;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.settlersofcatan.game.Game;
import com.example.settlersofcatan.game.Node;
import com.example.settlersofcatan.game.Player;
import com.example.settlersofcatan.game.Resource;
import com.example.settlersofcatan.game.Tile;
import com.example.settlersofcatan.server_client.GameClient;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    private MapView map;
    private PlayerView playerView;
    private ResourceView resources;
    private OpponentView opponent1;
    private OpponentView opponent2;
    private OpponentView opponent3;
    private Button endTurnButton;
    private Button moveRobberButton;
    private ImageView dice;

    private GameClient client;

    static final int[] playerColors = new int[]{
            Color.parseColor("#05A505"),
            Color.parseColor("#FF9800"),
            Color.parseColor("#F44336"),
            Color.parseColor("#2196F3"), };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        client = GameClient.getInstance();

        map=findViewById(R.id.mapView);
        playerView =findViewById(R.id.playerView);
        playerView.setHexGrid(map.getHexGrid());
        playerView.invalidate();
        resources=findViewById(R.id.resourceView);

        opponent1=findViewById(R.id.opponent1);

        opponent2=findViewById(R.id.opponent2);

        opponent3=findViewById(R.id.opponent3);

        endTurnButton = findViewById(R.id.endTurnButton);
        endTurnButton.setOnClickListener((v) -> Game.getInstance().endTurn(client.getId()));
        endTurnButton.setEnabled(Game.getInstance().getCurrentPlayerId() == client.getId());

        moveRobberButton = findViewById(R.id.moveRobber);
        moveRobberButton.setOnClickListener(this::moveRobber);

        dice = findViewById(R.id.btn_dice);
        dice.setOnClickListener(this::rollDice);

        client.registerActivity(this);

        setButtonToPlayerColor();

        ((TextView) findViewById(R.id.victory_points)).setText(String.valueOf(Game.getInstance().getPlayerById(client.getId()).getVictoryPoints()));
    }

    private void moveRobber(View view){
        selectPlayerAndResource(playerView.getSelectedTile());
    }

    private void moveRobber(Resource resource, int otherPlayerId){
        Game.getInstance().moveRobber(playerView.getSelectedTile(), resource, client.getId(), otherPlayerId);
        playerView.invalidate();
        findViewById(R.id.moveRobber).setEnabled(Game.getInstance().canMoveRobber());
    }

    private void rollDice(View view){
        int result = Game.getInstance().rollDice(client.getId());
        if (result > 0){
            ((TextView) findViewById(R.id.rollResult)).setText(String.valueOf(result));
            resources.invalidate();
        }
        if (result == 7){
            findViewById(R.id.moveRobber).setEnabled(true);
        }
    }

    private void selectPlayerAndResource(Tile tile){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_robber, null);
        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(false);

        List<String> spinnerArray = new ArrayList<>();
        for (Node node : tile.getNodes()){
            if (node.getBuilding() != null){
                Player player = node.getBuilding().getPlayer();
                if (player.getId() != client.getId() && !spinnerArray.contains(player.getName())){
                    spinnerArray.add(player.getName());
                }
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = dialogView.findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        Button confirm = dialogView.findViewById(R.id.confirm);
        confirm.setOnClickListener((view) -> {
            String playerName = spinner.getSelectedItem().toString();
            Player player = Game.getInstance().getPlayerByName(playerName);
            Resource resource = Resource.SHEEP;
            moveRobber(resource, player.getId());
            alertDialog.dismiss();
        });

        alertDialog.show();
    }

    private void setButtonToPlayerColor(){
        Player player = Game.getInstance().getPlayerById(client.getId());
        ((ImageView)findViewById(R.id.btn_city)).setImageResource(PlayerView.CITY_IDS[player.getId()]);
        ((ImageView)findViewById(R.id.btn_road)).setImageResource(PlayerView.ROAD_IDS[player.getId()]);
        ((ImageView)findViewById(R.id.btn_settlement)).setImageResource(PlayerView.SETTLEMENT_IDS[player.getId()]);
    }

    public void redrawViews(){
        findViewById(R.id.opponents).invalidate();
        findViewById(R.id.playerView).invalidate();
        findViewById(R.id.resourceView).invalidate();
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_road:
                playerView.buildRoad();
                break;
            case R.id.btn_settlement:
                playerView.buildSettlement();
                break;
            case R.id.btn_city:
                playerView.buildCity();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.quit_lobby_title)
                .setMessage(R.string.quit_lobby_confirmation)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    if (client != null){
                        new Thread(client::disconnect).start();
                    }
                    finish();
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

}
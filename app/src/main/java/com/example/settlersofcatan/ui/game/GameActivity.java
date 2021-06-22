package com.example.settlersofcatan.ui.game;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.settlersofcatan.R;
import com.example.settlersofcatan.game.Game;
import com.example.settlersofcatan.game.Player;
import com.example.settlersofcatan.game.board.Tile;
import com.example.settlersofcatan.game.resources.Resource;
import com.example.settlersofcatan.game.trade.TradeOffer;
import com.example.settlersofcatan.server_client.GameClient;
import com.example.settlersofcatan.ui.resources.ResourceView;
import com.example.settlersofcatan.ui.resources.SelectableResourceView;
import com.example.settlersofcatan.ui.trade.ReceiveTradeOfferActivity;
import com.example.settlersofcatan.ui.trade.TradeActivity;
import com.example.settlersofcatan.util.OnPostDrawListener;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity implements OnPostDrawListener {

    private MapView map;
    private PlayerView playerView;
    private ResourceView resources;
    private OpponentView opponent1;
    private OpponentView opponent2;
    private OpponentView opponent3;
    private Button endTurnButton;
    private Button moveRobberButton;
    private ImageView dice;

    private DevelopmentCardView knights;
    private DevelopmentCardView victoryPoints;
    private DevelopmentCardView monopoly;
    private DevelopmentCardView roadBuilding;
    private DevelopmentCardView yearOfPlenty;
    private Button drawDevelopmentCard;
    private ImageButton buildingCosts;

    private Button btnTrade;

    private GameClient client;

    private SensorManager sensorManager;
    private Sensor sensor;
    private int currentSensorValue = 0;
    private int previousSensorValue = 0;
    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            currentSensorValue = (int) Math.sqrt((x*x + y*y + z*z));
            if (previousSensorValue != currentSensorValue
                    && currentSensorValue > 17
                    && !Game.getInstance().isBuildingPhase()) {
                previousSensorValue = currentSensorValue;
                sensorManager.unregisterListener(sensorEventListener);
                selectPlayerAndResource();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
            /**
             *  method not needed
              */
        }

        private void selectPlayerAndResource(){
            Player currentPlayer = Game.getInstance().getPlayerById(client.getId());
            List<String> spinnerArray = new ArrayList<>();
            for (Player player : Game.getInstance().getPlayers()){
                if (player.getId() != currentPlayer.getId()){
                    spinnerArray.add(player.getName());
                }
            }

            showAlertDialog(spinnerArray, "CHEAT");
        }
    };

    static final int[] playerColors = new int[]{
            Color.parseColor("#05A505"),
            Color.parseColor("#F44336"),
            Color.parseColor("#FF9800"),
            Color.parseColor("#2196F3"), };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);



        client = GameClient.getInstance();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        map=findViewById(R.id.mapView);
        playerView =findViewById(R.id.playerView);
        playerView.setHexGrid(map.getHexGrid());
        playerView.invalidate();
        resources=findViewById(R.id.resourceView);

        opponent1=findViewById(R.id.opponent1);
        opponent2=findViewById(R.id.opponent2);
        opponent3=findViewById(R.id.opponent3);

        knights=findViewById(R.id.view_knights);
        victoryPoints=findViewById(R.id.view_victory_point_cards);
        monopoly=findViewById(R.id.view_monopoly);
        roadBuilding=findViewById(R.id.view_road_building);
        yearOfPlenty=findViewById(R.id.view_year_of_plenty);

        endTurnButton = findViewById(R.id.endTurnButton);

        moveRobberButton = findViewById(R.id.moveRobber);
        moveRobberButton.setOnClickListener(this::moveRobber);

        dice = findViewById(R.id.btn_dice);
        dice.setOnClickListener(this::rollDice);

        drawDevelopmentCard=findViewById(R.id.btn_draw_development);

        btnTrade = findViewById(R.id.btn_trade);
        btnTrade.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), TradeActivity.class);
            startActivity(i);
        });

        buildingCosts=findViewById(R.id.imgBtn_costs);
        buildingCosts.setOnClickListener(this::showCostDialog);

        client.registerActivity(this);

        initializeButtons();
    }

    private void initializeButtons(){
        Game game = Game.getInstance();

        endTurnButton.setOnClickListener((v) -> game.endTurn(client.getId()));

        drawDevelopmentCard.setOnClickListener(
                view -> {
                    int type = game.drawDevelopmentCard(GameClient.getInstance().getId());
                    GameClient.getInstance().getGameActivity().findViewById(R.id.resourceView).invalidate();

                    if (type == 0){
                        knights.updateView(type);
                    }else if (type == 1){
                        victoryPoints.updateView(type);
                    }else if (type == 2){
                        monopoly.updateView(type);
                    }else if (type == 3){
                        roadBuilding.updateView(type);
                    }else if (type == 4){
                        yearOfPlenty.updateView(type);
                    }
                }
        );

        setButtonToPlayerColor();
        runOnUiThread(() -> {
            drawDevelopmentCard.setEnabled(game.getCurrentPlayerId() == client.getId() && !game.isBuildingPhase());
            endTurnButton.setEnabled(game.getCurrentPlayerId() == client.getId() && game.isBuildingPhase());
            btnTrade.setEnabled(game.getCurrentPlayerId() == client.getId() && !game.isBuildingPhase());
            showCurrentPlayer();
            ((TextView) findViewById(R.id.victory_points)).setText(String.valueOf(game.getPlayerById(client.getId()).getVictoryPoints()
                    + game.getPlayerById(client.getId()).getHiddenVictoryPoints()));
        });
    }

    public void redrawViewsNewGameState(){
        map.invalidate();
        playerView.setHexGrid(map.getHexGrid());
        redrawViewsTurnEnd();
    }

    public void redrawViewsTurnEnd(){
        initializeButtons();
        redrawViews();
        if (Game.getInstance().getCurrentPlayerId() == client.getId()){
            runOnUiThread(() -> {
                Toast.makeText(this, R.string.turn_message, Toast.LENGTH_LONG).show();
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        resources.invalidate();
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void moveRobber(View view){
        if (playerView.getSelectedTile() != null) {
            selectPlayerAndResource(playerView.getSelectedTile());
        }
    }

    private void moveRobber(Resource resource, int otherPlayerId){
        Game.getInstance().moveRobber(playerView.getSelectedTile(), resource, client.getId(), otherPlayerId);
        playerView.invalidate();
        findViewById(R.id.moveRobber).setEnabled(Game.getInstance().canMoveRobber());
        resources.invalidate();
    }

    private void rollDice(View view){
        int result = Game.getInstance().rollDice(client.getId());
        if (result > 0){
            ((TextView) findViewById(R.id.rollResult)).setText(String.valueOf(result));
            endTurnButton.setEnabled(true);
            resources.invalidate();
        }
        if (result == 7){
            findViewById(R.id.moveRobber).setEnabled(true);
        }
    }

    private void selectPlayerAndResource(Tile tile){
        Player currentPlayer = Game.getInstance().getPlayerById(client.getId());
        List<String> spinnerArray = tile.getAdjacentPlayersNamesExcept(currentPlayer);
        if (spinnerArray.isEmpty()){
            moveRobber(null, -1);
            return;
        }

        showAlertDialog(spinnerArray, "ROBBERS");
    }

    public void showAlertDialog(List<String> spinnerArray, String tag){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_robber, null);
        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(false);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = dialogView.findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        Button confirm = dialogView.findViewById(R.id.confirm);
        Button cancel = dialogView.findViewById(R.id.cancel);
        if(tag.equals("ROBBERS")){
            cancel.setVisibility(View.INVISIBLE);
        }
        SelectableResourceView resourceView = dialogView.findViewById(R.id.robberResourceView);

        confirm.setOnClickListener((view) -> {
            if (tag.equals("CHEAT")) {
                String playerName = spinner.getSelectedItem().toString();
                Player victim = Game.getInstance().getPlayerByName(playerName);
                Resource resource = resourceView.getSelectedResource();
                Game.getInstance().robResource(victim.getId(), GameClient.getInstance().getId(), resource);
                alertDialog.dismiss();
                sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
            }else if (tag.equals("ROBBERS")){
                String playerName = spinner.getSelectedItem().toString();
                Player player = Game.getInstance().getPlayerByName(playerName);
                Resource resource = resourceView.getSelectedResource();
                moveRobber(resource, player.getId());
                alertDialog.dismiss();
            }
        });
        cancel.setOnClickListener(view -> {
            alertDialog.dismiss();
            sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        });

        AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Player player = Game.getInstance().getPlayerByName(parent.getItemAtPosition(position).toString());
                resourceView.setResourceValuesOf(player);
                resourceView.initListeners(() -> confirm.setEnabled(true));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                confirm.setEnabled(false);
                resourceView.setEmptyResources();
            }
        };
        spinner.setOnItemSelectedListener(onItemSelectedListener);

        alertDialog.show();
    }

    public void showCostDialog(View view){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_building_resources_costs, null);
        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(false);

        Button dismiss = dialogView.findViewById(R.id.btn_close_costs);
        dismiss.setOnClickListener(view1 -> alertDialog.dismiss());

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
        playerView.invalidate();
        redrawResourceView();
    }

    public void redrawResourceView(){
        resources.invalidate();
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

    private void showCurrentPlayer(){
        Game game = Game.getInstance();
        int currentPlayerId = game.getCurrentPlayerId();
        String currentPlayerName = game.getPlayerById(currentPlayerId).getName();

        unselectPlayer();

        if (opponent1.getPlayerName().equals(currentPlayerName)){
            FrameLayout border = opponent1.findViewById(R.id.layout_selected_border);
            border.setVisibility(View.VISIBLE);
        }else if (opponent2.getPlayerName().equals(currentPlayerName)){
            FrameLayout border = opponent2.findViewById(R.id.layout_selected_border);
            border.setVisibility(View.VISIBLE);
        }else if (opponent3.getPlayerName().equals(currentPlayerName)){
            FrameLayout border = opponent3.findViewById(R.id.layout_selected_border);
            border.setVisibility(View.VISIBLE);
        }
    }

    private void unselectPlayer(){
        FrameLayout border = opponent1.findViewById(R.id.layout_selected_border);
        border.setVisibility(View.INVISIBLE);

        border = opponent2.findViewById(R.id.layout_selected_border);
        border.setVisibility(View.INVISIBLE);

        border = opponent3.findViewById(R.id.layout_selected_border);
        border.setVisibility(View.INVISIBLE);
    }

    public void updateOpponentView(String username, int rolled){

        if (opponent1.getPlayerName().equals(username)){
            opponent1.updateDice(rolled);
            opponent1.invalidate();
        }else if (Game.getInstance().getPlayers().size() > 2 && opponent2.getPlayerName().equals(username)){
            opponent2.updateDice(rolled);
            opponent2.invalidate();
        }else if (Game.getInstance().getPlayers().size() > 3 && opponent3.getPlayerName().equals(username)){
            opponent3.updateDice(rolled);
            opponent3.invalidate();
        }

    }

    public void displayTradeOffer(TradeOffer tradeOffer) {
        Intent i = new Intent(getApplicationContext(), ReceiveTradeOfferActivity.class);
        i.putExtra("tradeoffer", (Parcelable) tradeOffer);
        startActivity(i);
    }

    @Override
    public void onPostDraw() {
        playerView.setHexGrid(map.getHexGrid());
        playerView.invalidate();
    }
}
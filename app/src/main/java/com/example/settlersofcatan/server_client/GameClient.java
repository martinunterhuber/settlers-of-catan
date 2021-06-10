package com.example.settlersofcatan.server_client;

import android.content.Intent;
import android.util.Log;

import com.example.settlersofcatan.GameActivity;
import com.example.settlersofcatan.GameEndActivity;
import com.example.settlersofcatan.Ranking;
import androidx.appcompat.app.AppCompatActivity;

import com.example.settlersofcatan.GameActivity;
import com.example.settlersofcatan.R;
import com.example.settlersofcatan.game.Board;
import com.example.settlersofcatan.game.City;
import com.example.settlersofcatan.game.DevelopmentCard;
import com.example.settlersofcatan.game.DevelopmentCardDeck;
import com.example.settlersofcatan.game.Edge;
import com.example.settlersofcatan.game.Game;
import com.example.settlersofcatan.game.Harbor;
import com.example.settlersofcatan.game.Knights;
import com.example.settlersofcatan.game.Monopoly;
import com.example.settlersofcatan.game.Node;
import com.example.settlersofcatan.game.Player;
import com.example.settlersofcatan.game.Resource;
import com.example.settlersofcatan.game.ResourceMap;
import com.example.settlersofcatan.game.Road;
import com.example.settlersofcatan.game.RoadBuilding;
import com.example.settlersofcatan.game.Robber;
import com.example.settlersofcatan.game.Settlement;
import com.example.settlersofcatan.game.Tile;
import com.example.settlersofcatan.game.TradeOffer;
import com.example.settlersofcatan.game.VictoryPoints;
import com.example.settlersofcatan.game.YearOfPlenty;
import com.example.settlersofcatan.server_client.networking.Callback;
import com.example.settlersofcatan.server_client.networking.dto.BaseMessage;
import com.example.settlersofcatan.server_client.networking.dto.ClientDiceMessage;
import com.example.settlersofcatan.server_client.networking.dto.ClientJoinedMessage;
import com.example.settlersofcatan.server_client.networking.dto.ClientLeftMessage;
import com.example.settlersofcatan.server_client.networking.dto.ClientWinMessage;
import com.example.settlersofcatan.server_client.networking.dto.DevelopmentCardMessage;
import com.example.settlersofcatan.server_client.networking.dto.GameStateMessage;
import com.example.settlersofcatan.server_client.networking.dto.TextMessage;
import com.example.settlersofcatan.server_client.networking.dto.TradeOfferMessage;
import com.example.settlersofcatan.server_client.networking.dto.TradeReplyMessage;
import com.example.settlersofcatan.server_client.networking.kryonet.NetworkClientKryo;
import com.example.settlersofcatan.server_client.networking.kryonet.NetworkConstants;
import com.example.settlersofcatan.ui.trade.WaitForReplyActivity;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import androidx.appcompat.app.AppCompatActivity;

public class GameClient {
    private static GameClient instance;
    private NetworkClientKryo client;
    private String username = "";
    private int id;
    private Callback<BaseMessage> startGameCallback;
    private GameActivity gameActivity;
    private WaitForReplyActivity waitForReplyActivity;

    private GameClient(){

    }

    public static GameClient getInstance(){
        if (instance == null){
            instance = new GameClient();
        }
        return instance;
    }

    public void connect(String host, String username) {
        this.username = username;
        client = new NetworkClientKryo();
        registerMessageClasses();
        client.registerCallback(this::callback);
        try {
            connect(host);
        } catch (IOException e){
            Log.e(NetworkConstants.TAG, "Failed to connect to " + host);
        }
    }

    public boolean isConnected(){
        return client.isConnected();
    }

    private void registerMessageClasses(){
        client.registerClass(TextMessage.class);
        client.registerClass(ClientJoinedMessage.class);
        client.registerClass(ClientLeftMessage.class);
        client.registerClass(GameStateMessage.class);
        client.registerClass(ClientWinMessage.class);
        client.registerClass(Ranking.class);
        client.registerClass(Game.class);
        client.registerClass(HashSet.class);
        client.registerClass(ArrayList.class);
        client.registerClass(Board.class);
        client.registerClass(Player.class);
        client.registerClass(Tile.class);
        client.registerClass(Tile[].class);
        client.registerClass(Tile[][].class);
        client.registerClass(Edge.class);
        client.registerClass(Edge[].class);
        client.registerClass(Node.class);
        client.registerClass(Node[].class);
        client.registerClass(Settlement.class);
        client.registerClass(City.class);
        client.registerClass(Road.class);
        client.registerClass(Harbor.class);
        client.registerClass(Resource.class);
        client.registerClass(HashMap.class);
        client.registerClass(ResourceMap.class);
        client.registerClass(DevelopmentCardDeck.class);
        client.registerClass(DevelopmentCard.class);
        client.registerClass(DevelopmentCard[].class);
        client.registerClass(Knights.class);
        client.registerClass(VictoryPoints.class);
        client.registerClass(Monopoly.class);
        client.registerClass(RoadBuilding.class);
        client.registerClass(YearOfPlenty.class);
        client.registerClass(int[].class);
        client.registerClass(SecureRandom.class);
        client.registerClass(ClientDiceMessage.class);
        client.registerClass(Robber.class);
        client.registerClass(DevelopmentCardDeck.class);
        client.registerClass(DevelopmentCardMessage.class);
        client.registerClass(TradeOfferMessage.class);
        client.registerClass(TradeOffer.class);
        client.registerClass(TradeReplyMessage.class);
    }

    private void gameCallback(BaseMessage message){
        if (message instanceof GameStateMessage){
            sendMessage(message);
        } else if (message instanceof ClientWinMessage){
            sendMessage(message);
        }

    }

    private void callback(BaseMessage message){
        if (message instanceof GameStateMessage){
            Game game = ((GameStateMessage) message).game;
            game.setClientCallback(this::gameCallback);
            Game.setInstance(game);
            for (Player player : Game.getInstance().getPlayers()){
                if (player.getName().equals(this.username)){
                    id = player.getId();
                }
            }
            if (startGameCallback != null){
                startGameCallback.callback(message);
            }
            if (gameActivity != null) {
                // gameActivity.redrawViews();
            }
        }
        if (message instanceof ClientWinMessage){
            if(gameActivity != null) {
                Intent intent = new Intent(gameActivity, GameEndActivity.class);
                gameActivity.startActivity(intent);
                gameActivity.finish();
            }
        }
        if (message instanceof ClientDiceMessage){
            if(gameActivity != null) {

                gameActivity.runOnUiThread(() -> gameActivity.updateOpponentView(
                        ((ClientDiceMessage) message).getUsername(),
                        ((ClientDiceMessage) message).getRolled())

                );

            }
        } else if (message instanceof DevelopmentCardMessage){
            DevelopmentCardDeck.setInstance(((DevelopmentCardMessage) message).deck);
        } else if (message instanceof TradeOfferMessage) {
            TradeOffer tradeOffer = ((TradeOfferMessage) message).tradeOffer;
            Log.i("GameClient", "REACHED HERE");
            if (gameActivity != null && tradeOffer.getTo().getId() == id) {
                gameActivity.runOnUiThread(() -> gameActivity.displayTradeOffer(tradeOffer));
            }
        } else if (message instanceof TradeReplyMessage) {
            if (waitForReplyActivity != null) {
                waitForReplyActivity.getReply(((TradeReplyMessage) message).acceptedTrade);
            }
        }
        Log.i(NetworkConstants.TAG, message.toString());
    }

    public void registerActivity(GameActivity activity){
        gameActivity = activity;
    }

    public void registerStartGameCallback(Callback<BaseMessage> c){
        this.startGameCallback = c;
    }


    private void connect(String host) throws IOException {
        client.connect(host);
        Log.i(NetworkConstants.TAG, "Connected to Host");
        client.sendMessage(new ClientJoinedMessage(username));
    }

    public void disconnect(){
        client.sendMessage(new ClientLeftMessage(username));
        client.disconnect();
        Log.i(NetworkConstants.TAG, "Disconnected from Host");
    }

    public void sendMessage(BaseMessage message){
        client.sendMessage(message);
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public AppCompatActivity getGameActivity(){
        return gameActivity;
    }

    public void registerWaitForReplyActivity(WaitForReplyActivity activity) {
        waitForReplyActivity = activity;
    }
}

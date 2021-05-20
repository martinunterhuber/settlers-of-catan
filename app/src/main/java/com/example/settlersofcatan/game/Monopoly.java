package com.example.settlersofcatan.game;

import android.util.Log;

import com.example.settlersofcatan.R;
import com.example.settlersofcatan.ResourceDialog;
import com.example.settlersofcatan.server_client.GameClient;

public class Monopoly extends DevelopmentCard{

    public Monopoly() {
        super(2);
    }

    @Override
    public void playCard() {
        super.playCard();
        Log.i("DEVELOPMENTCARDS: ", "Monopoly played.");

        ResourceDialog dialog = new ResourceDialog();
        dialog.showDialog(GameClient.getInstance().getGameActivity(),this);
    }

    public void getResourcesFromOpponents(Resource resource){
        int resourceCount = 0;
        for (Player player : Game.getInstance().getPlayers()){
            if (player.getId() != GameClient.getInstance().getId()){
                resourceCount += player.getResourceCount(resource);
                player.takeResource(resource, player.getResourceCount(resource));
                Log.i("DEVELOPMENT_MONOPOLY", "Opponent's resource decreased.");
            }
        }

        while (resourceCount > 0) {
            Game.getInstance().getPlayerById(GameClient.getInstance().getId()).giveSingleResource(resource);
            Log.i("DEVELOPMENT_MONOPOLY","Resource added.");
            resourceCount--;
        }
        GameClient.getInstance().getGameActivity().findViewById(R.id.resourceView).invalidate();

        String obtained = String.valueOf(Game.getInstance().getPlayerById(GameClient.getInstance().getId())
                                                            .getResourceCount(resource));
        Log.i("DEVELOPMENT", "Number of selected resource: " + obtained);
    }
}

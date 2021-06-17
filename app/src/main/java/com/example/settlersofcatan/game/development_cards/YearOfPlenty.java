package com.example.settlersofcatan.game.development_cards;

import android.util.Log;

import com.example.settlersofcatan.R;
import com.example.settlersofcatan.ui.resources.ResourceDialog;
import com.example.settlersofcatan.game.Game;
import com.example.settlersofcatan.game.resources.Resource;
import com.example.settlersofcatan.server_client.GameClient;

public class YearOfPlenty extends DevelopmentCard{

    public YearOfPlenty() {
        super(2);
    }

    @Override
    public void playCard() {
        super.playCard();
        Log.i("DEVELOPMENTCARDS: ", "Year of Plenty played.");

        ResourceDialog dialog = new ResourceDialog();
        dialog.showDialog(GameClient.getInstance().getGameActivity(),this);
        dialog.showDialog(GameClient.getInstance().getGameActivity(),this);
    }

    public void getResources(Resource resource){
        Game.getInstance().getPlayerById(GameClient.getInstance().getId()).giveSingleResource(resource);
        Log.i("DEVELOPMENT_YEAR","Resource added.");
        GameClient.getInstance().getGameActivity().findViewById(R.id.resourceView).invalidate();
    }
}

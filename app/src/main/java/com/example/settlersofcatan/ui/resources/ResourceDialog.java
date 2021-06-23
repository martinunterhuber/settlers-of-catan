package com.example.settlersofcatan.ui.resources;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;
import android.widget.Button;

import com.example.settlersofcatan.R;
import com.example.settlersofcatan.game.development_cards.DevelopmentCard;
import com.example.settlersofcatan.game.development_cards.Monopoly;
import com.example.settlersofcatan.game.development_cards.YearOfPlenty;
import com.example.settlersofcatan.game.resources.Resource;

public class ResourceDialog {

    public void showDialog(Activity activity, DevelopmentCard caller){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_resource);

        Button wood = dialog.findViewById(R.id.btn_wood);
        initializeListener(caller, dialog, wood, Resource.FOREST);

        Button wheat = dialog.findViewById(R.id.btn_wheat);
        initializeListener(caller, dialog, wheat, Resource.WHEAT);

        Button sheep = dialog.findViewById(R.id.btn_sheep);
        initializeListener(caller, dialog, sheep, Resource.SHEEP);

        Button clay = dialog.findViewById(R.id.btn_clay);
        initializeListener(caller, dialog, clay, Resource.CLAY);

        Button ore = dialog.findViewById(R.id.btn_ore);
        initializeListener(caller, dialog, ore, Resource.ORE);

        dialog.show();

    }

    private void initializeListener(DevelopmentCard caller, Dialog dialog, Button button, Resource resource) {
        button.setOnClickListener(view -> {
            if (caller instanceof Monopoly) {
                new Thread(() -> (
                        (Monopoly) caller).getResourcesFromOpponents(resource)
                ).start();
            }

            if (caller instanceof YearOfPlenty) {
                new Thread(() -> (
                        (YearOfPlenty) caller).getResources(resource)
                ).start();
            }

            dialog.dismiss();
        });
    }
}

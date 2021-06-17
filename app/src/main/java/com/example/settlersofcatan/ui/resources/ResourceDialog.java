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
        wood.setOnClickListener(view -> {
            if (caller instanceof Monopoly){
                new Thread(() -> (
                        (Monopoly) caller).getResourcesFromOpponents(Resource.FOREST)
                ).start();
            }

            if (caller instanceof YearOfPlenty){
                new Thread(() -> (
                        (YearOfPlenty) caller).getResources(Resource.FOREST)
                ).start();
            }

            dialog.dismiss();
        });

        Button wheat = dialog.findViewById(R.id.btn_wheat);
        wheat.setOnClickListener(view -> {
            if (caller instanceof Monopoly){
                new Thread(() -> (
                        (Monopoly) caller).getResourcesFromOpponents(Resource.WHEAT)
                ).start();
            }

            if (caller instanceof YearOfPlenty){
                new Thread(() -> (
                        (YearOfPlenty) caller).getResources(Resource.WHEAT)
                ).start();
            }
            dialog.dismiss();
        });

        Button sheep = dialog.findViewById(R.id.btn_sheep);
        sheep.setOnClickListener(view -> {
            if (caller instanceof Monopoly){
                new Thread(() -> (
                        (Monopoly) caller).getResourcesFromOpponents(Resource.SHEEP)
                ).start();
            }

            if (caller instanceof YearOfPlenty){
                new Thread(() -> (
                        (YearOfPlenty) caller).getResources(Resource.SHEEP)
                ).start();
            }
            dialog.dismiss();
        });

        Button clay = dialog.findViewById(R.id.btn_clay);
        clay.setOnClickListener(view -> {
            if (caller instanceof Monopoly){
                new Thread(() -> (
                        (Monopoly) caller).getResourcesFromOpponents(Resource.CLAY)
                ).start();
            }

            if (caller instanceof YearOfPlenty){
                new Thread(() -> (
                        (YearOfPlenty) caller).getResources(Resource.CLAY)
                ).start();
            }

            dialog.dismiss();
        });

        Button ore = dialog.findViewById(R.id.btn_ore);
        ore.setOnClickListener(view -> {
            if (caller instanceof Monopoly){
                new Thread(() -> (
                        (Monopoly) caller).getResourcesFromOpponents(Resource.ORE)
                ).start();
            }

            if (caller instanceof YearOfPlenty){
                new Thread(() -> (
                        (YearOfPlenty) caller).getResources(Resource.ORE)
                ).start();
            }

            dialog.dismiss();
        });

        dialog.show();

    }
}

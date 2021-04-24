package com.example.settlersofcatan.server_client;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.settlersofcatan.R;

public class WaitForHostActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setContentView(R.layout.activity_wait_for_host);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.quit_lobby_title)
                .setMessage(R.string.quit_lobby_confirmation)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    GameClient client = Game.getInstance().getClient();
                    if (client != null){
                        new Thread(client::disconnect).start();
                        Game.getInstance().setClient(null);
                    }
                    finish();
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    // TODO: when host sends message go to the game activity
}

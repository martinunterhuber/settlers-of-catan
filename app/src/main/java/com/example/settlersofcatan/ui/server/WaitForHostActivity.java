package com.example.settlersofcatan.ui.server;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.settlersofcatan.server_client.GameClient;
import com.example.settlersofcatan.ui.game.GameActivity;
import com.example.settlersofcatan.R;

public class WaitForHostActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setContentView(R.layout.activity_wait_for_host);
        GameClient.getInstance().registerStartGameCallback(
                (message) -> {
                    Intent intent = new Intent(this, GameActivity.class);
                    startActivity(intent);
                    finish();
                }
        );
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.quit_lobby_title)
                .setMessage(R.string.quit_lobby_confirmation)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    GameClient client = GameClient.getInstance();
                    if (client != null){
                        new Thread(client::disconnect).start();
                    }
                    finish();
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }
}

package com.example.settlersofcatan.ui.server;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.settlersofcatan.ui.color.ChooseColorActivity;
import com.example.settlersofcatan.R;
import com.example.settlersofcatan.server_client.GameClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WaitForHostActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setContentView(R.layout.activity_wait_for_host);
        GameClient.getInstance().registerStartGameCallback(
                message -> {
                    Intent intent = new Intent(this, ChooseColorActivity.class);
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

package com.example.settlersofcatan.server_client;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.settlersofcatan.R;
import com.example.settlersofcatan.GameActivity;
import com.example.settlersofcatan.game.Game;
import com.example.settlersofcatan.server_client.networking.dto.GameStateMessage;
import com.example.settlersofcatan.server_client.networking.kryonet.NetworkConstants;
import com.example.settlersofcatan.server_client.networking.kryonet.NetworkServerKryo;

import java.io.IOException;

public class CreateServerFragment extends Fragment {
    EditText[] users = new EditText[4];
    GameServer server;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_server, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        users[0] = getView().findViewById(R.id.editTextPlayer);
        users[1] = getView().findViewById(R.id.editTextPlayer1);
        users[2] = getView().findViewById(R.id.editTextPlayer2);
        users[3] = getView().findViewById(R.id.editTextPlayer3);
        new Thread(() -> {
            server = GameServer.getInstance();
            server.init();
            server.registerUserChangedCallback(this::updateUsers);
        }).start();
        String ip = NetworkServerKryo.getLocalIPAddress();
        ((TextView)getView().findViewById(R.id.serverIP)).setText(ip);
        getView().findViewById(R.id.createServerButton2).setOnClickListener(this::startGame);
    }

    private void startGame(View view){
        String username = ((EditText)getView().findViewById(R.id.editTextPlayer)).getText().toString();
        new Thread(
                () -> {
                    try {
                        GameClient client = GameClient.getInstance();
                        client.init("localhost", username);
                        server.broadcastMessage(new GameStateMessage(new Game()));
                    } catch (IOException e) {
                        Log.e(NetworkConstants.TAG, e.getMessage(), e);
                    }
                }
        ).start();
        Intent intent = new Intent(getActivity(), GameActivity.class);
        startActivity(intent);
    }

    private void updateUsers(String username){
        for (int i = 1; i < users.length; i++) {
            users[i].setText(server.getClientUsernameAt(i));
        }
    }
}

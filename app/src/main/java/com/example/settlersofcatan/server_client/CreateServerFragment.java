package com.example.settlersofcatan.server_client;

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
import com.example.settlersofcatan.server_client.networking.kryonet.NetworkConstants;
import com.example.settlersofcatan.server_client.networking.kryonet.NetworkServerKryo;

import java.io.IOException;

public class CreateServerFragment extends Fragment {
    EditText[] users = new EditText[3];
    GameServer server;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_server, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        users[0] = getView().findViewById(R.id.editTextPlayer1);
        users[1] = getView().findViewById(R.id.editTextPlayer2);
        users[2] = getView().findViewById(R.id.editTextPlayer3);
        new Thread(() -> {
            server = new GameServer();
            server.registerUserChangedCallback(this::updateUsers);
            Game.getInstance().setServer(server);
        }).start();
        String ip = NetworkServerKryo.getLocalIPAddress();
        ((TextView)getView().findViewById(R.id.serverIP)).setText(ip);
        getView().findViewById(R.id.createServerButton2).setOnClickListener(this::startGame);
    }

    private void startGame(View view){
        try {
            String username = ((EditText)view.findViewById(R.id.editTextPlayer)).getText().toString();
            Game.getInstance().setClient(new GameClient("localhost", username));
        } catch (IOException e) {
            Log.e(NetworkConstants.TAG, e.getMessage(), e);
        }
        // TODO: implement
    }

    private void updateUsers(String username){
        for (int i = 0; i < users.length; i++) {
            users[i].setText(server.getClientUsernameAt(i));
        }
    }
}

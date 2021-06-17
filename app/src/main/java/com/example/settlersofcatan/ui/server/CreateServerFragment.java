package com.example.settlersofcatan.ui.server;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.settlersofcatan.R;
import com.example.settlersofcatan.game.Game;
import com.example.settlersofcatan.game.development_cards.DevelopmentCardDeck;
import com.example.settlersofcatan.game.resources.PlayerResources;
import com.example.settlersofcatan.server_client.GameClient;
import com.example.settlersofcatan.server_client.GameServer;
import com.example.settlersofcatan.server_client.networking.dto.DevelopmentCardMessage;
import com.example.settlersofcatan.server_client.networking.dto.GameStateMessage;
import com.example.settlersofcatan.server_client.networking.dto.PlayerResourcesMessage;
import com.example.settlersofcatan.server_client.networking.kryonet.NetworkServerKryo;
import com.example.settlersofcatan.ui.game.FragmentInfo;
import com.example.settlersofcatan.ui.game.GameActivity;

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

        ImageButton btn = getView().findViewById(R.id.imageButton);
        btn.setOnClickListener((v)->showInfo());

    }

    private void startGame(View view){
        String username = ((EditText)getView().findViewById(R.id.editTextPlayer)).getText().toString();
        new Thread(
                () -> {
                    GameClient client = GameClient.getInstance();
                    client.connect("localhost", username);
                    client.registerStartGameCallback((message) -> {
                        Intent intent = new Intent(getActivity(), GameActivity.class);
                        startActivity(intent);
                    });

                    Game game = Game.getInstance();
                    DevelopmentCardDeck deck = DevelopmentCardDeck.getInstance();
                    game.init(server.getClientUsernames());
                    PlayerResources playerResources = PlayerResources.getInstance();
                    server.broadcastMessage(new GameStateMessage(game));
                    server.broadcastMessage(new DevelopmentCardMessage(deck));
                    server.broadcastMessage(new PlayerResourcesMessage(playerResources));
                }
        ).start();
    }

    private void updateUsers(String username){
        getActivity().runOnUiThread(() -> {
            for (int i = 1; i < users.length; i++) {
                users[i].setText(server.getClientUsernameAt(i));
            }
        });
    }
    private void showInfo(){
       getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.infoframe,new FragmentInfo()).commit();
    }
}

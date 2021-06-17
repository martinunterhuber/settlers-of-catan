package com.example.settlersofcatan.ui.server;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.settlersofcatan.R;
import com.example.settlersofcatan.server_client.GameClient;

public class JoinServerFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_join_server, container, false);
        view.findViewById(R.id.joinServerButton).setOnClickListener(this::joinServer);
        return view;
    }

    private void joinServer(View view){
        String host = ((EditText) getView().findViewById(R.id.editTextServerIP)).getText().toString();
        String username = ((EditText) getView().findViewById(R.id.editTextUsername)).getText().toString();

        new Thread(() -> {
                GameClient client = GameClient.getInstance();
                client.connect(host, username);
                if (!client.isConnected()){
                    alertConnectionError(host);
                } else {
                    Intent intent = new Intent(this.getActivity(), WaitForHostActivity.class);
                    startActivity(intent);
                }
            }
        ).start();
    }

    private void alertConnectionError(String host){
        getActivity().runOnUiThread(() ->
            new AlertDialog.Builder(getContext())
                    .setTitle(R.string.connection_error)
                    .setMessage(getResources().getString(R.string.could_not_connect, host))
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> {})
                    .show()
        );
    }
}

package com.example.settlersofcatan.server_client.networking.dto;

import com.example.settlersofcatan.ui.ranking.Ranking;

public class ClientWinMessage extends BaseMessage{
    public Ranking ranking;

    public ClientWinMessage(){

    }

    public ClientWinMessage(Ranking ranking) {
        this.ranking = ranking;
    }

    @Override
    public String toString() {
        return ranking.toString();
    }
}

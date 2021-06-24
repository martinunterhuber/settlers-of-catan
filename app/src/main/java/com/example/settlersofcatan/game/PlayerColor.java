package com.example.settlersofcatan.game;

public enum PlayerColor {
    GREEN(0), RED(1), ORANGE(2), BLUE(3);

    private final int id;

    PlayerColor(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

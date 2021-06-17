package com.example.settlersofcatan.ui.board;

public abstract class HexagonPart {
    private int resID;

    public int getResID() {
        return resID;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }

    public abstract void setSelectedResID();
}

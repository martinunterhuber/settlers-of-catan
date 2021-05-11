package com.example.settlersofcatan;

public abstract class HexagonPart {
    private int resID;

    public int getResID() {
        return resID;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }

    abstract void setSelectedResID();
}

package com.example.settlersofcatan.game;

public class TileCoordinates {
    private final int r;
    private final int q;

    public TileCoordinates(int r, int q){
        this.r = r;
        this.q = q;
    }

    public int getR() {
        return r;
    }

    public int getQ() {
        return q;
    }

    public double getX(){
        return Math.sqrt(3) * (double) q  +  Math.sqrt(3)/2 * (double) r;
    }

    public double getY(){
        return 3./2 * (double) r;
    }
}

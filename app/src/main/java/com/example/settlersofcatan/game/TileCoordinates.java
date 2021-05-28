package com.example.settlersofcatan.game;

public class TileCoordinates {
    private int q;
    private int r;

    public TileCoordinates(){

    }

    public TileCoordinates(int q, int r){
        this.q = q;
        this.r = r;
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

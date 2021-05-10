package com.example.settlersofcatan;

import com.example.settlersofcatan.game.Node;

public class Point {
    private int x;
    private int y;
    private int resID;
    private boolean isAccessable=true;
    private Node node;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(int x, int y, int resID, Node node) {
        this.x = x;
        this.y = y;
        this.resID = resID;
        this.node = node;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getResID() {
        return resID;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }

    public boolean isAccessable() {
        return isAccessable;
    }

    public void setAccessable(boolean accessable) {
        isAccessable = accessable;
    }

    public Node getNode() {
        return node;
    }
}

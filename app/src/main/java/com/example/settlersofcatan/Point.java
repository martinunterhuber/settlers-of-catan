package com.example.settlersofcatan;

import com.example.settlersofcatan.game.Node;
import com.example.settlersofcatan.game.NodePlaceable;
import com.example.settlersofcatan.game.Settlement;

public class Point implements HexagonPart {
    private int x;
    private int y;
    private int resID;
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

    @Override
    public void setSelectedResID(){
        NodePlaceable building = node.getBuilding();
        if (building == null){
            resID = R.drawable.corner_selected;
        } else if (building instanceof Settlement) {
            resID = R.drawable.settlement_selected;
        }
    }

    public Node getNode() {
        return node;
    }
}

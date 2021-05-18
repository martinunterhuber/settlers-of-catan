package com.example.settlersofcatan;

import com.example.settlersofcatan.game.Node;
import com.example.settlersofcatan.game.NodePlaceable;
import com.example.settlersofcatan.game.Settlement;

public class Point extends HexagonPart {
    private int x;
    private int y;
    private Node node;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(int x, int y, int resID, Node node) {
        this.x = x;
        this.y = y;
        this.setResID(resID);
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

    public double getDistanceTo(Point point){
        int dx = this.x - point.x;
        int dy = this.y - point.y;
        return Math.sqrt(dx*dx  + dy*dy);
    }


    @Override
    public void setSelectedResID(){
        NodePlaceable building = node.getBuilding();
        if (building == null){
            setResID(R.drawable.corner_selected);
        } else if (building instanceof Settlement) {
            setResID(R.drawable.settlement_selected);
        }
    }

    public Node getNode() {
        return node;
    }
}

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

    public int getY() {
        return y;
    }

    public double getDistanceTo(Point point){
        int dx = this.x - point.x;
        int dy = this.y - point.y;
        return Math.sqrt((double)dx*dx  + dy*dy);
    }

    /**
        Returns the distance from 'this' to the line segment bounded by p1 and p2
        Source: http://paulbourke.net/geometry/pointlineplane/
     */
    public double getDistanceToLineSegmentBoundedBy(Point p1, Point p2){
        double dx = (double) p2.x - p1.x;
        double dy = (double) p2.y - p1.y;

        double u = ((this.x - p1.x) * dx + (this.y - p1.y) * dy) / (dx * dx + dy * dy);

        Point closestPoint;
        if (u < 0) {
            closestPoint = p1;
        } else if (u > 1) {
            closestPoint = p2;
        } else {
            closestPoint = new Point((int) (p1.x + u * dx), (int) (p1.y + u * dy));
        }

        return this.getDistanceTo(closestPoint);
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

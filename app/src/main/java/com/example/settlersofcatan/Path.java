package com.example.settlersofcatan;

import com.example.settlersofcatan.game.Edge;
import com.example.settlersofcatan.game.NodePlaceable;
import com.example.settlersofcatan.game.Settlement;

public class Path extends HexagonPart {
    private Point x1;
    private Point x2;
    private int length;
    private Edge edge;

    public Path(Point x1, Point x2) {
        this.x1 = x1;
        this.x2 = x2;
        //Pythagoras
        length=(int) Math.sqrt((x2.getX()-x1.getX())*(x2.getX()-x1.getX())
                                    +(x2.getY()-x1.getY())*(x2.getY()-x1.getY()));
    }

    public Path(Point x1, Point x2, int resID, Edge edge) {
        this.x1 = x1;
        this.x2 = x2;
        this.setResID(resID);
        this.edge = edge;

        //Pythagoras
        length=(int) Math.sqrt((x2.getX()-x1.getX())*(x2.getX()-x1.getX())
                +(x2.getY()-x1.getY())*(x2.getY()-x1.getY()));
    }

    public Point getX1() {
        return x1;
    }

    public Point getX2() {
        return x2;
    }

    public int getLength() {
        return length;
    }

    public Edge getEdge() {
        return edge;
    }

    public int getLengthX(){
        return (x1.getX() - x2.getX());
    }

    public int getLengthY(){
        return (x1.getY() - x2.getY());
    }

    @Override
    public void setSelectedResID(){
        setResID(R.drawable.road_selected);
    }
}

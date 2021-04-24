package com.example.settlersofcatan;

public class Path {
    private Point x1;
    private Point x2;
    private int length;

    public Path(Point x1, Point x2) {
        this.x1 = x1;
        this.x2 = x2;
        //Satz des Pythagoras
        length=(int)Math.abs(Math.sqrt((x2.getX()-x1.getX())^2
                                    +(x2.getY()-x1.getY())^2));
    }

    public Point getX1() {
        return x1;
    }

    public void setX1(Point x1) {
        this.x1 = x1;
    }

    public Point getX2() {
        return x2;
    }

    public void setX2(Point x2) {
        this.x2 = x2;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}

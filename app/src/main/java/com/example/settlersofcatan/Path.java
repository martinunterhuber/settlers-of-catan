package com.example.settlersofcatan;

public class Path {
    private Point x1;
    private Point x2;
    private int length;
    private int resID;

    public Path(Point x1, Point x2) {
        this.x1 = x1;
        this.x2 = x2;
        //Pythagoras
        length=(int) Math.sqrt((x2.getX()-x1.getX())*(x2.getX()-x1.getX())
                                    +(x2.getY()-x1.getY())*(x2.getY()-x1.getY()));
    }

    public Path(Point x1, Point x2, int resID) {
        this.x1 = x1;
        this.x2 = x2;
        this.resID = resID;

        //Pythagoras
        length=(int) Math.sqrt((x2.getX()-x1.getX())*(x2.getX()-x1.getX())
                +(x2.getY()-x1.getY())*(x2.getY()-x1.getY()));
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

    public int getResID() {
        return resID;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }
}

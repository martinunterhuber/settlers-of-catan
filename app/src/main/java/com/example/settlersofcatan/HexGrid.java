package com.example.settlersofcatan;

import java.util.ArrayList;

/**
 * Class for hexagonal grid without double paths or corner points.
 */
public class HexGrid {

    private final ArrayList<Point> corners;
    private final ArrayList<Path> paths;

    private Path touchedPath;
    private Point touchedCorner;

    public HexGrid(Hexagon[] tiles) {
        this.corners=new ArrayList<>();
        this.paths=new ArrayList<>();

        for (Hexagon hex : tiles) {
            for (Point p : hex.getPoints()){
                addCorner(p);
            }
            for (Path p : hex.getPaths()){
                addPaths(p);
            }
        }
    }

    private void addCorner(Point corner){
        boolean dublicate=false;
        for (Point c : corners){

            if (corner.getX() > c.getX() - 5 && corner.getX() < c.getX()+5 && corner.getY() > c.getY() -5 && corner.getY() < c.getY()+5) {
                dublicate = true;
                break;
            }
        }

        if (!dublicate){
            corners.add(corner);
        }
    }

    private void addPaths(Path path){
        boolean dublicate=false;
        for (Path p : paths){

            if (path.getX1().getX() > p.getX2().getX() - 20 && path.getX1().getX() < p.getX2().getX() + 20
                        && path.getX1().getY() > p.getX2().getY() - 20 && path.getX1().getY() < p.getX2().getY() + 20
                        && path.getX2().getX() > p.getX1().getX() - 20 && path.getX2().getX() < p.getX1().getX() + 20
                        && path.getX2().getY() > p.getX1().getY() - 20 && path.getX2().getY() < p.getX1().getY() + 20) {
                dublicate = true;
                break;
            }

        }

        if (!dublicate){
            paths.add(path);
        }
    }

    /**
     * Checks if the point the user has touched lies on one of the lines.
     */
    public boolean isOnLine(Point point){
        for (Path p : paths) {


            if ((point.getX() > p.getX1().getX() && point.getX() < p.getX2().getX() && point.getY() > p.getX1().getY() && point.getY() < p.getX2().getY())
                    || (point.getX() > p.getX2().getX() && point.getX() < p.getX1().getX() && point.getY() > p.getX1().getY() && point.getY() < p.getX2().getY())
                    || (point.getX() > p.getX2().getX() && point.getX() < p.getX1().getX() && point.getY() > p.getX2().getY() && point.getY() < p.getX1().getY())
                    || (point.getX() > p.getX1().getX() && point.getX() < p.getX2().getX() && point.getY() > p.getX2().getY() && point.getY() < p.getX1().getY())) {
                touchedPath=p;
                return true;
            }

            //vertical lines
            if (point.getX() > p.getX1().getX() - 15 && point.getX() < p.getX1().getX() + 15 &&
                    ((point.getY() > p.getX1().getY() && point.getY() < p.getX2().getY()) || (point.getY() > p.getX2().getY() && point.getY() < p.getX1().getY()))){
                touchedPath=p;
                return true;
            }

        }
        return false;
    }

    /**
     * Checks if the point the user has touched lies in one of the circles.
     */
    public boolean isInCircle(Point corner){
        for (Point c : corners){
            if (corner.getX() > c.getX() - 10 && corner.getX() < c.getX()+50 && corner.getY() > c.getY() -10 && corner.getY() < c.getY()+50) {
                touchedCorner = c;
                return true;
            }
        }

        return false;
    }

//----------- Getter and Setter -----------------------------------------------------------------

    public ArrayList<Point> getCorners() {
        return corners;
    }

    public ArrayList<Path> getPaths() {
        return paths;
    }

    public Path getTouchedLine(){
        return touchedPath;
    }

    public Point getTouchedCorner() {
        return touchedCorner;
    }


}

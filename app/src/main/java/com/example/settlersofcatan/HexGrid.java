package com.example.settlersofcatan;

import java.util.ArrayList;

/**
 * Class for hexagonal grid without double paths or corner points.
 */
public class HexGrid {

    private ArrayList<Point> corners;
    private ArrayList<Path> paths;

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

    public void addCorner(Point corner){
        boolean dublicate=false;
        for (Point c : corners){
            if (corner.getX() == c.getX() && corner.getY() == c.getY()) {
                dublicate = true;
                break;
            }
        }

        if (!dublicate){
            corners.add(corner);
        }
    }

    public void addPaths(Path path){
        boolean dublicate=false;
        for (Path p : paths){
            if (path.getX1().getX() == p.getX1().getX() && path.getX1().getY() == p.getX1().getY() &&
                    path.getX2().getX() == p.getX2().getX() && path.getX2().getY() == p.getX2().getY()) {
                dublicate = true;
                break;
            }
        }

        if (!dublicate){
            paths.add(path);
        }
    }


//----------- Getter and Setter -----------------------------------------------------------------

    public ArrayList<Point> getCorners() {
        return corners;
    }

    public void setCorners(ArrayList<Point> corners) {
        this.corners = corners;
    }

    public ArrayList<Path> getPaths() {
        return paths;
    }

    public void setPaths(ArrayList<Path> paths) {
        this.paths = paths;
    }


}

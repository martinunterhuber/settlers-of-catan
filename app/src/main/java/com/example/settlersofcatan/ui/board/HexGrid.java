package com.example.settlersofcatan.ui.board;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for hexagonal grid without double paths or corner points.
 */
public class HexGrid {

    private final List<Point> corners;
    private final List<Path> paths;
    private final Hexagon[] tiles;

    private Path touchedPath;
    private Point touchedCorner;
    private Hexagon touchedTile;

    public HexGrid(Hexagon[] tiles) {
        this.corners = new ArrayList<>();
        this.paths = new ArrayList<>();
        this.tiles = tiles;

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
        for (Point c : corners){
            if (c.getDistanceTo(corner) < 5) {
                return;
            }
        }
        corners.add(corner);
    }

    private void addPaths(Path path){
        for (Path p : paths){
            if ((path.getX1().getDistanceTo(p.getX1()) < 5 && path.getX2().getDistanceTo(p.getX2()) < 5)
                || (path.getX1().getDistanceTo(p.getX2()) < 5 && path.getX2().getDistanceTo(p.getX1()) < 5)){
                return;
            }
        }
        paths.add(path);
    }

    /**
     * Checks if the point the user has touched lies on one of the lines.
     */
    public boolean isOnLine(Point touched){
        touchedPath = null;
        double shortestDistance = Double.MAX_VALUE;
        for (Path path : paths) {
            double distance = touched.getDistanceToLineSegmentBoundedBy(path.getX1(), path.getX2());
            if (distance < 50 && distance < shortestDistance){
                touchedPath = path;
                shortestDistance = distance;
            }
        }
        return touchedPath != null;
    }

    /**
     * Checks if the point the user has touched lies in one of the circles.
     */
    public boolean isInCircle(Point touched){
        touchedCorner = null;
        double shortestDistance = Double.MAX_VALUE;
        for (Point corner : corners){
            double distance = corner.getDistanceTo(touched);
            if (distance < 30 && distance < shortestDistance){
                touchedCorner = corner;
                shortestDistance = distance;
            }
        }
        return touchedCorner != null;
    }

    public boolean isInTile(Point point){
        for (Hexagon tile : tiles){
            double distance = point.getDistanceTo(tile.getCenter());
            if (distance < 40){
                touchedTile = tile;
                return true;
            }
        }
        return false;
    }

//----------- Getter and Setter -----------------------------------------------------------------

    public List<Point> getCorners() {
        return corners;
    }

    public List<Path> getPaths() {
        return paths;
    }

    public Path getTouchedLine(){
        return touchedPath;
    }

    public Point getTouchedCorner() {
        return touchedCorner;
    }

    public Hexagon getTouchedTile() {
        return touchedTile;
    }

    public Hexagon[] getTiles() {
        return tiles;
    }
}

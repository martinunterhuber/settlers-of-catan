package com.example.settlersofcatan;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class for hexagonal grid without double paths or corner points.
 */
public class HexGrid {

    private final ArrayList<Point> corners;
    private final ArrayList<Path> paths;
    private final HashMap<Point, ArrayList<Point>> neighbouringCities;
    private final HashMap<Point, ArrayList<Path>> neighbouringRoads;
    private final Hexagon[] tiles;

    private Path touchedPath;
    private Point touchedCorner;
    private Hexagon touchedTile;

    public HexGrid(Hexagon[] tiles) {
        this.corners = new ArrayList<>();
        this.paths = new ArrayList<>();
        this.neighbouringCities = new HashMap<>();
        this.neighbouringRoads = new HashMap<>();
        this.tiles = tiles;

        for (Hexagon hex : tiles) {
            for (Point p : hex.getPoints()){
                addCorner(p);
            }
            for (Path p : hex.getPaths()){
                addPaths(p);
            }
        }

        for (Point p : corners){
            addNeighbouringRoads(p);
            addNeighbouringCities(p);
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
            neighbouringCities.put(corner, new ArrayList<>());
            neighbouringRoads.put(corner, new ArrayList<>());
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

    private void addNeighbouringCities(Point corner){
        ArrayList<Path> roads = neighbouringRoads.get(corner);
        ArrayList<Point> cities = neighbouringCities.get(corner);
        int max = 3;

        for (Path p : roads){

            /**
             * If the point X1 of the road is near the city, then search for a neighbouring city near X2.
             */
            if (p.getX1().getX() > corner.getX() - 10 && p.getX1().getX() < corner.getX() + 10
                    && p.getX1().getY() > corner.getY() - 10 && p.getX1().getY() < corner.getY() + 10){
                for (Point c : corners){
                    if (c.getX() >  p.getX2().getX() - 10 &&  c.getX() < p.getX2().getX() + 10
                            && c.getY() >  p.getX2().getY() - 10 &&  c.getY() < p.getX2().getY() + 10){

                        cities.add(c);
                        neighbouringCities.put(corner, cities);
                        max--;
                        break;
                    }

                }
            }else {     //Same as above, but X1 and X2 are swapped.
                for (Point c : corners){
                    if (c.getX() >  p.getX1().getX() - 10 &&  c.getX() < p.getX1().getX() + 10
                            && c.getY() >  p.getX1().getY() - 10 &&  c.getY() < p.getX1().getY() + 10){

                        cities.add(c);
                        neighbouringCities.put(corner, cities);
                        max--;
                        break;
                    }
                }
            }

            if (max == 0){
                break;
            }

        }


    }

    private void addNeighbouringRoads(Point corner){
        ArrayList<Path> roads = neighbouringRoads.get(corner);
        int max = 3;

        for (Path road : paths){
            if ((road.getX1().getX() > corner.getX() - 10 && road.getX1().getX() < corner.getX() + 10
                    && road.getX1().getY() > corner.getY() - 10 && road.getX1().getY() < corner.getY() + 10)
                    || (road.getX2().getX() > corner.getX() - 10 && road.getX2().getX() < corner.getX() + 10
                    && road.getX2().getY() > corner.getY() - 10 && road.getX2().getY() < corner.getY() + 10)){
                roads.add(road);
                neighbouringRoads.put(corner, roads);
                max--;
            }

            if (max == 0){
                break;
            }
        }


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

    public Hexagon getTouchedTile() {
        return touchedTile;
    }

    public Hexagon[] getTiles() {
        return tiles;
    }

    public HashMap<Point, ArrayList<Point>> getNeighbouringCities() {
        return neighbouringCities;
    }

    public HashMap<Point, ArrayList<Path>> getNeighbouringRoads() {
        return neighbouringRoads;
    }

    public ArrayList<Point> getNeighbouringCities (Point corner){
        return  neighbouringCities.get(corner);
    }

    public ArrayList<Path> getNeighbouringRoads(Point corner) {
        return neighbouringRoads.get(corner);
    }
}

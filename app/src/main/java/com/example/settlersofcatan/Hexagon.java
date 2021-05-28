package com.example.settlersofcatan;

import android.util.Log;

import com.example.settlersofcatan.game.Node;
import com.example.settlersofcatan.game.Resource;
import com.example.settlersofcatan.game.Tile;

/**
 * The class calculates corner points and paths of a hexagon based on the center point.
 */
public class Hexagon extends HexagonPart {
    private final Point center;
    private final int radius;
    private final int width;
    private final int halfWidth;
    private final Tile tile;
    private final int drawableResourceId;
    private final int b;    // Y-Distance from center to NE, NW, SE, SW Nodes

    private Point[] points=new Point[6];
    private Path[] paths=new Path[6];

    public Hexagon(Tile tile, int width){
        this.tile = tile;
        this.radius = (int) (width/Math.sqrt(3));
        this.width = width;
        this.halfWidth = width / 2;
        this.center = hexToPixel(tile, this.radius, new Point(-halfWidth, (int)(radius * 1.5)));
        this.drawableResourceId = getResourceIdFromTileResource();

        b = (int) (Math.sqrt(Math.pow(radius, 2) - Math.pow(halfWidth, 2)));

        calcPoints();
        calcPaths();
    }

    private Point hexToPixel(Tile tile, int scale, Point offset){
        int x = (int)(scale * tile.getX()) + offset.getX();
        int y = (int)(scale * tile.getY()) + offset.getY();
        return new Point(x, y);
    }

    private int getResourceIdFromTileResource(){
        int drawable;
        Resource resource = tile.getResource();
        if (resource == null){
            drawable = R.drawable.deserthex;
        } else {
            switch (tile.getResource()){
                case SHEEP:
                    drawable = R.drawable.sheephex;
                    break;
                case FOREST:
                    drawable = R.drawable.woodhex;
                    break;
                case WHEAT:
                    drawable = R.drawable.wheathex;
                    break;
                case CLAY:
                    drawable = R.drawable.clayhex;
                    break;
                case ORE:
                    drawable = R.drawable.orehex;
                    break;
                default:
                    drawable = R.drawable.deserthex;
            }
        }
        return drawable;
    }

    /**
     * Calculates the corners of the hexagon starting from the top point in clockwise direction.
     */
    private void calcPoints(){
        Node[] nodes = tile.getNodes();
        int i = 0;

        points[0] = new Point(center.getX(),center.getY() - radius, R.drawable.corner_unselected, nodes[i++]);
        points[1] = new Point(points[0].getX() + halfWidth, points[0].getY() + b, R.drawable.corner_unselected, nodes[i++]);
        points[2] = new Point(points[1].getX(), points[1].getY() + radius, R.drawable.corner_unselected, nodes[i++]);
        points[3] = new Point(center.getX(),center.getY() + radius, R.drawable.corner_unselected, nodes[i++]);
        points[4] = new Point(points[2].getX() - width, points[2].getY(), R.drawable.corner_unselected, nodes[i++]);
        points[5] = new Point(points[4].getX(), points[1].getY(), R.drawable.corner_unselected, nodes[i]);
    }

    /**
     * Calculates the paths between the points in a clockwise direction.
     */
    private void calcPaths(){
        for (int i=0; i<6; i++){
            paths[i]=new Path(points[i],points[(i+1) % 6], R.drawable.road_unselected, tile.getEdges()[i]);
        }
    }

//------- Getter and Setter --------------------------------------------------------------------


    public Point[] getPoints() {
        return points;
    }

    public void setPoints(Point[] points) {
        this.points = points;
    }

    public Path[] getPaths() {
        return paths;
    }

    public void setPaths(Path[] paths) {
        this.paths = paths;
    }

    public Point getCenter() {
        return center;
    }

    public int getDrawableResourceId() {
        return drawableResourceId;
    }

    public int getRadius() {
        return radius;
    }

    public int getWidth() {
        return width;
    }

    public int getTileNumber(){
        return tile.getNumber();
    }

    public int getHalfWidth() {
        return halfWidth;
    }

    public Tile getTile() {
        return tile;
    }

    @Override
    void setSelectedResID() {
        this.setResID(R.drawable.tile_selected);
    }
}

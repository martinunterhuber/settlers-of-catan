package com.example.settlersofcatan;

/**
 * The class calculates corner points and paths of a hexagon based on the center point.
 */
public class Hexagon {
    private final Point center;
    private final int radius;
    private final int height;
    //auxiliary variable
    private double b;
    //maybe for later use
    private String resource;
    private int numberToken;
    private Point[] points=new Point[6];
    private Path[] paths=new Path[6];

    public Hexagon(Point center, int radius, int height){
        this.center=center;
        this.radius=radius;
        this.height=height;

        b= Math.sqrt(Math.pow(radius,2)-Math.pow(height,2));

        calcPoints();
        calcPaths();
    }

    /**
     * Calculates the corners of the hexagon starting from the top point in clockwise direction.
     */
    private void calcPoints(){

        //top corner point
        points[0]=new Point(center.getX(),center.getY()-radius, R.drawable.corner_unselected);

        points[1]=new Point(points[0].getX()+height, (int) (points[0].getY()+b), R.drawable.corner_unselected);

        points[2]=new Point(points[1].getX(), points[1].getY()+radius, R.drawable.corner_unselected);

        //bottom corner point
        points[3]=new Point(center.getX(),center.getY()+radius, R.drawable.corner_unselected);

        points[4]=new Point(points[2].getX()-2*height, points[2].getY(), R.drawable.corner_unselected);

        points[5]=new Point(points[4].getX(), points[4].getY()-radius, R.drawable.corner_unselected);
    }

    /**
     * Calculates the paths between the points in a clockwise direction.
     */
    private void calcPaths(){

        for (int i=0; i<5; i++){
            paths[i]=new Path(points[i],points[i+1], R.drawable.road_unselected);
        }

        paths[5]=new Path(points[5],points[0], R.drawable.road_unselected);
    }

//------- Getter and Setter --------------------------------------------------------------------

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

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

    public int getNumberToken() {
        return numberToken;
    }

    public void setNumberToken(int numberToken) {
        this.numberToken = numberToken;
    }

    public Point getCenter() {
        return center;
    }

}

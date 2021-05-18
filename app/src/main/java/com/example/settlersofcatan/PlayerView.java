package com.example.settlersofcatan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.settlersofcatan.game.Game;
import com.example.settlersofcatan.game.NodePlaceable;
import com.example.settlersofcatan.game.Road;
import com.example.settlersofcatan.game.Settlement;
import com.example.settlersofcatan.game.Tile;
import com.example.settlersofcatan.server_client.GameClient;

/**
 * View class on which the cities, settlements and roads of the players are placed.
 */
public class PlayerView extends View {

    private HexGrid hexGrid;

    private HexagonPart selected;

    private GameClient client = GameClient.getInstance();

    final static int[] SETTLEMENT_IDS = new int[]{R.drawable.settlement_green , R.drawable.settlement_orange, R.drawable.settlement_red, R.drawable.settlement_blue};
    final static int[] ROAD_IDS = new int[]{R.drawable.road_green, R.drawable.road_orange, R.drawable.road_red, R.drawable.road_blue};
    final static int[] CITY_IDS = new int[]{R.drawable.city_green, R.drawable.city_orange, R.drawable.city_red, R.drawable.city_blue};

    public PlayerView(Context context) {
        super(context);
    }

    public PlayerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (hexGrid.getCorners()!=null) {
            setRoads();
            drawRoads(canvas);
            setBuildings();
            drawCorners(canvas);
            setTiles();
            drawTiles(canvas);
        }
    }

    public void setBuildings(){
        for (Point point : hexGrid.getCorners()){
            NodePlaceable building = point.getNode().getBuilding();
            if (building != null){
                if (building instanceof Settlement){
                    point.setResID(SETTLEMENT_IDS[building.getPlayer().getId()]);
                } else {
                    point.setResID(CITY_IDS[building.getPlayer().getId()]);
                }
            } else {
                point.setResID(R.drawable.corner_unselected);
            }
            if (point == selected) {
                point.setSelectedResID();
            }
        }
    }

    public void setRoads(){
        for (Path path : hexGrid.getPaths()){
            Road road = path.getEdge().getRoad();
            if (road != null){
                path.setResID(ROAD_IDS[road.getPlayer().getId()]);
            } else if (selected != path) {
                path.setResID(R.drawable.road_unselected);
            } else {
                path.setSelectedResID();
            }
        }
    }

    public void setTiles(){
        for (Hexagon tile : hexGrid.getTiles()){
            if (tile.getTile().hasRobber()){
                tile.setResID(R.drawable.robber);
            } else if (selected == tile){
                tile.setSelectedResID();
            } else {
                tile.setResID(0);
            }
        }
    }

    public void markSelected(Point touched){
        HexagonPart touchedPart = null;
        if (hexGrid.isInCircle(touched)) {
            touchedPart = hexGrid.getTouchedCorner();
        } else if (hexGrid.isOnLine(touched)) {
            touchedPart = hexGrid.getTouchedLine();
        } else if (hexGrid.isInTile(touched)){
            touchedPart = hexGrid.getTouchedTile();
        }

        if (selected != null && touchedPart == selected){
            selected = null;
        } else {
            selected = touchedPart;
        }
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Point touched = new Point((int) event.getX(), (int) event.getY());

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            markSelected(touched);
            return true;
        }
        return false;
    }

    public void buildRoad(){
        if (selected != null && selected instanceof Path){
            Game.getInstance().buildRoad(((Path)selected).getEdge(), client.getId());
            ((AppCompatActivity) getContext()).findViewById(R.id.resourceView).invalidate();
            invalidate();
        }
    }

    public void buildSettlement() {
        if (selected != null && selected instanceof Point){
            Game.getInstance().buildSettlement(((Point)selected).getNode(), client.getId());
            selected = null;
            ((AppCompatActivity) getContext()).findViewById(R.id.resourceView).invalidate();
            invalidate();
        }
    }

    public void buildCity(){
        if (selected != null && selected instanceof Point){
            Game.getInstance().buildCity(((Point)selected).getNode(), client.getId());
            ((AppCompatActivity) getContext()).findViewById(R.id.resourceView).invalidate();
            invalidate();
        }
    }

//----------- Methods for drawing -------------------------------------------------------------------------

    private void drawRoads(Canvas canvas){
        for (Path p : hexGrid.getPaths()){
            Bitmap bitmap = getBitmap(p.getResID());
            bitmap = Bitmap.createScaledBitmap(bitmap, p.getLength(),20, false);

            /**
             *                        A
             *                        ^
             *                     /    \
             *                F  +       +  B
             *                   |       |
             *                E  +       +  C
             *                    \   /
             *                      v
             *                      D
             */



            //vertical lines [BC] and [EF]
            if (p.getX1().getX()==p.getX2().getX()){
                bitmap = rotateBitmap(bitmap, 90);

                if (p.getX1().getY()<p.getX2().getY()) {
                    canvas.drawBitmap(bitmap, p.getX1().getX()-10, p.getX1().getY(), null);
                }else {
                    canvas.drawBitmap(bitmap, p.getX2().getX()-10, p.getX2().getY(), null);
                }

            }else if (p.getX1().getX() < p.getX2().getX()       // distance [AB]
                    && p.getX1().getY() < p.getX2().getY()){
                bitmap = rotateBitmap(bitmap, 30);

                canvas.drawBitmap(bitmap, p.getX1().getX(), p.getX1().getY()-10,null);

            }else if (p.getX1().getX() > p.getX2().getX()       // distance [CD]
                    && p.getX1().getY() < p.getX2().getY()){
                bitmap = rotateBitmap(bitmap, 150);

                canvas.drawBitmap(bitmap, p.getX2().getX(), p.getX1().getY()-10,null);

            }else if (p.getX1().getX() > p.getX2().getX()       // distance [DE]
                    && p.getX1().getY() > p.getX2().getY()){
                bitmap = rotateBitmap(bitmap, 30);

                canvas.drawBitmap(bitmap, p.getX2().getX(), p.getX2().getY()-10,null);

            }else if (p.getX1().getX() < p.getX2().getX()       // distance [FA]
                    && p.getX1().getY() > p.getX2().getY()){
                bitmap = rotateBitmap(bitmap, 150);

                canvas.drawBitmap(bitmap, p.getX1().getX(), p.getX2().getY()-10,null);
            }
        }
    }

    private void drawCorners(Canvas canvas){

        for (Point p : hexGrid.getCorners()){
            Bitmap bitmap = getBitmap(p.getResID());

            canvas.drawBitmap(bitmap,p.getX()-bitmap.getWidth()/2,p.getY()-bitmap.getHeight()/2,null);
        }
    }

    private void drawTiles(Canvas canvas){
        for (Hexagon tile : hexGrid.getTiles()){
            Bitmap bitmap = getBitmap(tile.getResID());
            if (bitmap != null){
                bitmap = Bitmap.createScaledBitmap(bitmap, 120,120, false);
                canvas.drawBitmap(bitmap, tile.getCenter().getX() - bitmap.getWidth() / 2, tile.getCenter().getY() - bitmap.getHeight() / 2,null);
            }
        }
    }

    /**
     * XML drawable gets converted into bitmap.
     */
    private Bitmap getBitmap(int drawableRes) {
        if (drawableRes == 0){
            return null;
        }
        Drawable drawable = getResources().getDrawable(drawableRes);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static Bitmap rotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

//--------- Getter and Setter -------------------------------------------------------------------------

    public void setHexGrid(HexGrid grid){
        this.hexGrid=grid;
    }

    public Tile getSelectedTile(){
        if (selected != null && selected instanceof Hexagon){
            return ((Hexagon) selected).getTile();
        }
        return null;
    }
}

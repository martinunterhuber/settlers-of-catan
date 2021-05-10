package com.example.settlersofcatan;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import androidx.annotation.Nullable;

import com.example.settlersofcatan.game.Game;
import com.example.settlersofcatan.game.Resource;
import com.example.settlersofcatan.game.Tile;


/**
 * View class on which the board is drawn.
 */

public class MapView extends View {

    private final int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private final int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private final Point[] centers = new Point[19];
    private final Hexagon[] tiles= new Hexagon[19];
    private final Bitmap[] bitmaps= new Bitmap[19];
    private final HexGrid hexGrid;    //to avoid duplicate points or paths
    private int radius;
    private int hexHeight;

    public MapView(Context context) {
        super(context);

        calcCenters();
        generateTiles();
        generateBitmaps();

        hexGrid=new HexGrid(tiles);
    }

    public MapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        calcCenters();
        generateTiles();
        generateBitmaps();

        hexGrid=new HexGrid(tiles);
    }

    public MapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        calcCenters();
        generateTiles();
        generateBitmaps();

        hexGrid=new HexGrid(tiles);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBitmaps(canvas);
        drawNumberTokens(canvas);
    }

    private Point hexToPixel(Tile tile, int scale, Point offset){
        int x = (int)(scale * (Math.sqrt(3) * tile.getQ()  +  Math.sqrt(3)/2 * tile.getR())) + offset.getX();
        int y = (int)(scale * (3./2 * (double)tile.getR())) + offset.getY();
        return new Point(x, y);
    }

    /**
     * Calculates the center points of each hexagon based on the screen size
     * starting from the middle row of the field.
     */
    private void calcCenters(){
        hexHeight = screenWidth/10;
        radius = (int) (2*hexHeight/Math.sqrt(3));
        int screenMiddle = screenHeight/2;

        Tile[] packedTiles = Game.getInstance().getBoard().getPackedTiles();
        for (int i = 0; i < packedTiles.length; i++) {
            Tile tile = packedTiles[i];
            centers[i] = hexToPixel(tile, radius, new Point(-hexHeight, hexHeight * 5));
        }
    }

    private void generateTiles(){
        for (int i=0; i<19; i++){
            tiles[i]=new Hexagon(centers[i], radius, hexHeight);
        }
    }

    /**
     *  Loads all bitmaps that are needed for the board
     */
    private void generateBitmaps(){
        Tile[] packedTiles = Game.getInstance().getBoard().getPackedTiles();
        for (int i = 0; i < packedTiles.length; i++) {
            Tile tile = packedTiles[i];
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

            bitmaps[i]= BitmapFactory.decodeResource(getResources(), drawable);
        }
    }

    /**
     * randomizes the number tokens that are placed on the fields
     */
    private List<Integer> randomizeNumbers(){
        ArrayList<Integer> numberToken=new ArrayList<>(Arrays.asList(2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 11, 12));
        Collections.shuffle(numberToken);
        return numberToken;
    }

    /**
     * XML drawable gets converted into bitmap.
     */
    private Bitmap getBitmap(int drawableRes) {
        Drawable drawable = getResources().getDrawable(drawableRes);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);

        return bitmap;
    }

//-------- Methods for drawing -----------------------------------------------------------------------------

    private void drawBitmaps(Canvas canvas){
        int imgOffsetX= - hexHeight;
        int imgOffsetY= - radius;

        for (int i=0; i<19; i++) {
            Bitmap scaled=Bitmap.createScaledBitmap(bitmaps[i],2*hexHeight,2*radius,false);
            canvas.drawBitmap(scaled,centers[i].getX()+imgOffsetX, centers[i].getY()+imgOffsetY, null);
        }

    }

    private void drawNumberTokens(Canvas canvas){

        Paint textpaint= new Paint();
        textpaint.setColor(Color.BLACK);
        textpaint.setTextAlign(Paint.Align.CENTER);
        textpaint.setTextSize(40f);
        Bitmap numberToken = getBitmap(R.drawable.tilenumbertoken);

        Tile[] tiles = Game.getInstance().getBoard().getPackedTiles();
        for (int i = 0; i < tiles.length; i++) {
            setNumberToken(centers[i], tiles[i].getNumber());
            canvas.drawBitmap(numberToken,centers[i].getX()-50, centers[i].getY()-50, null);
            canvas.drawText(String.valueOf(tiles[i].getNumber()),centers[i].getX(),centers[i].getY()+10, textpaint);
        }

    }

//----------- Getter and Setter -------------------------------------------------------------------

    public HexGrid getHexGrid() {
        return hexGrid;
    }

    public void setNumberToken(Point center, int token){
        for (Hexagon hex : tiles){
            if (hex.getCenter().equals(center)){
                hex.setNumberToken(token);
            }
        }
    }
}

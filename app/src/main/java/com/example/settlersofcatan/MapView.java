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
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

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
        printLines(canvas);
        printCorners(canvas);
    }

/*    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        

        return false;
    }

 */

    /**
     * Calculates the center points of each hexagon based on the screen size
     * starting from the middle row of the field.
     */
    private void calcCenters(){
        hexHeight = screenWidth/10;
        radius = (int) (2*hexHeight/Math.sqrt(3));
        int screenMiddle = screenHeight/2;

        //third row, 5 fields
        centers[7]=new Point(hexHeight, screenMiddle);
        for (int i=1; i<5; i++){
            centers[i+7]=new Point(centers[i+6].getX()+2*hexHeight, screenMiddle);
        }

        //second row, 4 fields
        for (int i=0; i<4; i++){
            centers[i+3]=new Point(centers[i+7].getX()+hexHeight, (int) (screenMiddle - 1.5*radius));
        }

        //fourth row, 4 fields
        for (int i=0; i<4; i++){
            centers[i+12]=new Point(centers[i+7].getX()+hexHeight, (int) (screenMiddle + 1.5*radius));
        }

        //first row, 3 fields
        for (int i = 0; i<3; i++){
            centers[i]=new Point(centers[i+3].getX()+hexHeight, (int) (centers[i+3].getY() - 1.5*radius));
        }

        //fifth row, 3 fields
        for (int i = 0; i<3; i++){
            centers[i+16]=new Point(centers[i+12].getX()+hexHeight, (int) (centers[i+12].getY() + 1.5*radius));
        }

    }

    private void generateTiles(){
        for (int i=0; i<19; i++){
            tiles[i]=new Hexagon(centers[i], radius, hexHeight);
        }
    }

    private List<Point> randomiseTiles(){
        ArrayList<Point> tilesToShuffle = new ArrayList<>(Arrays.asList(centers));
        //tilesToShuffle.addAll(Arrays.asList(centers));

        tilesToShuffle.remove(9);
        Collections.shuffle(tilesToShuffle);
        return tilesToShuffle;
    }

    /**
     *  Loads all bitmaps that are needed for the board
     */
    private void generateBitmaps(){
        bitmaps[0]= BitmapFactory.decodeResource(getResources(),R.drawable.deserthex);

        for (int i=1; i<19; i++){
            if (i<4)
                bitmaps[i]= BitmapFactory.decodeResource(getResources(),R.drawable.clayhex);
            else if (i<7)
                bitmaps[i]= BitmapFactory.decodeResource(getResources(),R.drawable.orehex);
            else if (i<11)
                bitmaps[i]= BitmapFactory.decodeResource(getResources(),R.drawable.wheathex);
            else if (i<15)
                bitmaps[i]= BitmapFactory.decodeResource(getResources(),R.drawable.sheephex);
            else
                bitmaps[i]= BitmapFactory.decodeResource(getResources(),R.drawable.woodhex);
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

        Bitmap desert=Bitmap.createScaledBitmap(bitmaps[0],2*hexHeight,2*radius,false);
        canvas.drawBitmap(desert,centers[9].getX()+imgOffsetX, centers[9].getY()+imgOffsetY, null);

        List<Point> shuffledTiles=randomiseTiles();

        for (int i=1; i<19; i++){
            Bitmap scaled=Bitmap.createScaledBitmap(bitmaps[i],2*hexHeight,2*radius,false);
            canvas.drawBitmap(scaled,shuffledTiles.get(i-1).getX()+imgOffsetX, shuffledTiles.get(i-1).getY()+imgOffsetY, null);
        }

    }

    private void drawNumberTokens(Canvas canvas){

        Paint textpaint= new Paint();
        textpaint.setColor(Color.BLACK);
        textpaint.setTextAlign(Paint.Align.CENTER);
        textpaint.setTextSize(40f);
        Bitmap numberToken = getBitmap(R.drawable.tilenumbertoken);

        List<Point> shuffledTiles=randomiseTiles();
        List<Integer> shuffeldNumberTokens=randomizeNumbers();

        for (int i=0; i<18; i++){
            canvas.drawBitmap(numberToken,shuffledTiles.get(i).getX()-50, shuffledTiles.get(i).getY()-50, null);
            canvas.drawText(shuffeldNumberTokens.get(i).toString(),shuffledTiles.get(i).getX(),shuffledTiles.get(i).getY()+10, textpaint);
        }

    }

    private void printLines(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStrokeWidth(12f);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);

        for (Path p : hexGrid.getPaths()){

            canvas.drawLine(p.getX1().getX(), p.getX1().getY(),
                    p.getX2().getX(), p.getX2().getY(), paint);

        }

    }

    private void printCorners(Canvas canvas){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);

        for (Point p : hexGrid.getCorners()){

            canvas.drawCircle(p.getX(),p.getY(),15, paint);

        }

    }

}

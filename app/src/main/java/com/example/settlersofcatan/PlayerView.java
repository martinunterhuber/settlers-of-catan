package com.example.settlersofcatan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;

/**
 * View class on which the cities, settlements and roads of the players are placed.
 */
public class PlayerView extends View {

    private HexGrid hexGrid;
    private Point touchedPoint;
    private Path touchedPath;

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
            drawRoads(canvas);
            drawCorners(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Point touched = new Point((int) event.getX(), (int) event.getY());

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (hexGrid.isInCircle(touched)) {
                    if (touchedPoint!=null && touchedPoint.getResID()==R.drawable.corner_selected)
                        touchedPoint.setResID(R.drawable.corner_unselected);

                    if (touchedPath!=null && touchedPath.getResID()==R.drawable.road_selected)
                        touchedPath.setResID(R.drawable.road_unselected);

                    touchedPoint = hexGrid.getTouchedCorner();
                    if (touchedPoint.getResID()==R.drawable.corner_unselected) {
                        touchedPoint.setResID(R.drawable.corner_selected);
                        invalidate();
                        return true;
                    }
                }else if (hexGrid.isOnLine(touched)){
                    if (touchedPath!=null && touchedPath.getResID()==R.drawable.road_selected)
                        touchedPath.setResID(R.drawable.road_unselected);

                    if (touchedPoint!=null && touchedPoint.getResID()==R.drawable.corner_selected)
                        touchedPoint.setResID(R.drawable.corner_unselected);

                    touchedPath = hexGrid.getTouchedLine();
                    if (touchedPath.getResID()==R.drawable.road_unselected) {
                        touchedPath.setResID(R.drawable.road_selected);
                        invalidate();
                        return true;
                    }
                }
        }
        return false;
    }

    public void buildRoad(){
        touchedPath.setResID(R.drawable.road_white);
        invalidate();
    }

    public void buildSettlement() {
        touchedPoint.setResID(R.drawable.settlement_white);
        invalidate();
    }

    public void buildCity(){

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


}

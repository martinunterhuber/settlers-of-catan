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
import android.view.View;

import androidx.annotation.Nullable;

import com.example.settlersofcatan.game.Game;
import com.example.settlersofcatan.game.Tile;
import com.example.settlersofcatan.util.OnPostDrawListener;


/**
 * View class on which the board is drawn.
 */

public class MapView extends View {

    private final int WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
    private final int HEXAGON_WIDTH = WIDTH / 5;

    private Hexagon[] tiles= new Hexagon[19];
    private HexGrid hexGrid;

    public MapView(Context context) {
        super(context);
        generateTiles();

        hexGrid = new HexGrid(tiles);
    }

    public MapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        generateTiles();

        hexGrid = new HexGrid(tiles);
    }

    public MapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        generateTiles();

        hexGrid = new HexGrid(tiles);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        invalidate();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        generateTiles();

        hexGrid = new HexGrid(tiles);

        ((OnPostDrawListener) getContext()).onPostDraw();

        drawBitmaps(canvas);
        drawNumberTokens(canvas);
    }

    private void generateTiles(){
        int hexagonWidth = HEXAGON_WIDTH;
        if (getHeight() != 0){
            if (hexagonWidth >= getHeight() / 6){
                hexagonWidth = getHeight() / 6;
            }
        }
        Tile[] packedTiles = Game.getInstance().getBoard().getPackedTiles();
        for (int i = 0; i < packedTiles.length; i++) {
            tiles[i] = new Hexagon(packedTiles[i], hexagonWidth, (WIDTH - 5 * hexagonWidth) / 2);
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

//-------- Methods for drawing -----------------------------------------------------------------------------

    private void drawBitmaps(Canvas canvas){
        for (Hexagon hexagon: tiles) {
            Bitmap scaled = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(getResources(), hexagon.getDrawableResourceId()),
                    hexagon.getWidth(),
                    2*hexagon.getRadius(),
                    false
            );
            canvas.drawBitmap(scaled, hexagon.getCenter().getX() - hexagon.getHalfWidth(), hexagon.getCenter().getY() - hexagon.getRadius(), null);
        }
    }

    private void drawNumberTokens(Canvas canvas){
        Paint textpaint= new Paint();
        textpaint.setColor(Color.BLACK);
        textpaint.setTextAlign(Paint.Align.CENTER);
        textpaint.setTextSize(40f);
        Bitmap numberToken = getBitmap(R.drawable.tilenumbertoken);

        for (Hexagon hexagon: tiles) {
            canvas.drawBitmap(numberToken,hexagon.getCenter().getX()-50, hexagon.getCenter().getY()-50, null);
            canvas.drawText(String.valueOf(hexagon.getTileNumber()),hexagon.getCenter().getX(),hexagon.getCenter().getY()+10, textpaint);
        }

    }

//----------- Getter and Setter -------------------------------------------------------------------

    public HexGrid getHexGrid() {
        return hexGrid;
    }
}

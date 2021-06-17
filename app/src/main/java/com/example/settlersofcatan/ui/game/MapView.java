package com.example.settlersofcatan.ui.game;

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

import com.example.settlersofcatan.R;
import com.example.settlersofcatan.game.Game;
import com.example.settlersofcatan.game.board.Edge;
import com.example.settlersofcatan.game.board.Tile;
import com.example.settlersofcatan.game.resources.Resource;
import com.example.settlersofcatan.ui.board.HexGrid;
import com.example.settlersofcatan.ui.board.Hexagon;
import com.example.settlersofcatan.ui.board.Path;
import com.example.settlersofcatan.ui.board.Point;
import com.example.settlersofcatan.util.OnPostDrawListener;


/**
 * View class on which the board is drawn.
 */

public class MapView extends View {

    private final int WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
    private final int HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;

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

        drawHarbors(canvas);

        ((OnPostDrawListener) getContext()).onPostDraw();

        drawBitmaps(canvas);
        drawNumberTokens(canvas);
    }

    private void generateTiles(){
        int hexagonWidth = WIDTH / 6;
        int availableWidth = WIDTH;
        int availableHeight = HEIGHT;
        if (getWidth() != 0){
            availableWidth = getWidth();
            hexagonWidth = availableWidth / 6;
        }
        if (getHeight() != 0){
            availableHeight = getHeight();
            if (hexagonWidth >= availableHeight / 6){
                hexagonWidth = availableHeight / 6;
            }
        }
        Tile[] packedTiles = Game.getInstance().getBoard().getPackedTiles();
        for (int i = 0; i < packedTiles.length; i++) {
            tiles[i] = new Hexagon(packedTiles[i], hexagonWidth, (availableWidth - 5 * hexagonWidth) / 2, (availableHeight - 5 * hexagonWidth) / 2);
        }
    }

    private void drawHarbors(Canvas canvas){
        for (Path path : hexGrid.getPaths()){
            if (path.getEdge().getHarbor() == null){
                continue;
            }

            int offsetX = 0;
            int offsetY = 0;
            Edge edge = path.getEdge();
            Tile tile = edge.getAdjacentTiles().iterator().next();
            switch (tile.getDirectionOfEdge(edge)){
                case NORTH_EAST:
                    offsetY = -47;
                    offsetX = 30;
                    break;
                case EAST:
                    offsetX = 60;
                    break;
                case SOUTH_EAST:
                    offsetY = 47;
                    offsetX = 30;
                    break;
                case SOUTH_WEST:
                    offsetY = 47;
                    offsetX = -30;
                    break;
                case WEST:
                    offsetX = -60;
                    break;
                case NORTH_WEST:
                    offsetY = -47;
                    offsetX = -30;
                    break;
                default:
                    break;
            }
            drawHarbor(path, canvas, new Point(offsetX, offsetY));
        }
    }

    private void drawHarbor(Path path, Canvas canvas, Point offset){
        int bitmapWidth = 100;
        int halfWidth = bitmapWidth / 2;
        int shipHeight = 100;
        int resourceHeight = 115;
        float positionLeft = path.getX2().getX() + path.getDifferenceX() / 2f - (float) halfWidth + (float) offset.getX();
        float positionTop = path.getX2().getY() + path.getDifferenceY() / 2f - (float) halfWidth - 10f + (float) offset.getY();

        if (path.getEdge().getHarbor() != null){
            Resource resource = path.getEdge().getHarbor().getResource();

            if (resource != null) {
                Bitmap resourceBitmap = getBitmap(Hexagon.getResourceIdFromResource(path.getEdge().getHarbor().getResource()));
                if (resourceBitmap != null) {
                    resourceBitmap = Bitmap.createScaledBitmap(resourceBitmap, bitmapWidth, resourceHeight, false);
                    canvas.drawBitmap(resourceBitmap, positionLeft, positionTop, null);
                }
            }

            Bitmap ship = getBitmap(R.drawable.ship);
            if (ship != null) {
                ship = Bitmap.createScaledBitmap(ship, bitmapWidth, shipHeight, false);
                canvas.drawBitmap(ship, positionLeft, positionTop, null);
            }
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

package com.example.settlersofcatan;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * View class on which the cities, settlements and roads of the players are placed.
 */
public class PlayerView extends View {

    Bitmap settlement= BitmapFactory.decodeResource(getResources(),R.drawable.settlement_white);
    Bitmap city= BitmapFactory.decodeResource(getResources(),R.drawable.city_white);
    Bitmap road= BitmapFactory.decodeResource(getResources(),R.drawable.road_white);

    public PlayerView(Context context) {
        super(context);
    }

    public PlayerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}

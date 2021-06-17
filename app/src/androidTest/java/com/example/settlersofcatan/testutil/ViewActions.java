package com.example.settlersofcatan.testutil;

import android.view.InputDevice;
import android.view.MotionEvent;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.GeneralClickAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Tap;

import com.example.settlersofcatan.Point;

public class ViewActions {
    public static ViewAction clickXY(final int x, final int y){
        return new GeneralClickAction(
                Tap.SINGLE,
                view -> {
                    final int[] screenPos = new int[2];
                    view.getLocationOnScreen(screenPos);

                    final float screenX = screenPos[0] + x;
                    final float screenY = screenPos[1] + y;

                    return new float[]{screenX, screenY};
                },
                Press.FINGER,
                InputDevice.SOURCE_MOUSE,
                MotionEvent.BUTTON_PRIMARY
        );
    }

    public static ViewAction clickPoint(final Point point){
        return clickXY(point.getX(), point.getY());
    }
}

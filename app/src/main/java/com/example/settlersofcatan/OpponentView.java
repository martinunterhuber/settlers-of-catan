package com.example.settlersofcatan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class OpponentView extends FrameLayout {
    public OpponentView(@NonNull Context context) {
        super(context);
        initView();
    }

    public OpponentView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public OpponentView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.opponent_view, this);
    }


}

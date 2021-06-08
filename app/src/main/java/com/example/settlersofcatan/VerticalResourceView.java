package com.example.settlersofcatan;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class VerticalResourceView extends ResourceView {
    public VerticalResourceView(@NonNull Context context) {
        super(context);
    }

    public VerticalResourceView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public VerticalResourceView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initView() {
        inflate(getContext(), R.layout.resource_view_vertical, this);
        setWillNotDraw(false);
    }
}

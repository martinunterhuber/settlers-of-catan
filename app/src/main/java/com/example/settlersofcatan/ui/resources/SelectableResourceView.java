package com.example.settlersofcatan.ui.resources;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.settlersofcatan.R;
import com.example.settlersofcatan.game.resources.Resource;
import com.example.settlersofcatan.util.Procedure;

public class SelectableResourceView extends ResourceView {
    private final int selectedColor = getResources().getColor(R.color.selected);

    private TextView selectedText;
    private Resource selectedResource;

    public SelectableResourceView(@NonNull Context context) {
        super(context);
    }

    public SelectableResourceView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SelectableResourceView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void doOnDraw() {

    }

    public boolean hasSelected(){
        return selectedResource != null;
    }

    public Resource getSelectedResource() {
        return selectedResource;
    }

    private void listener(Resource resource, int resourceId, Procedure doAfter){
        selectedResource = resource;
        if (selectedText != null){
            selectedText.setTextColor(Color.WHITE);
        }
        selectedText = findViewById(resourceId);
        selectedText.setTextColor(selectedColor);
        doAfter.run();
    }

    public void initListeners(Procedure doAfter){
        findViewById(R.id.img_wood).setOnClickListener((v) -> {
            listener(Resource.FOREST, R.id.txt_wood, doAfter);
        });
        findViewById(R.id.img_clay).setOnClickListener((v) -> {
            listener(Resource.CLAY, R.id.txt_clay, doAfter);
        });
        findViewById(R.id.img_ore).setOnClickListener((v) -> {
            listener(Resource.ORE, R.id.txt_ore, doAfter);
        });
        findViewById(R.id.img_sheep).setOnClickListener((v) -> {
            listener(Resource.SHEEP, R.id.txt_sheep, doAfter);
        });
        findViewById(R.id.img_wheat).setOnClickListener((v) -> {
            listener(Resource.WHEAT, R.id.txt_wheat, doAfter);
        });
    }
}

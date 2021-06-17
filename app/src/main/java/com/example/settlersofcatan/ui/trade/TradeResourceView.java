package com.example.settlersofcatan.ui.trade;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.settlersofcatan.R;
import com.example.settlersofcatan.game.resources.Resource;
import com.example.settlersofcatan.game.resources.ResourceMap;

/**
 * Class to represent a set of different resources with the purpose of trading.
 * Uses the same layout and much of the code of resourceView, but tailored for use
 * in trading.
 * Not a pure view, as it stores what it is supposed to display itself.
 * Very smelly.
 */
public class TradeResourceView extends FrameLayout {

    private TextView woodTxt;
    private TextView wheatTxt;
    private TextView sheepTxt;
    private TextView clayTxt;
    private TextView oreTxt;

    private ImageView woodImg;
    private ImageView wheatImg;
    private ImageView sheepImg;
    private ImageView clayImg;
    private ImageView oreImg;

    private ResourceMap content = ResourceMap.getEmptyResourceMap();

    private boolean invisibleIfZero = true;


    public TradeResourceView(@NonNull Context context) {
        super(context);
        initView();
        initResourceTextFields();
        initResourceImageFields();
        setResourceValues();
    }

    public TradeResourceView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
        initResourceTextFields();
        initResourceImageFields();
        setResourceValues();
    }

    public TradeResourceView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initResourceTextFields();
        initResourceImageFields();
        setResourceValues();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private void initView() {
        inflate(getContext(), R.layout.resource_view, this);
        setWillNotDraw(false);
    }

    /**
     * Method to update the View with a new resource map
     * @param resourceMap the new set of resources
     */
    public void updateResourceValues(ResourceMap resourceMap) {
        content = resourceMap;
        updateResourceValues();
    }

    /**
     * Method to update the resource values, checks if any are zero and makes them invisible and makes
     * nonzero resources visible if the flag (invisibleIfZero) is raised.
     */
    public void updateResourceValues() {
        if (invisibleIfZero) {
            for (Resource resource : Resource.values()) {
                if (content.getResourceCount(resource) <= 0) {
                    makeResourceInvisible(resource);
                } else {
                    makeResourceVisible(resource);
                }
            }
        }
        setResourceValues();
    }

    public ResourceMap getContent() {
        return content;
    }


    public void setInvisibleIfZero(boolean bool) {
        invisibleIfZero = bool;
    }

    public ImageView getResourceImage(Resource resource) {
        switch (resource) {
            case FOREST:
                return woodImg;
            case WHEAT:
                return wheatImg;
            case SHEEP:
                return sheepImg;
            case CLAY:
                return clayImg;
            case ORE:
                return oreImg;
        }
        return null;
    }

    /**
     * Method to actually set the strings to the corresponding number of the resource
     */
    private void setResourceValues(){
        woodTxt.setText(String.valueOf(content.getResourceCount(Resource.FOREST)));
        wheatTxt.setText(String.valueOf(content.getResourceCount(Resource.WHEAT)));
        sheepTxt.setText(String.valueOf(content.getResourceCount(Resource.SHEEP)));
        clayTxt.setText(String.valueOf(content.getResourceCount(Resource.CLAY)));
        oreTxt.setText(String.valueOf(content.getResourceCount(Resource.ORE)));
    }

    private void makeResourceInvisible(Resource resource) {
        switch (resource) {
            case FOREST:
                woodTxt.setVisibility(INVISIBLE);
                woodImg.setVisibility(INVISIBLE);
                break;
            case WHEAT:
                wheatTxt.setVisibility(INVISIBLE);
                wheatImg.setVisibility(INVISIBLE);
                break;
            case SHEEP:
                sheepTxt.setVisibility(INVISIBLE);
                sheepImg.setVisibility(INVISIBLE);
                break;
            case CLAY:
                clayTxt.setVisibility(INVISIBLE);
                clayImg.setVisibility(INVISIBLE);
                break;
            case ORE:
                oreTxt.setVisibility(INVISIBLE);
                oreImg.setVisibility(INVISIBLE);
                break;
        }
    }


    private void makeResourceVisible(Resource resource) {
        switch (resource) {
            case FOREST:
                woodTxt.setVisibility(VISIBLE);
                woodImg.setVisibility(VISIBLE);
                break;
            case WHEAT:
                wheatTxt.setVisibility(VISIBLE);
                wheatImg.setVisibility(VISIBLE);
                break;
            case SHEEP:
                sheepTxt.setVisibility(VISIBLE);
                sheepImg.setVisibility(VISIBLE);
                break;
            case CLAY:
                clayTxt.setVisibility(VISIBLE);
                clayImg.setVisibility(VISIBLE);
                break;
            case ORE:
                oreTxt.setVisibility(VISIBLE);
                oreImg.setVisibility(VISIBLE);
                break;
        }
    }

    private void initResourceTextFields() {
        woodTxt = findViewById(R.id.txt_wood);
        wheatTxt = findViewById(R.id.txt_wheat);
        sheepTxt = findViewById(R.id.txt_sheep);
        clayTxt = findViewById(R.id.txt_clay);
        oreTxt = findViewById(R.id.txt_ore);
    }

    private void initResourceImageFields() {
        woodImg = findViewById(R.id.img_wood);
        wheatImg = findViewById(R.id.img_wheat);
        sheepImg = findViewById(R.id.img_sheep);
        clayImg = findViewById(R.id.img_clay);
        oreImg = findViewById(R.id.img_ore);
    }
}

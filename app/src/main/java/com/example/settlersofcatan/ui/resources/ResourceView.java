package com.example.settlersofcatan.ui.resources;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.settlersofcatan.R;
import com.example.settlersofcatan.game.Game;
import com.example.settlersofcatan.game.Player;
import com.example.settlersofcatan.game.resources.Resource;
import com.example.settlersofcatan.server_client.GameClient;

public class ResourceView extends FrameLayout {
    private TextView woodTxt;
    private TextView wheatTxt;
    private TextView sheepTxt;
    private TextView clayTxt;
    private TextView oreTxt;

    private boolean showAnimation = false;

    // TODO: change this animation
    private final Animation updateAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.blink);

    public ResourceView(@NonNull Context context) {
        super(context);
        initView();
        initResourceTextFields();
    }

    public ResourceView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
        initResourceTextFields();
    }

    public ResourceView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initResourceTextFields();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        doOnDraw();
    }

    protected void doOnDraw(){
        setResourceValues();
    }

    protected void initView() {
        inflate(getContext(), R.layout.resource_view, this);
        setWillNotDraw(false);
    }

    private void setResourceValues(){
        Player player = Game.getInstance().getPlayerById(GameClient.getInstance().getId());
        setResourceValuesOf(player);
        showAnimation = true;
    }

    public void setResourceValuesOf(Player player){
        setResourceValueIfHasChanged(player, woodTxt, Resource.FOREST, findViewById(R.id.img_wood));
        setResourceValueIfHasChanged(player, wheatTxt, Resource.WHEAT, findViewById(R.id.img_wheat));
        setResourceValueIfHasChanged(player, sheepTxt, Resource.SHEEP, findViewById(R.id.img_sheep));
        setResourceValueIfHasChanged(player, clayTxt, Resource.CLAY, findViewById(R.id.img_clay));
        setResourceValueIfHasChanged(player, oreTxt, Resource.ORE, findViewById(R.id.img_ore));
    }

    public void setResourceValueIfHasChanged(Player player, TextView textView, Resource resource, View imageView){
        if (!textView.getText().toString().equals(String.valueOf(player.getResourceCount(resource)))){
            textView.setText(String.valueOf(player.getResourceCount(resource)));
            if (showAnimation){
                imageView.startAnimation(updateAnimation);
            }
        }
    }

    public void setEmptyResources(){
        woodTxt.setText(String.valueOf(0));
        wheatTxt.setText(String.valueOf(0));
        sheepTxt.setText(String.valueOf(0));
        clayTxt.setText(String.valueOf(0));
        oreTxt.setText(String.valueOf(0));
    }

    private void initResourceTextFields() {
        woodTxt = findViewById(R.id.txt_wood);
        wheatTxt = findViewById(R.id.txt_wheat);
        sheepTxt = findViewById(R.id.txt_sheep);
        clayTxt = findViewById(R.id.txt_clay);
        oreTxt = findViewById(R.id.txt_ore);
    }
}

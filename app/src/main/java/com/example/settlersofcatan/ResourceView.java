package com.example.settlersofcatan;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.settlersofcatan.game.Game;
import com.example.settlersofcatan.game.Player;
import com.example.settlersofcatan.game.Resource;
import com.example.settlersofcatan.game.ResourceMap;
import com.example.settlersofcatan.server_client.GameClient;

public class ResourceView extends FrameLayout {
    private TextView woodTxt;
    private TextView wheatTxt;
    private TextView sheepTxt;
    private TextView clayTxt;
    private TextView oreTxt;

    private Player player;

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
        setResourceValues();
    }

    private void initView() {
        inflate(getContext(), R.layout.resource_view, this);
        setWillNotDraw(false);
        player = Game.getInstance().getPlayerById(GameClient.getInstance().getId());
    }

    private void setResourceValues(){
        woodTxt.setText(String.valueOf(player.getResourceCount(Resource.FOREST)));
        wheatTxt.setText(String.valueOf(player.getResourceCount(Resource.WHEAT)));
        sheepTxt.setText(String.valueOf(player.getResourceCount(Resource.SHEEP)));
        clayTxt.setText(String.valueOf(player.getResourceCount(Resource.CLAY)));
        oreTxt.setText(String.valueOf(player.getResourceCount(Resource.ORE)));
    }

    private void initResourceTextFields() {
        woodTxt = findViewById(R.id.txt_wood);
        wheatTxt = findViewById(R.id.txt_wheat);
        sheepTxt = findViewById(R.id.txt_sheep);
        clayTxt = findViewById(R.id.txt_clay);
        oreTxt = findViewById(R.id.txt_ore);
    }
}

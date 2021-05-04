package com.example.settlersofcatan;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ResourceView extends FrameLayout {
    private int wood=0;
    private int wheat=0;
    private int sheep=0;
    private int clay=0;
    private int ore=0;

    private final TextView woodTxt;
    private final TextView wheatTxt;
    private final TextView sheepTxt;
    private final TextView clayTxt;
    private final TextView oreTxt;

    public ResourceView(@NonNull Context context) {
        super(context);

        initView();

        woodTxt=findViewById(R.id.txt_wood);
        woodTxt.setText(String.valueOf(wood));

        wheatTxt=findViewById(R.id.txt_wheat);
        wheatTxt.setText(String.valueOf(wheat));

        sheepTxt=findViewById(R.id.txt_sheep);
        sheepTxt.setText(String.valueOf(sheep));

        clayTxt=findViewById(R.id.txt_clay);
        clayTxt.setText(String.valueOf(clay));

        oreTxt=findViewById(R.id.txt_ore);
        oreTxt.setText(String.valueOf(ore));
    }

    public ResourceView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initView();

        woodTxt=findViewById(R.id.txt_wood);
        woodTxt.setText(String.valueOf(wood));

        wheatTxt=findViewById(R.id.txt_wheat);
        wheatTxt.setText(String.valueOf(wheat));

        sheepTxt=findViewById(R.id.txt_sheep);
        sheepTxt.setText(String.valueOf(sheep));

        clayTxt=findViewById(R.id.txt_clay);
        clayTxt.setText(String.valueOf(clay));

        oreTxt=findViewById(R.id.txt_ore);
        oreTxt.setText(String.valueOf(ore));
    }

    public ResourceView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();

        woodTxt=findViewById(R.id.txt_wood);
        woodTxt.setText(String.valueOf(wood));

        wheatTxt=findViewById(R.id.txt_wheat);
        wheatTxt.setText(String.valueOf(wheat));

        sheepTxt=findViewById(R.id.txt_sheep);
        sheepTxt.setText(String.valueOf(sheep));

        clayTxt=findViewById(R.id.txt_clay);
        clayTxt.setText(String.valueOf(clay));

        oreTxt=findViewById(R.id.txt_ore);
        oreTxt.setText(String.valueOf(ore));

    }

    private void initView() {

        inflate(getContext(), R.layout.resource_view, this);
    }

//----------- Getter and Setter --------------------------------------------------------------------------------

    public int getWood() {
        return wood;
    }

    public void setWood(int wood) {
        this.wood = wood;
    }

    public int getWheat() {
        return wheat;
    }

    public void setWheat(int wheat) {
        this.wheat = wheat;
    }

    public int getSheep() {
        return sheep;
    }

    public void setSheep(int sheep) {
        this.sheep = sheep;
    }

    public int getClay() {
        return clay;
    }

    public void setClay(int clay) {
        this.clay = clay;
    }

    public int getOre() {
        return ore;
    }

    public void setOre(int ore) {
        this.ore = ore;
    }
}

package com.example.settlersofcatan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResourceRankingActivity extends AppCompatActivity {

    private TextView clayt;
    private TextView woodt;
    private TextView oret;
    private TextView shept;
    private TextView wheatt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources_ranking);

        Button sbtn = (Button)findViewById(R.id.btn_back);
        sbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResourceRankingActivity.this,GameEndActivity.class));
            }
        });
        //when the Button in GameEndActivity gets pressed, it will start counting the numbers
        Button rbtn = (Button)findViewById(R.id.resranbtn);
        rbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                woodt = findViewById(R.id.woodtxt);
                clayt = findViewById(R.id.claytxt);
                oret = findViewById(R.id.oretxt);
                shept = findViewById(R.id.sheeptxt);
                wheatt = findViewById(R.id.wheattxt);
            }
        });
    }
}
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
        sbtn.setOnClickListener(v -> finish());
    }
}
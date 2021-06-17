package com.example.settlersofcatan.ui.ranking;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.settlersofcatan.R;

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
package com.example.settlersofcatan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        Button sbtn = (Button)findViewById(R.id.startscreen_button);
        sbtn.setOnClickListener(v -> {
            startActivity(new Intent(this ,MainActivity.class));
            this.finish();
        });
    }
}
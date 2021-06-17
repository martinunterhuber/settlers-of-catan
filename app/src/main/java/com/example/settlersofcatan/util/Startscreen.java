package com.example.settlersofcatan.util;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.settlersofcatan.MainActivity;
import com.example.settlersofcatan.R;

public class Startscreen extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        Button sbtn = (Button)findViewById(R.id.startscreen_button);
        sbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Startscreen.this,MainActivity.class));
            }
        });


    }
}
package com.example.settlersofcatan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.example.settlersofcatan.game.Player;
import com.example.settlersofcatan.game.Resource;
import com.example.settlersofcatan.server_client.ServerFragmentStateAdapter;
import com.example.settlersofcatan.server_client.WaitForHostActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private TextView countertxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        ServerFragmentStateAdapter adapter = new ServerFragmentStateAdapter(this);
        ViewPager2 viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position){
                        case 0: tab.setText(R.string.create_server); break;
                        case 1: tab.setText(R.string.join_server); break;
                        default: throw new IllegalArgumentException();
                    }
                }
        ).attach();
        super.onPostCreate(savedInstanceState);
    }
}
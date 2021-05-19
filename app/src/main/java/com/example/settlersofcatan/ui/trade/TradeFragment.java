package com.example.settlersofcatan.ui.trade;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.settlersofcatan.R;

/**
 * Fragment to facilitate trading with players, uses a nested ExchangeFragment.
 * Work in progress
 */
public class TradeFragment extends Fragment {


    public static TradeFragment newInstance() {
        return new TradeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.trade_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
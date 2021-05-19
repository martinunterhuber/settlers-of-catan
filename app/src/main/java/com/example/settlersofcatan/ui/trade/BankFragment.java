package com.example.settlersofcatan.ui.trade;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.settlersofcatan.R;
import com.example.settlersofcatan.game.Game;
import com.example.settlersofcatan.server_client.GameClient;

/**
 * Fragment to facilitate trading with the bank/harbors, has a nested BankTradeFragment and a button to complete trade.
 */
public class BankFragment extends Fragment {

    BankTradeFragment bankTradeFragment;

    Button completeTrade;


    public static BankFragment newInstance() {
        return new BankFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bank_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bankTradeFragment = (BankTradeFragment) getChildFragmentManager().findFragmentById(R.id.fragment);
        completeTrade = getView().findViewById(R.id.completeTradeBtn);

        completeTrade.setOnClickListener(v -> executeTrade());
    }

    private void executeTrade() {
        if (bankTradeFragment.isInAcceptedState()) {
            Game.getInstance().getPlayerById(GameClient.getInstance().getId()).setResources(bankTradeFragment.getCurrentInventoryView().getContent());

            getActivity().finish();
        } else {
            Animation shake = AnimationUtils.loadAnimation(completeTrade.getContext(), R.anim.shake);
            completeTrade.startAnimation(shake);
        }
    }

}
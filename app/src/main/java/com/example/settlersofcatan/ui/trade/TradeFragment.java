package com.example.settlersofcatan.ui.trade;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.settlersofcatan.R;
import com.example.settlersofcatan.game.Game;
import com.example.settlersofcatan.game.Player;
import com.example.settlersofcatan.game.trade.TradeOffer;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Fragment to facilitate trading with players, uses a nested ExchangeFragment.
 * Work in progress
 */
public class TradeFragment extends Fragment {


    ExchangeFragment exchangeFragment;
    Button toFirstBtn;
    Button toSecondBtn;
    Button toThirdBtn;
    ArrayList<Player> otherPlayers;
    Player currentPlayer;


    public static TradeFragment newInstance() {
        return new TradeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.trade_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        exchangeFragment = (ExchangeFragment) getChildFragmentManager().findFragmentById(R.id.exchangeFragment);

        toFirstBtn = getView().findViewById(R.id.toFirstBtn);
        toSecondBtn = getView().findViewById(R.id.toSecondBtn);
        toThirdBtn = getView().findViewById(R.id.toThirdBtn);
        toFirstBtn.setVisibility(View.GONE);
        toSecondBtn.setVisibility(View.GONE);
        toThirdBtn.setVisibility(View.GONE);

        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(toFirstBtn);
        buttons.add(toSecondBtn);
        buttons.add(toThirdBtn);

        Game game = Game.getInstance();
        currentPlayer = game.getPlayerById(game.getCurrentPlayerId());

        otherPlayers = new ArrayList<>(game.getPlayers());
        otherPlayers.remove(currentPlayer);
        for (int i = 0; i < otherPlayers.size(); i++) {
            Player player = otherPlayers.get(i);
            String name = player.getName();

            buttons.get(i).setText(name.length() > 5 ? name.substring(0, 5) : name);
            buttons.get(i).setVisibility(View.VISIBLE);
            buttons.get(i).setOnClickListener(v -> makeTradeOffer(player));
        }

    }

    private void makeTradeOffer(Player player) {
        TradeOffer tradeOffer = new TradeOffer(currentPlayer, player);
        tradeOffer.setGive(exchangeFragment.getGiveInventoryView().getContent());
        tradeOffer.setReceive(exchangeFragment.getReceiveInventoryView().getContent());
        Game.getInstance().sendTradeOffer(tradeOffer);
        ((TradeActivity) getActivity()).waitForReply(tradeOffer);
    }

}
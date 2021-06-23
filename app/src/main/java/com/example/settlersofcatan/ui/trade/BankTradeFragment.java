package com.example.settlersofcatan.ui.trade;

import android.view.DragEvent;

/**
 * Child of ExchangeFragment to specifically facilitate trading with the bank/harbors.
 * That means considering the resource exchange rates, and only being allowed to
 * receive resources if an equivalent was first given.
 */
public class BankTradeFragment extends ExchangeFragment{

    @Override
    protected void initBaseVariables() {
        super.initBaseVariables();
        resourceExchangeRates = currentPlayer.getResourceExchangeRates();
    }

    @Override
    protected void validateTransfer(TradeResourceView target, DragEvent dragEvent) {
        TradeResourceView source = (TradeResourceView) dragEvent.getLocalState();
        if (target == receiveInventoryView
                && (((source == currentInventoryView && giveCounter <= receiveCounter)
                || (source == giveInventoryView && giveCounter - 1 <= receiveCounter)))) {
            return;
        }
        executeTransfer(target, source);
    }

    @Override
    public boolean isInAcceptedState() {
        return giveCounter == receiveCounter;
    }
}

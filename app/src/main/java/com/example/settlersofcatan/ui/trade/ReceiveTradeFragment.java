package com.example.settlersofcatan.ui.trade;

import android.view.DragEvent;

import com.example.settlersofcatan.game.TradeOffer;

public class ReceiveTradeFragment extends ExchangeFragment {

    @Override
    protected void initBaseVariables() {
        super.initBaseVariables();
        resourceExchangeRates = currentPlayer.getResourceExchangeRates();
    }



    public void setResourceViews(TradeOffer tradeOffer) {
        currentInventoryView.getContent().decrementResourceMap(tradeOffer.getReceive());
        currentInventoryView.getContent().incrementResourceMap(tradeOffer.getGive());
        receiveInventoryView.getContent().incrementResourceMap(tradeOffer.getGive());
        giveInventoryView.getContent().incrementResourceMap(tradeOffer.getReceive());
        giveInventoryView.updateResourceValues();
        receiveInventoryView.updateResourceValues();
        currentInventoryView.updateResourceValues();
    }

    public boolean wasChanged() {
        return giveCounter != 0 || receiveCounter != 0;
    }

}

package com.example.settlersofcatan.ui.trade;

import com.example.settlersofcatan.game.trade.TradeOffer;

public class ReceiveTradeFragment extends ExchangeFragment {

    public void setResourceViews(TradeOffer tradeOffer) {
        currentInventoryView.getContent().decrementResourceMap(tradeOffer.getReceive());
        currentInventoryView.getContent().incrementResourceMap(tradeOffer.getGive());
        receiveInventoryView.getContent().incrementResourceMap(tradeOffer.getGive());
        giveInventoryView.getContent().incrementResourceMap(tradeOffer.getReceive());
        giveInventoryView.updateResourceValues();
        receiveInventoryView.updateResourceValues();
        currentInventoryView.updateResourceValues();
    }


}

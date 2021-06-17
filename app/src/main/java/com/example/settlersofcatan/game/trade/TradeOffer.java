package com.example.settlersofcatan.game.trade;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.settlersofcatan.game.Game;
import com.example.settlersofcatan.game.Player;
import com.example.settlersofcatan.game.resources.Resource;
import com.example.settlersofcatan.game.resources.ResourceMap;

public class TradeOffer implements Parcelable {
    private Player from;
    private Player to;

    private ResourceMap receive; // what the player making the trade offer wants to receive
    private ResourceMap give; // what the player making the trade offer is willing to give

    public TradeOffer(){}

    public TradeOffer(Player from, Player to) {
        this.from = from;
        this.to = to;
    }

    protected TradeOffer(Parcel in) {
        receive = new ResourceMap(in.createIntArray());
        give = new ResourceMap(in.createIntArray());
        to = Game.getInstance().getPlayerById(in.readInt());
        from = Game.getInstance().getPlayerById(in.readInt());
    }

    public static final Creator<TradeOffer> CREATOR = new Creator<TradeOffer>() {
        @Override
        public TradeOffer createFromParcel(Parcel in) {
            return new TradeOffer(in);
        }

        @Override
        public TradeOffer[] newArray(int size) {
            return new TradeOffer[size];
        }
    };

    public void addResourceReceive(Resource resource, int number) {
        receive.incrementResourceCount(resource, number);
    }

    public void addResourceGive(Resource resource, int number) {
        give.incrementResourceCount(resource, number);
    }

    public void removeResourceReceive(Resource resource, int number) {
        receive.decrementResourceCount(resource, number);
    }

    public void removeResourceGive(Resource resource, int number) {
        give.decrementResourceCount(resource, number);
    }


    public Player getFrom() {
        return from;
    }

    public Player getTo() {
        return to;
    }

    public ResourceMap getReceive() {
        return receive;
    }

    public ResourceMap getGive() {
        return give;
    }

    public void setGive(ResourceMap give) {
        this.give = give;
    }

    public void setReceive(ResourceMap receive) {
        this.receive = receive;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(receive.toArray());
        dest.writeIntArray(give.toArray());
        dest.writeInt(to.getId());
        dest.writeInt(from.getId());
    }

}

package com.example.settlersofcatan.ui.trade;

import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.settlersofcatan.R;
import com.example.settlersofcatan.game.Game;
import com.example.settlersofcatan.game.Player;
import com.example.settlersofcatan.game.resources.Resource;
import com.example.settlersofcatan.game.resources.ResourceMap;
import com.example.settlersofcatan.server_client.GameClient;

/**
 * Fragment to facilitate any exchange of resources where player input on the selection of resources is
 * required. Consists of three TradeResourceView representing what resources are available, what can be given
 * and what can be received. Resources are moved between these views via drag and drop.
 * This class in itself can represent the framework for a normal trade offer, but can be a
 * superclass for more specific resource exchanges (e.g. trade with bank/harbor, Year of Plenty development
 * card resource selection, being robbed). These should only have to overwrite the initBaseVariables, validateTransfer and
 * isAcceptedState methods, as the functionality in the other methods should be shared.
 */
public class ExchangeFragment extends Fragment {

    protected TradeResourceView currentInventoryView;
    protected TradeResourceView giveInventoryView;
    protected TradeResourceView receiveInventoryView;

    protected TextView receiveText;
    protected TextView giveText;

    protected Player currentPlayer;

    protected Resource resourceBeingDragged;
    /**
     * Base value of exchange is one for all.
     */
    protected int[] resourceExchangeRates = {1, 1, 1, 1, 1};

    /**
     * The give counter counts how many sets of resources (sets to a number of a resource at its exchange rate)  are in the giveInventoryView.
     */
    protected int giveCounter;
    /**
     * The give counter counts how many resources are in the receiveInventoryView.
     */
    protected int receiveCounter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exchange, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initBaseVariables();
        initDrag();
    }

    public TradeResourceView getCurrentInventoryView() {
        return currentInventoryView;
    }

    public TradeResourceView getGiveInventoryView() {
        return giveInventoryView;
    }

    public TradeResourceView getReceiveInventoryView() {
        return receiveInventoryView;
    }

    protected void initBaseVariables() {

        giveCounter = 0;
        receiveCounter = 0;

        currentInventoryView = getView().findViewById(R.id.tradeResourceView2);
        giveInventoryView = getView().findViewById(R.id.tradeResourceView1);
        receiveInventoryView = getView().findViewById(R.id.tradeResourceView3);

        receiveText = getView().findViewById(R.id.receiveTextView);
        giveText = getView().findViewById(R.id.giveTextView);

        receiveText.setText("I receive:");
        giveText.setText("I give:");

        //TradeResourceView makes the image of a resource invisible if it hits zero, but for
        //the resources the player has they should not be
        currentInventoryView.setInvisibleIfZero(false);

        giveInventoryView.updateResourceValues();
        receiveInventoryView.updateResourceValues();

        currentPlayer = Game.getInstance().getPlayerById(GameClient.getInstance().getId());

        currentInventoryView.updateResourceValues(ResourceMap.cloneResourceMap(currentPlayer.getResources()));

    }

    /**
     * Method to initialize drag functionality for all resource images in all TradeResourceViews
     * representing the corresponding resource), and drop functionality for the TradeResourceViews
     * in which they should be dropped. The Resource being dragged is stored in the class variable
     * resourceBeingDragged, and the (TradeResourceView) source of the resource being dragged is stored
     * in the DragEvent. This way the transfer can be facilitated.
     */
    protected void initDrag() {
        for (Resource resource : Resource.values()) {
            ImageView currentImg = currentInventoryView.getResourceImage(resource);
            currentImg.setOnLongClickListener(v -> {
                ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
                resourceBeingDragged = resource;
                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};

                ClipData dragData = new ClipData("", mimeTypes, item);
                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(currentImg);

                v.startDrag(dragData, myShadow, currentInventoryView, 0);
                return true;
            });

            ImageView giveImg = giveInventoryView.getResourceImage(resource);
            giveImg.setOnLongClickListener(v -> {
                ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
                resourceBeingDragged = resource;
                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};

                ClipData dragData = new ClipData("", mimeTypes, item);
                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(giveImg);

                v.startDrag(dragData, myShadow, giveInventoryView, 0);
                return true;
            });

            ImageView receiveImg = receiveInventoryView.getResourceImage(resource);
            receiveImg.setOnLongClickListener(v -> {
                ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
                resourceBeingDragged = resource;
                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};

                ClipData dragData = new ClipData("", mimeTypes, item);
                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(receiveImg);
                v.startDrag(dragData, myShadow, receiveInventoryView, 0);
                return true;
            });
        }

        currentInventoryView.setOnDragListener((v, event) -> {
            if (event.getAction() == DragEvent.ACTION_DROP) {
                validateTransfer(currentInventoryView, event);
            }
            return true;
        });

        receiveInventoryView.setOnDragListener((v, event) -> {
            if (event.getAction() == DragEvent.ACTION_DROP) {
                validateTransfer(receiveInventoryView, event);
            }
            return true;
        });

        giveInventoryView.setOnDragListener((v, event) -> {
            if (event.getAction() == DragEvent.ACTION_DROP) {
                validateTransfer(giveInventoryView, event);
            }
            return true;
        });


    }

    /**
     * This method validates that a transfer which would be possible is allowed (in contrast to the
     * transfer being intrinsically impossible). In normal trade all transfers are allowed, so here nothing
     * is done, but child classes may overwrite this.
     * @param target The target of the transfer
     * @param dragEvent the dragEvent of the drag, contains the source (TradeSourceView) of the transfer
     */
    protected void validateTransfer(TradeResourceView target, DragEvent dragEvent) {
        TradeResourceView source = (TradeResourceView) dragEvent.getLocalState();
        executeTransfer(target, source);
    }

    /**
     * Method to execute a transfer of a resource between TradeResourceViews, absolutely horrible code (but works (for now)),
     * (hopefully) subject to change.
     * Horribleness can be explained by the resource transfer being different
     * depending on the source and the target of the transfer.
     * Also prevents impossible transfers, e.g. you cant give a resource you do not have.
     * Resource exchange rates are also respected here, although this is only relevant for
     * the child BankTradeFragment.
     * @param target Where the resource comes from
     * @param source Where the resource should go
     */
    protected void executeTransfer(TradeResourceView target,TradeResourceView source) {
        Resource resource = resourceBeingDragged;
        int exchangeRate = resourceExchangeRates[resource.getIndex()];
        if (source == currentInventoryView) {
            if (target == giveInventoryView) {
                if (source.getContent().getResourceCount(resource) >= exchangeRate) {
                    giveCounter++;
                    source.getContent().decrementResourceCount(resource, exchangeRate);
                    target.getContent().incrementResourceCount(resource, exchangeRate);
                }
            }
            else if (target == receiveInventoryView) {
                receiveCounter++;
                source.getContent().incrementResourceCount(resource, 1);
                target.getContent().incrementResourceCount(resource, 1);
            }
        }
        else if (source == giveInventoryView){
            if (target == currentInventoryView) {
                giveCounter--;
                source.getContent().decrementResourceCount(resource, exchangeRate);
                target.getContent().incrementResourceCount(resource, exchangeRate);
            }
            else if (target == receiveInventoryView){
                giveCounter--;
                receiveCounter++;
                source.getContent().decrementResourceCount(resource, exchangeRate);
                currentInventoryView.getContent().incrementResourceCount(resource, exchangeRate + 1);
                target.getContent().incrementResourceCount(resource, 1);
            }
        }
        else {
            if (target == currentInventoryView) {
                if (currentInventoryView.getContent().getResourceCount(resource) > 0) {
                    receiveCounter--;
                    source.getContent().decrementResourceCount(resource, 1);
                    target.getContent().decrementResourceCount(resource, 1);
                }
            }
            else if ((target == giveInventoryView)
                    && (currentInventoryView.getContent().getResourceCount(resource) >= exchangeRate + 1)) {
                receiveCounter--;
                giveCounter++;
                source.getContent().decrementResourceCount(resource, 1);
                currentInventoryView.getContent().decrementResourceCount(resource, exchangeRate + 1);
                target.getContent().incrementResourceCount(resource, exchangeRate);
            }
        }
        giveInventoryView.updateResourceValues();
        receiveInventoryView.updateResourceValues();
        currentInventoryView.updateResourceValues();
    }

    public boolean isInAcceptedState() {
        return true;
    }
}
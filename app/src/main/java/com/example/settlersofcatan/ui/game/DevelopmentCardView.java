package com.example.settlersofcatan.ui.game;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.settlersofcatan.R;
import com.example.settlersofcatan.game.Game;
import com.example.settlersofcatan.server_client.GameClient;

/**
 * View that contains the title and description of a development card
 * and shows how many cards the player owns. By clicking on [Play], a card is played.
 * The view is located in a NavigationView in GameActivity.
 */
public class DevelopmentCardView extends FrameLayout {
    private TextView title;
    private TextView description;
    private TextView count;
    private Button button;

    public DevelopmentCardView(Context context) {
        super(context);
        initView();
    }

    public DevelopmentCardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DevelopmentCardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        updateView(Integer.parseInt(this.getTag().toString())-1);
    }

    private void initView() {
        inflate(getContext(), R.layout.development_cards_view, this);

        title=findViewById(R.id.txt_title);
        description=findViewById(R.id.txt_description);
        count=findViewById(R.id.txt_count_number);
        button=findViewById(R.id.btn_card_play);

        button.setOnClickListener(view -> {

            if (Integer.parseInt(count.getText().toString()) > 0) {
                Game.getInstance().playDevelopmentCard(GameClient.getInstance().getId(),
                                                        Integer.parseInt(this.getTag().toString()));
                updateView(Integer.parseInt(this.getTag().toString())-1);

            }
        });

        initDevelopmentCard();
    }

    public void initDevelopmentCard() {
        switch (this.getTag().toString()){
            case "1":
                title.setText(R.string.development_knights);
                description.setText(R.string.knights_description);
                break;
            case "2":
                title.setText(R.string.development_victory_points);
                description.setText(R.string.victory_point_description);
                button.setVisibility(GONE);
                break;
            case "3":
                title.setText(R.string.development_monopoly);
                description.setText(R.string.monopoly_description);
                break;
            case "4":
                title.setText(R.string.development_road_building);
                description.setText(R.string.road_building_description);
                break;
            case "5":
                title.setText(R.string.development_year_of_plenty);
                description.setText(R.string.year_of_plenty_description);
                break;
            default:
                break;

        }

        updateView(Integer.parseInt(this.getTag().toString())-1);

    }

    public void updateView(int type){
        int cardCount = Game.getInstance()
                .getPlayerById(GameClient.getInstance().getId())
                .getDevelopmentCardCount(type);

        this.count.setText(String.valueOf(cardCount));
    }


}

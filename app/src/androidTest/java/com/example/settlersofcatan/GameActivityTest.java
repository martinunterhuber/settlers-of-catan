package com.example.settlersofcatan;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.settlersofcatan.game.Game;
import com.example.settlersofcatan.game.Tile;
import com.example.settlersofcatan.server_client.GameClient;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.example.settlersofcatan.testutil.ViewActions.clickPoint;

@RunWith(AndroidJUnit4.class)
public class GameActivityTest {
    ActivityScenario<GameActivity> gameActivityScenario;
    GameActivity activity;

    @Before
    public void setUp(){
        GameClient.getInstance();
        ArrayList<String> players = new ArrayList<>();
        players.add("test");
        Game.getInstance().init(players);
        gameActivityScenario = ActivityScenario.launch(GameActivity.class);
        gameActivityScenario.onActivity(activity -> this.activity = activity);
    }

    @After
    public void tearDown(){
        gameActivityScenario.close();
    }

    @Test
    public void testSelectTileOnPlayerView() {
//        PlayerView playerView = ((PlayerView)activity.findViewById(R.id.playerView));
//        Hexagon hexagon = playerView.getHexGrid().getTiles()[0];
//        onView(withId(R.id.playerView)).perform(clickPoint(hexagon.getCenter()));
//        Tile tile = playerView.getSelectedTile();
//        Assert.assertNotNull(tile);
//        Assert.assertEquals(hexagon.getTile(), tile);
    }
}

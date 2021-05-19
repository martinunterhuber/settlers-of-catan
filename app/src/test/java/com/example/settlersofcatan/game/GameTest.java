package com.example.settlersofcatan.game;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class GameTest {
    private Game game;
    private Tile[][] tiles;

    @Before
    public void init() {
        Game.setInstance(null);
        game = Game.getInstance();
        game.setClientCallback((message) -> {});
        ArrayList<String> players = new ArrayList<>();
        players.add("Player1");
        players.add("Player2");
        players.add("Player3");
        players.add("Player4");
        game.init(players);
        tiles = game.getBoard().getTiles();
    }

    @Test
    public void testInitialPlacementOfSettlement(){
        game.buildSettlement(tiles[2][2].getNorthNode(), 0);

        Assert.assertNotNull(tiles[2][2].getNorthNode().getBuilding());
        Assert.assertEquals(tiles[2][2].getNorthNode().getBuilding().getPlayer().getId(), 0);
    }

    @Test
    public void testInitialPlacementTooCloseToOtherPlayer(){
        game.buildSettlement(tiles[2][2].getNorthNode(), 0);
        game.endTurn(0);
        game.buildSettlement(tiles[2][2].getNortheastNode(), 1);

        Assert.assertNull(tiles[2][2].getNortheastNode().getBuilding());
    }

    @Test
    public void testInitialPlacementOfRoad(){
        game.buildSettlement(tiles[2][2].getNorthNode(), 0);
        game.buildRoad(tiles[2][2].getNortheastEdge(), 0);

        Assert.assertNotNull(tiles[2][2].getNortheastEdge().getRoad());
        Assert.assertEquals(tiles[2][2].getNortheastEdge().getRoad().getPlayer().getId(), 0);
    }

    @Test
    public void testInitialPlacementOfRoadBeforeSettlement(){
        game.buildRoad(tiles[2][2].getNortheastEdge(), 0);
        game.buildSettlement(tiles[2][2].getNorthNode(), 0);

        Assert.assertNull(tiles[2][2].getNortheastEdge().getRoad());
    }

    @Test
    public void testInitialPlacementOfRoadOutsideRange(){
        game.buildSettlement(tiles[2][2].getNorthNode(), 0);
        game.buildRoad(tiles[3][3].getNortheastEdge(), 0);

        Assert.assertNull(tiles[3][3].getNortheastNode().getBuilding());
    }

    @Test
    public void testInitialPlacementOfSettlementOtherPlayers(){
        game.buildSettlement(tiles[2][2].getNorthNode(), 0);
        game.buildRoad(tiles[2][2].getNortheastEdge(), 0);
        game.endTurn(0);
        game.buildSettlement(tiles[2][2].getSoutheastNode(), 1);

        Assert.assertNotNull(tiles[2][2].getSoutheastNode().getBuilding());
        Assert.assertEquals(tiles[2][2].getNortheastEdge().getRoad().getPlayer().getId(), 0);
    }

    @Test
    public void testEndTurn(){
        game.buildSettlement(tiles[2][2].getNorthNode(), 0);
        game.buildRoad(tiles[2][2].getNortheastEdge(), 0);
        game.endTurn(0);
        Assert.assertEquals(game.getCurrentPlayerId(), 1);
    }

    @Test
    public void testEndTurnWithoutPlacingSettlement(){
        game.endTurn(0);
        Assert.assertEquals(game.getCurrentPlayerId(), 0);
    }

    private void doBuildRound(int q, int r, int player){
        game.buildSettlement(tiles[q][r].getNorthNode(), player);
        game.buildRoad(tiles[q][r].getNortheastEdge(), player);
        game.endTurn(player);
    }

    @Test
    public void testOrderForInitialBuildingRounds(){
        doBuildRound(2, 0, 0);
        Assert.assertEquals(game.getCurrentPlayerId(), 1);

        doBuildRound(2, 1, 1);
        Assert.assertEquals(game.getCurrentPlayerId(), 2);

        doBuildRound(2, 2, 2);
        Assert.assertEquals(game.getCurrentPlayerId(), 3);

        doBuildRound(2, 3, 3);
        Assert.assertEquals(game.getCurrentPlayerId(), 3);

        doBuildRound(3, 0, 3);
        Assert.assertEquals(game.getCurrentPlayerId(), 2);

        doBuildRound(3, 1, 2);
        Assert.assertEquals(game.getCurrentPlayerId(), 1);

        doBuildRound(3, 2, 1);
        Assert.assertEquals(game.getCurrentPlayerId(), 0);

        doBuildRound(3, 3, 0);
        Assert.assertEquals(game.getCurrentPlayerId(), 0);
    }

    private void skipBuildingPhase(){
        doBuildRound(2, 0, 0);
        doBuildRound(2, 1, 1);
        doBuildRound(2, 2, 2);
        doBuildRound(2, 3, 3);
        doBuildRound(3, 0, 3);
        doBuildRound(3, 1, 2);
        doBuildRound(3, 2, 1);
        doBuildRound(3, 3, 0);
    }

    @Test
    public void testEndTurnBeforeRollingTheDice() {
        skipBuildingPhase();
        game.endTurn(0);
        Assert.assertEquals(game.getCurrentPlayerId(), 0);
    }

    @Test
    public void testEndTurnAfterRollingTheDice() {
        skipBuildingPhase();
        int dice = game.rollDice(0);
        game.endTurn(0);
        Assert.assertTrue(dice == 7 || game.getCurrentPlayerId() == 1);
    }

    private int rollUntilRobber(){
        int player = 0;
        int dice = game.rollDice(player);
        while (dice != 7) {
            game.endTurn(player);
            player = (player + 1) % 4;
            dice = game.rollDice(player);
        }
        return player;
    }

    @Test
    public void testEndTurnBeforeDoingRobber() {
        skipBuildingPhase();
        int player = rollUntilRobber();
        game.endTurn(player);
        Assert.assertEquals(player, game.getCurrentPlayerId());
    }

    @Test
    public void testRobberWithoutTakingResource() {
        skipBuildingPhase();
        int player = rollUntilRobber();
        game.moveRobber(tiles[3][3], null, player, -1);
        Assert.assertTrue(tiles[3][3].hasRobber());
    }
}

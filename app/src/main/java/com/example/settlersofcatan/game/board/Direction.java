package com.example.settlersofcatan.game.board;

public enum Direction {
    NORTH(0), NORTH_EAST(1), EAST(2), SOUTH_EAST(3),
    SOUTH(4), SOUTH_WEST(5), WEST(6), NORTH_WEST(7);

    private final int index;

    Direction(int index){
        this.index = index;
    }

    public static Direction[] edgeValues(){
        return new Direction[]{NORTH_EAST, EAST, SOUTH_EAST, SOUTH_WEST, WEST, NORTH_WEST};
    }

    public static Direction[] nodeValues(){
        return new Direction[]{NORTH, NORTH_EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, NORTH_WEST};
    }

    public static Direction valueOf(int index){
        for (Direction direction : Direction.values()) {
            if (index == direction.index){
                return direction;
            }
        }
        throw new IllegalArgumentException("Direction does not exist!");
    }
}

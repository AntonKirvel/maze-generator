package com.antonkirvel.maze.model;

import java.util.Arrays;

public enum DIRECTION {
    UP(0, 0, -1),
    RIGHT(1, 1, 0),
    DOWN(2, 0, 1),
    LEFT(3, -1, 0);

    private int index;
    private int moveX;
    private int moveY;

    DIRECTION(int index, int moveX, int moveY) {
        this.index = index;
        this.moveX = moveX;
        this.moveY = moveY;
    }

    public int getMoveX() {
        return moveX;
    }

    public int getMoveY() {
        return moveY;
    }

    public int getIndex() {
        return index;
    }

    public static DIRECTION fromIndex(int index) {
        return Arrays.stream(DIRECTION.values()).filter(i -> i.index == index).findFirst().orElse(null);
    }

    public DIRECTION oppositeDirection() {
        return DIRECTION.fromIndex((this.index + 2) % 4);
    }
}

package com.antonkirvel.maze.model;

public class Maze {

    // ! Martrix's row index represent X coordinate and column index represent Y coordinate
    private Cell[][] cells;
    private int startX;
    private int startY;

    // Create new maze with all bordered cells and with 1 entrance
    public Maze(int width, int height, int startX, int startY, DIRECTION entrance) {
        cells = new Cell[width][height];
        if (startX != 0 && startY != 0) {
            throw new IllegalArgumentException("We always have entrance on top or left side of the maze");
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells[i][j] = Cell.of(i, j).withBorder(DIRECTION.UP, DIRECTION.DOWN, DIRECTION.LEFT, DIRECTION.RIGHT);
            }
        }
        cells[startX][startY].removeBorder(entrance);
        this.startX = startX;
        this.startY = startY;
    }

    public int getWidth() {
        return cells.length;
    }

    public int getHeight() {
        return cells[0].length;
    }

    public Cell getCell(int x, int y) {
        if (x < 0 || x >= cells.length) {
            throw new IllegalArgumentException("Horizontal index is out of maze bounds");
        }
        if (y < 0 || y >= cells[0].length) {
            throw new IllegalArgumentException("Vertical index is out of maze bounds");
        }
        return cells[x][y];
    }

    public Cell getStartingCell() {
        return cells[startX][startY];
    }
}

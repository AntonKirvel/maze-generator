package com.antonkirvel.maze.model;

import java.util.Optional;

/**
 * Wrapper around 2-dimension matrix. Stores surrounding cells, main maze matrix cells are shifted (+1,+1)
 */
public class CellField {
    private int width;
    private int height;
    private Cell[][] cells;

    public CellField(int width, int height) {
        this.width = width;
        this.height = height;
        cells = new Cell[width + 2][height + 2];
    }

    public Cell at(int x, int y) {
        if (x < -1 || x > width) {
            throw new ArrayIndexOutOfBoundsException("Horizontal cell is out of range");
        }
        if (y < -1 || y > height) {
            throw new ArrayIndexOutOfBoundsException("Vertical cell is out of range");
        }
        return Optional.ofNullable(cells[x + 1][y + 1]).orElseGet(() -> {
            Cell cell = Cell.of(x, y, x == -1 || y == -1 || x == width || y == height);
            cells[x + 1][y + 1] = cell;
            return cell;
        });
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}

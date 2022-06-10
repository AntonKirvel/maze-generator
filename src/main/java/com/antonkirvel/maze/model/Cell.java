package com.antonkirvel.maze.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Cell {
    private int x;
    private int y;
    private boolean outerSpace;
    private boolean targetArea;
    private List<AdjacentCell> adjacentCells = new ArrayList<>();

    private Cell(int x, int y, boolean outerSpace) {
        this.x = x;
        this.y = y;
        this.outerSpace = outerSpace;
    }

    public static Cell of(int x, int y, boolean outerSpace) {
        return new Cell(x, y, outerSpace);
    }

    public void addAdjacentCell(Cell adjacentCell) {
        adjacentCells.add(new AdjacentCell(adjacentCell, true));
    }

    public void removeBorder(int x, int y) {
        adjacentCells.stream().filter(ac -> ac.getCell().getX() == x && ac.getCell().getY() == y).findFirst().ifPresent(ac -> {
            ac.setBordered(false);
            ac.getCell().getAdjacentCells().stream().filter(ac2 -> ac2.getCell().getX() == this.x && ac2.getCell().getY() == this.y).findFirst().ifPresent(ac2 -> {
                ac2.setBordered(false);
            });
        });
    }

    public List<AdjacentCell> getAdjacentCells() {
        return adjacentCells;
    }

    public boolean isIsolated() {
        return adjacentCells.stream().allMatch(ac -> ac.isBordered());
    }

    @Override
    public String toString() {
        return "Cell{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

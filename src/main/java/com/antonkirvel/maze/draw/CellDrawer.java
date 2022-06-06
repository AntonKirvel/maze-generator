package com.antonkirvel.maze.draw;

import com.antonkirvel.maze.model.Cell;

import java.awt.*;

public class CellDrawer {

    private int baseX;
    private int baseY;
    private int cellSize;
    private Graphics graphics;

    public CellDrawer(Graphics graphics, int baseX, int baseY, int cellSize) {
        this.graphics = graphics;
        this.baseX = baseX;
        this.baseY = baseY;
        this.cellSize = cellSize;
    }

    public void draw(Cell cell) {
        cell.getBorders().forEach(border -> {
            switch (border) {
                case UP:
                    graphics.drawLine(baseX + cell.getX() * cellSize, baseY + cell.getY() * cellSize, baseX + (cell.getX() + 1) * cellSize, baseY + cell.getY() * cellSize);
                    break;
                case DOWN:
                    graphics.drawLine(baseX + cell.getX() * cellSize, baseY + (cell.getY() + 1) * cellSize, baseX + (cell.getX() + 1) * cellSize, baseY + (cell.getY() + 1) * cellSize);
                    break;
                case LEFT:
                    graphics.drawLine(baseX + cell.getX() * cellSize, baseY + cell.getY() * cellSize, baseX + cell.getX() * cellSize, baseY + (cell.getY() + 1) * cellSize);
                    break;
                case RIGHT:
                    graphics.drawLine(baseX + (cell.getX() + 1) * cellSize, baseY + cell.getY() * cellSize, baseX + (cell.getX() + 1) * cellSize, baseY + (cell.getY() + 1) * cellSize);
            }
        });
    }
}

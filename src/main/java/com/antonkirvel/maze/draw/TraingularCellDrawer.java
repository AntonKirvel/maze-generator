package com.antonkirvel.maze.draw;

import com.antonkirvel.maze.model.AdjacentCell;
import com.antonkirvel.maze.model.Cell;

import java.awt.*;

public class TraingularCellDrawer implements CellDrawer {
    private int baseX;
    private int baseY;
    private int triangleSide;
    private int triangeHeight;
    private Graphics graphics;

    public TraingularCellDrawer(Graphics graphics, int baseX, int baseY, int cellSize) {
        this.graphics = graphics;
        this.baseX = baseX;
        this.baseY = baseY;
        this.triangleSide = (int) (cellSize * Math.sqrt(2.0));
        this.triangeHeight = (int) (cellSize * Math.sqrt(2.0) * Math.sqrt(3.0) / 2.0);
    }

    @Override
    public void drawCell(Cell cell) {
        cell.getAdjacentCells().stream().filter(AdjacentCell::isBordered).map(AdjacentCell::getCell).forEach(cell2 -> {
            if (cell.getX() == cell2.getX()) {
                graphics.drawLine(baseX + triangleSide / 2 * cell.getX(), baseY + triangeHeight * (cell.getY() + cell2.getY() + 1) / 2, baseX + triangleSide / 2 * (cell.getX() + 2), baseY + triangeHeight * (cell.getY() + cell2.getY() + 1) / 2);
            } else {
                if ((cell.getY() + cell.getX()) % 2 == 0) {
                    graphics.drawLine(baseX + triangleSide / 2 * (cell.getX() + 1), baseY + triangeHeight * cell.getY(), baseX + triangleSide / 2 * (cell2.getX() + 1), baseY + triangeHeight * (cell.getY() + 1));
                } else {
                    graphics.drawLine(baseX + triangleSide / 2 * (cell.getX() + 1), baseY + triangeHeight * (cell.getY() + 1), baseX + triangleSide / 2 * (cell2.getX() + 1), baseY + triangeHeight * cell.getY());
                }
            }
        });
    }

    @Override
    public void drawLineBetweenCells(Cell previousCell, Cell cell, Cell nextCell) {
        graphics.drawLine(baseX + triangleSide / 2 + (previousCell.getX() + cell.getX()) * triangleSide / 4, baseY + triangeHeight / 2 + (previousCell.getY() + cell.getY()) * triangeHeight / 2, baseX + triangleSide / 2 + (nextCell.getX() + cell.getX()) * triangleSide / 4, baseY + triangeHeight / 2 + (nextCell.getY() + cell.getY()) * triangeHeight / 2);
    }
}

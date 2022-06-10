package com.antonkirvel.maze.draw;

import com.antonkirvel.maze.model.AdjacentCell;
import com.antonkirvel.maze.model.Cell;

import java.awt.*;

public class SquareCellDrawer implements CellDrawer {

    private int baseX;
    private int baseY;
    private int cellSize;
    private Graphics graphics;

    public SquareCellDrawer(Graphics graphics, int baseX, int baseY, int cellSize) {
        this.graphics = graphics;
        this.baseX = baseX;
        this.baseY = baseY;
        this.cellSize = cellSize;
    }

    public void drawCell(Cell cell) {
        cell.getAdjacentCells().stream().filter(AdjacentCell::isBordered).map(AdjacentCell::getCell).forEach(cell2 -> {
            if (cell.getX() == cell2.getX()) {
                graphics.drawLine(baseX + cell.getX() * cellSize, baseY + (cell.getY() + cell2.getY() + 1) * cellSize / 2, baseX + (cell.getX() + 1) * cellSize, baseY + (cell.getY() + cell2.getY() + 1) * cellSize / 2);
            } else {
                graphics.drawLine(baseX + (cell.getX() + cell2.getX() + 1) / 2 * cellSize, baseY + cell.getY() * cellSize, baseX + (cell.getX() + cell2.getX() + 1) * cellSize / 2, baseY + (cell.getY() + 1) * cellSize);
            }
        });
    }

    @Override
    public void drawLineBetweenCells(Cell previousCell, Cell cell, Cell nextCell) {
        graphics.drawLine(baseX + cellSize / 2 + (cell.getX() + previousCell.getX()) * cellSize / 2, baseY + cellSize / 2 + (cell.getY() + previousCell.getY()) * cellSize / 2, baseX + cell.getX() * cellSize + cellSize / 2, baseY + cell.getY() * cellSize + cellSize / 2);
        graphics.drawLine(baseX + cell.getX() * cellSize + cellSize / 2, baseY + cell.getY() * cellSize + cellSize / 2, baseX + cellSize / 2 + (cell.getX() + nextCell.getX()) * cellSize / 2, baseY + cellSize / 2 + (cell.getY() + nextCell.getY()) * cellSize / 2);
    }
}

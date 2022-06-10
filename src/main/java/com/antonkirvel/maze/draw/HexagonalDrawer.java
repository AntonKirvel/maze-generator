package com.antonkirvel.maze.draw;

import com.antonkirvel.maze.model.AdjacentCell;
import com.antonkirvel.maze.model.Cell;

import java.awt.*;

public class HexagonalDrawer implements CellDrawer {
    private int baseX;
    private int baseY;
    private int hexWidth;
    private int hexHeight;
    private Graphics graphics;

    public HexagonalDrawer(Graphics graphics, int baseX, int baseY, int cellSize) {
        this.baseX = baseX;
        this.baseY = baseY;
        this.hexWidth = (int) (cellSize * Math.sqrt(3));
        this.hexWidth += this.hexWidth % 2;
        this.hexHeight = cellSize * 3 / 2;
        this.hexHeight += this.hexHeight % 2;
        this.graphics = graphics;
    }

    @Override
    public void drawCell(Cell cell) {
        cell.getAdjacentCells().stream().filter(AdjacentCell::isBordered).map(AdjacentCell::getCell).forEach(cell2 -> {
            // up and down borders
            if (cell2.getX() == cell.getX()) {
                graphics.drawLine(baseX + hexWidth * cell.getX() * 3 / 4 + hexWidth / 4, baseY + hexHeight * (cell.getY() + cell2.getY() + 1) / 2 + (cell.getX() % 2 == 0 ? hexHeight / 2 : 0), baseX + hexWidth * cell.getX() * 3 / 4 + 3 * hexWidth / 4, baseY + hexHeight * (cell.getY() + cell2.getY() + 1) / 2 + (cell.getX() % 2 == 0 ? hexHeight / 2 : 0));
            }
            // left up and down borders
            if (cell2.getX() == cell.getX() - 1) {
                int startingY = baseY + hexHeight * cell.getY() + hexHeight / 2 + (cell.getX() % 2 == 0 ? hexHeight / 2 : 0);
                int endingY;
                if (cell2.getY() == cell.getY() - 1 || cell2.getY() == cell.getY() && (cell.getX() % 2 == 0)) {
                    endingY = startingY - hexHeight / 2;
                } else {
                    endingY = startingY + hexHeight / 2;
                }
                graphics.drawLine(baseX + hexWidth * cell.getX() * 3 / 4, startingY, baseX + hexWidth * cell.getX() * 3 / 4 + hexWidth / 4, endingY);
            }

            // right up and down borders
            if (cell2.getX() == cell.getX() + 1) {
                int endingY = baseY + hexHeight * cell.getY() + hexHeight / 2 + (cell.getX() % 2 == 0 ? hexHeight / 2 : 0);
                int startingY = 0;
                if (cell2.getY() == cell.getY() - 1 || cell2.getY() == cell.getY() && (cell.getX() % 2 == 0)) {
                    startingY = endingY - hexHeight / 2;
                } else {
                    startingY = endingY + hexHeight / 2;
                }
                graphics.drawLine(baseX + hexWidth * cell.getX() * 3 / 4 + 3 * hexWidth / 4, startingY, baseX + hexWidth * cell.getX() * 3 / 4 + hexWidth, endingY);
            }
        });
    }

    @Override
    public void drawLineBetweenCells(Cell previousCell, Cell cell, Cell nextCell) {
        int centerPreviousX = baseX + hexWidth / 2 + hexWidth * previousCell.getX() * 3 / 4;
        int centerPreviousY = baseY + hexHeight - (previousCell.getX() % 2 == 0 ? 0 : hexHeight / 2) + hexHeight * previousCell.getY();
        int centerCurrentX = baseX + hexWidth / 2 + hexWidth * cell.getX() * 3 / 4;
        int centerCurrentY = baseY + hexHeight - (cell.getX() % 2 == 0 ? 0 : hexHeight / 2) + hexHeight * cell.getY();
        int centerNextX = baseX + hexWidth / 2 + hexWidth * nextCell.getX() * 3 / 4;
        int centerNextY = baseY + hexHeight - (nextCell.getX() % 2 == 0 ? 0 : hexHeight / 2) + hexHeight * nextCell.getY();
        graphics.drawLine((centerPreviousX + centerCurrentX) / 2, (centerPreviousY + centerCurrentY) / 2, centerCurrentX, centerCurrentY);
        graphics.drawLine(centerCurrentX, centerCurrentY, (centerCurrentX + centerNextX) / 2, (centerCurrentY + centerNextY) / 2);
    }
}

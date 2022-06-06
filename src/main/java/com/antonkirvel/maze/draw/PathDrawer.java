package com.antonkirvel.maze.draw;

import com.antonkirvel.maze.model.Cell;

import java.awt.*;
import java.util.List;

public class PathDrawer {
    private Graphics graphics;
    private int width;
    private int cellSize;

    public PathDrawer(Graphics graphics, int width, int cellSize) {
        this.graphics = graphics;
        this.width = width;
        this.cellSize = cellSize;
    }

    public void drawPath(List<Cell> cells) {
        graphics.setColor(Color.RED);
        cells.stream().reduce((cell1, cell2) -> {
            graphics.drawLine(cell1.getX() * cellSize + 3 * cellSize / 2, cell1.getY() * cellSize + 3 * cellSize / 2, cell2.getX() * cellSize + 3 * cellSize / 2, cell2.getY() * cellSize + 3 * cellSize / 2);
            return cell2;
        });
        Cell lastCell = cells.get(cells.size() - 1);
        if (lastCell.getX() == width - 1) {
            graphics.drawLine(lastCell.getX() * cellSize + 3 * cellSize / 2, lastCell.getY() * cellSize + 3 * cellSize / 2, lastCell.getX() * cellSize + 5 * cellSize / 2, lastCell.getY() * cellSize + 3 * cellSize / 2);
        } else {
            graphics.drawLine(lastCell.getX() * cellSize + 3 * cellSize / 2, lastCell.getY() * cellSize + 3 * cellSize / 2, lastCell.getX() * cellSize + 3 * cellSize / 2, lastCell.getY() * cellSize + 5 * cellSize / 2);
        }
    }
}

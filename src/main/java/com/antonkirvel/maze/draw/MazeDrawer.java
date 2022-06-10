package com.antonkirvel.maze.draw;

import com.antonkirvel.maze.model.Cell;
import com.antonkirvel.maze.model.Maze;

import java.awt.*;
import java.util.List;

public class MazeDrawer {
    private Graphics graphics;
    private int baseX;
    private int baseY;
    private int cellSize;

    public MazeDrawer(Graphics graphics, int baseX, int baseY, int cellSize) {
        this.graphics = graphics;
        this.baseX = baseX;
        this.baseY = baseY;
        this.cellSize = cellSize;
    }

    public void draw(Maze maze) {
        graphics.setColor(Color.BLACK);
        CellDrawer cellDrawer = maze.getCellDrawer(graphics, baseX, baseY, cellSize);
        maze.forEach(cell -> {
            cellDrawer.drawCell(cell);
        });
    }

    public void drawPath(Maze maze, List<Cell> cells) {
        graphics.setColor(Color.RED);
        CellDrawer cellDrawer = maze.getCellDrawer(graphics, baseX, baseY, cellSize);
        for(int i=1;i<cells.size() - 1;i++){
            cellDrawer.drawLineBetweenCells(cells.get(i-1),cells.get(i),cells.get(i+1));
        }
    }
}

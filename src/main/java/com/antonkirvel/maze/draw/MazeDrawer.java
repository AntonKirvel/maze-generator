package com.antonkirvel.maze.draw;

import com.antonkirvel.maze.model.Maze;

import java.awt.*;

public class MazeDrawer {
    private CellDrawer cellDrawer;

    public MazeDrawer(Graphics graphics, int baseX, int baseY, int cellSize) {
        this.cellDrawer = new CellDrawer(graphics, baseX, baseY, cellSize);
    }

    public void draw(Maze maze) {
        for (int i = 0; i < maze.getWidth(); i++) {
            for (int j = 0; j < maze.getHeight(); j++) {
                cellDrawer.draw(maze.getCell(i, j));
            }
        }
    }
}

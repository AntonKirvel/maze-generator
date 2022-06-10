package com.antonkirvel.maze.draw;

import com.antonkirvel.maze.model.Cell;

public interface CellDrawer {
    void drawCell(Cell cell);
    void drawLineBetweenCells(Cell previousCell, Cell cell, Cell nextCell);
}

package com.antonkirvel.maze.model;

import com.antonkirvel.maze.draw.CellDrawer;

import java.awt.*;
import java.util.Iterator;

public interface Maze extends Iterable<Cell>{
    Cell getStartingCell();

    Cell getAnyIsolatedCellAdjacentToPath();

    Iterator<Cell> iterator();

    CellDrawer getCellDrawer(Graphics graphics, int baseX, int baseY, int cellSize);

    int getPhysicalWidth(int cellSize);

    int getPhysicalHeight(int cellSize);
}

package com.antonkirvel.maze.model;

import com.antonkirvel.maze.draw.CellDrawer;
import com.antonkirvel.maze.draw.HexagonalDrawer;
import com.antonkirvel.maze.draw.SquareCellDrawer;
import com.antonkirvel.maze.draw.TraingularCellDrawer;

import java.awt.*;
import java.util.Iterator;

public class SquareMaze implements Maze {

    // ! Martrix's row index represents X coordinate and column index represents Y coordinate
    private CellField cells;
    private int startX;
    private int startY;
    private CellShape cellShape;

    // Create new maze with all bordered cells and with 1 entrance
    public SquareMaze(int width, int height, int startX, int startY, CellShape cellShape) {
        this.cellShape = cellShape;
        if (cellShape != CellShape.SQUARE && cellShape != CellShape.TRIANGLE && cellShape != CellShape.HEXAGON) {
            throw new IllegalArgumentException("Only square, triangular, hexagonal cells are supported");
        }
        cells = new CellField(width, height);
        if (startX != 0 && startY != 0) {
            throw new IllegalArgumentException("We always have entrance on top or left side of the maze");
        }
        if (cellShape == CellShape.TRIANGLE && startX != 0 && startY % 2 == 0) {
            throw new IllegalArgumentException("Cannot start from the top of triangle");
        }

        // Link adjacent cells
        switch (cellShape) {
            case SQUARE:
                linkSquareCells(width, height);
                break;
            case TRIANGLE:
                linkTriangularCells(width, height);
                break;
            case HEXAGON:
                linkHexagonalCells(width, height);
        }

        // Mark destination cells
        for (int x = 0; x < width; x++) {
            cells.at(x, height).setTargetArea(true);
        }
        for (int y = 0; y <= height; y++) {
            cells.at(width, y).setTargetArea(true);
        }

        if (startX == 0) cells.at(startX, startY).removeBorder(-1, startY);
        else cells.at(startX, startY).removeBorder(startX, -1);
        this.startX = startX;
        this.startY = startY;
    }

    private void linkSquareCells(int width, int height) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells.at(x, y).addAdjacentCell(cells.at(x - 1, y));
                cells.at(x, y).addAdjacentCell(cells.at(x, y - 1));
                cells.at(x, y).addAdjacentCell(cells.at(x + 1, y));
                cells.at(x, y).addAdjacentCell(cells.at(x, y + 1));
            }
        }
    }

    private void linkTriangularCells(int width, int height) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells.at(x, y).addAdjacentCell(cells.at(x - 1, y));
                cells.at(x, y).addAdjacentCell(cells.at(x + 1, y));
                if ((y + x) % 2 == 1) {
                    cells.at(x, y).addAdjacentCell(cells.at(x, y - 1));
                }
                if ((y + x) % 2 == 0) {
                    cells.at(x, y).addAdjacentCell(cells.at(x, y + 1));
                }
            }
        }
    }

    private void linkHexagonalCells(int width, int height) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells.at(x, y).addAdjacentCell(cells.at(x - 1, y));
                cells.at(x, y).addAdjacentCell(cells.at(x, y - 1));
                cells.at(x, y).addAdjacentCell(cells.at(x + 1, y));
                cells.at(x, y).addAdjacentCell(cells.at(x, y + 1));

                if (x % 2 == 0) {
                    cells.at(x, y).addAdjacentCell(cells.at(x - 1, y + 1));
                    cells.at(x, y).addAdjacentCell(cells.at(x + 1, y + 1));
                } else {
                    cells.at(x, y).addAdjacentCell(cells.at(x - 1, y - 1));
                    cells.at(x, y).addAdjacentCell(cells.at(x + 1, y - 1));
                }
            }
        }
    }

    public Cell getStartingCell() {
        return cells.at(startX, startY);
    }

    @Override
    public Cell getAnyIsolatedCellAdjacentToPath() {
        for (int x = 0; x < cells.getWidth(); x++) {
            for (int y = 0; y < cells.getHeight(); y++) {
                if (cells.at(x, y).isIsolated() && cells.at(x, y).getAdjacentCells().stream().anyMatch(ac -> !ac.getCell().isOuterSpace() && !ac.getCell().isIsolated()))
                    return cells.at(x, y);
            }
        }
        return null;
    }

    @Override
    public Iterator<Cell> iterator() {
        return new Iterator<Cell>() {
            private int x = 0;
            private int y = 0;

            @Override
            public boolean hasNext() {
                return x <= cells.getWidth() - 1 && y <= cells.getHeight() - 1;
            }

            @Override
            public Cell next() {
                Cell cell = cells.at(x, y);
                if (y == cells.getHeight() - 1) {
                    x++;
                    y = 0;
                } else {
                    y++;
                }
                return cell;
            }
        };
    }

    @Override
    public CellDrawer getCellDrawer(Graphics graphics, int baseX, int baseY, int cellSize) {
        switch (cellShape) {
            case TRIANGLE:
                return new TraingularCellDrawer(graphics, baseX, baseY, cellSize);
            case HEXAGON:
                return new HexagonalDrawer(graphics, baseX, baseY, cellSize);
            default:
                return new SquareCellDrawer(graphics, baseX, baseY, cellSize);
        }
    }

    @Override
    public int getPhysicalWidth(int cellSize) {
        switch (cellShape) {
            case TRIANGLE:
                return ((int) (cellSize * Math.sqrt(2))) * (cells.getWidth() + 1) / 2;
            case HEXAGON:
                int hexWidth = (int) (cellSize * Math.sqrt(3));
                hexWidth += hexWidth % 2;
                return hexWidth * (cells.getWidth() * 3 + 1) / 4;
            default:
                return cellSize * cells.getWidth();
        }
    }

    @Override
    public int getPhysicalHeight(int cellSize) {
        switch (cellShape) {
            case TRIANGLE:
                return cells.getHeight() * ((int) (cellSize * Math.sqrt(2) * Math.sqrt(3) / 2));
            case HEXAGON:
                int hexHeight = cellSize * 3 / 2;
                hexHeight += hexHeight % 2;
                return hexHeight * cells.getHeight() + hexHeight / 2 ;
            default:
                return cells.getHeight() * cellSize;
        }
    }
}

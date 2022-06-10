package com.antonkirvel.maze.service;

import com.antonkirvel.maze.model.*;

import java.util.*;
import java.util.stream.Collectors;

public class PathGenerator {

    public static List<Cell> generateMainPath(Maze maze) {
        List<Cell> path = new ArrayList<>();
        Cell currentCell = maze.getStartingCell();
        // Add cell outside the maze to the path
        currentCell.getAdjacentCells().stream().filter(ac -> !ac.isBordered()).findAny().ifPresent(ac -> path.add(ac.getCell()));

        while (currentCell.getAdjacentCells().stream().noneMatch(ac->ac.getCell().isTargetArea())) {
            path.add(currentCell);
            List<Cell> availableMoves = currentCell.getAdjacentCells().stream().filter(ac -> !ac.getCell().isOuterSpace() && ac.getCell().isIsolated()).map(AdjacentCell::getCell).collect(Collectors.toList());
            if (!availableMoves.isEmpty()) {
                int move = (int) Math.floor(Math.random() * availableMoves.size());
                currentCell.removeBorder(availableMoves.get(move).getX(), availableMoves.get(move).getY());
                currentCell = availableMoves.get(move);
            } else {
                // Remove dead end and previous cell because we will add previous cell to the path on next iteration
                path.remove(path.size() - 1);

                currentCell = path.get(path.size() - 1);
                path.remove(path.size() - 1);
            }
        }
        path.add(currentCell);
        // Add cell outside the maze to the path
        Optional<Cell> outerSpaceCell = currentCell.getAdjacentCells().stream().map(AdjacentCell::getCell).filter(Cell::isTargetArea).findAny();
        if (outerSpaceCell.isPresent()) {
            currentCell.removeBorder(outerSpaceCell.get().getX(), outerSpaceCell.get().getY());
            path.add(outerSpaceCell.get());
        }
        return path;
    }

    public static boolean findDeadPath(Maze maze) {
        // Try to find isolated block which borders block on some path
        Cell isolatedCell = maze.getAnyIsolatedCellAdjacentToPath();
        if (isolatedCell == null) {
            return false;
        }
        Cell blockFromPath = isolatedCell.getAdjacentCells().stream().map(AdjacentCell::getCell).filter(c -> !c.isOuterSpace() && !c.isIsolated()).findFirst().get();
        generateDeadPath(blockFromPath, isolatedCell);
        return true;
    }

    private static void generateDeadPath(Cell startingCell, Cell firstMove) {
        startingCell.removeBorder(firstMove.getX(), firstMove.getY());
        Cell currentCell = firstMove;
        while (true) {
            List<Cell> availableMoves = currentCell.getAdjacentCells().stream().map(AdjacentCell::getCell).filter(c -> !c.isOuterSpace() && c.isIsolated()).collect(Collectors.toList());
            if (!availableMoves.isEmpty()) {
                int move = (int) Math.floor(Math.random() * availableMoves.size());
                currentCell.removeBorder(availableMoves.get(move).getX(), availableMoves.get(move).getY());
                currentCell = availableMoves.get(move);
            } else {
                // Reached dead end, path is done
                break;
            }
        }
    }
}

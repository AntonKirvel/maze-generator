package com.antonkirvel.maze.service;

import com.antonkirvel.maze.model.Cell;
import com.antonkirvel.maze.model.DIRECTION;
import com.antonkirvel.maze.model.Maze;

import java.util.*;
import java.util.stream.IntStream;

public class PathGenerator {

    public static List<Cell> generateMainPath(Maze maze) {
        List<Cell> path = new ArrayList<>();
        Cell currentCell = maze.getStartingCell();
        while (currentCell.getX() < maze.getWidth() - 1 && currentCell.getY() < maze.getHeight() - 1) {
            path.add(currentCell);
            int direction;
            Boolean[] availableDirections = getAvailableDirections(maze, currentCell);
            if (availableDirections[0] || availableDirections[1] || availableDirections[2] || availableDirections[3]) {
                do {
                    direction = (int) Math.floor(Math.random() * 4);
                } while (!availableDirections[direction]);
                currentCell.removeBorder(DIRECTION.fromIndex(direction));
                currentCell = maze.getCell(currentCell.getX() + DIRECTION.fromIndex(direction).getMoveX(), currentCell.getY() + DIRECTION.fromIndex(direction).getMoveY());
                currentCell.removeBorder(DIRECTION.fromIndex(direction).oppositeDirection());
            } else {
                // Remove dead end and previous cell because we will add previous cell to the path on next iteration
                path.remove(path.size() - 1);

                currentCell = path.get(path.size() - 1);
                path.remove(path.size() - 1);
            }
        }
        if (currentCell.getY() == maze.getHeight() - 1) {
            currentCell.removeBorder(DIRECTION.DOWN);
        } else {
            currentCell.removeBorder(DIRECTION.RIGHT);
        }
        path.add(currentCell);
        return path;
    }

    public static boolean findDeadPath(Maze maze) {
        // Try to find bordered block which borders block on some path
        for (int x = 0; x < maze.getWidth(); x++) {
            for (int y = 0; y < maze.getHeight(); y++) {
                Boolean[] availableDirections = getAvailableDirections(maze, maze.getCell(x, y));
                Optional<DIRECTION> availableDirection = IntStream.range(0, 4).mapToObj(i -> availableDirections[i] ? DIRECTION.fromIndex(i) : null).filter(Objects::nonNull).findFirst();
                if (availableDirection.isPresent()) {

                    generateDeadPath(maze, maze.getCell(x, y), availableDirection.get());
                    return true;
                }
            }
        }
        return false;
    }

    private static void generateDeadPath(Maze maze, Cell startingCell, DIRECTION startingDirection) {
        startingCell.removeBorder(startingDirection);
        Cell currentCell = maze.getCell(startingCell.getX() + startingDirection.getMoveX(), startingCell.getY() + startingDirection.getMoveY());
        currentCell.removeBorder(startingDirection.oppositeDirection());
        while (true) {
            int direction;
            Boolean[] availableDirections = getAvailableDirections(maze, currentCell);
            if (availableDirections[0] || availableDirections[1] || availableDirections[2] || availableDirections[3]) {
                do {
                    direction = (int) Math.floor(Math.random() * 4);
                } while (!availableDirections[direction]);
                currentCell.removeBorder(DIRECTION.fromIndex(direction));
                currentCell = maze.getCell(currentCell.getX() + DIRECTION.fromIndex(direction).getMoveX(), currentCell.getY() + DIRECTION.fromIndex(direction).getMoveY());
                currentCell.removeBorder(DIRECTION.fromIndex(direction).oppositeDirection());
            } else {
                // Reached dead end, path is done
                break;
            }
        }
    }

    private static Boolean[] getAvailableDirections(Maze maze, Cell currentCell) {
        Boolean[] availableDirections = new Boolean[4];
        availableDirections[0] = currentCell.getY() != 0 && maze.getCell(currentCell.getX(), currentCell.getY() - 1).isIsolated();
        availableDirections[1] = currentCell.getX() != maze.getWidth() - 1 && maze.getCell(currentCell.getX() + 1, currentCell.getY()).isIsolated();
        availableDirections[2] = currentCell.getY() != maze.getHeight() - 1 && maze.getCell(currentCell.getX(), currentCell.getY() + 1).isIsolated();
        availableDirections[3] = currentCell.getX() != 0 && maze.getCell(currentCell.getX() - 1, currentCell.getY()).isIsolated();
        return availableDirections;
    }
}

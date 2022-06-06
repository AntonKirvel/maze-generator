package com.antonkirvel.maze.model;

import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class Cell {
    private int x;
    private int y;
    private Boolean borders[] = new Boolean[]{false, false, false, false};

    private Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Cell of(int x, int y) {
        return new Cell(x, y);
    }

    public Cell withBorder(DIRECTION... borders) {
        Arrays.fill(this.borders, false);
        Arrays.stream(borders).forEach(b -> this.borders[b.getIndex()] = true);
        return this;
    }

    public void addBorder(DIRECTION border) {
        this.borders[border.getIndex()] = true;
    }

    public void removeBorder(DIRECTION border) {
        this.borders[border.getIndex()] = false;
    }

    public List<DIRECTION> getBorders() {
        return IntStream.range(0, 4).mapToObj(i -> borders[i] ? DIRECTION.fromIndex(i) : null).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public boolean isIsolated() {
        return Arrays.stream(borders).allMatch(b -> b);
    }
}

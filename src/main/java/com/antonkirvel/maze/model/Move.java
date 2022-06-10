package com.antonkirvel.maze.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Move {
    private int moveX;
    private int moveY;
}

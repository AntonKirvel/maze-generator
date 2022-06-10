package com.antonkirvel.maze.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdjacentCell {
    private Cell cell;
    private boolean bordered;
}

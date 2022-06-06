package com.antonkirvel.maze.controller;

import com.antonkirvel.maze.draw.MazeDrawer;
import com.antonkirvel.maze.draw.PathDrawer;
import com.antonkirvel.maze.model.Cell;
import com.antonkirvel.maze.model.DIRECTION;
import com.antonkirvel.maze.model.Maze;
import com.antonkirvel.maze.service.PathGenerator;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
public class MazeController {

    public static final int CELL_SIZE = 30;
    public static final int MAX_MAZE_SIZE = 100;

    @GetMapping(path = "/maze", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> maze(@RequestParam(required = true) int width, @RequestParam(required = true) int height, @RequestParam(required = false)boolean showMainPath) throws IOException {
        if (width <= 1 || height <= 1) {
            throw new IllegalArgumentException("Width and height should be at least 2");
        }
        if (width > MAX_MAZE_SIZE || height > MAX_MAZE_SIZE) {
            throw new IllegalArgumentException("Width and height should be no more than " + MAX_MAZE_SIZE);
        }
        BufferedImage image = new BufferedImage(width * CELL_SIZE + CELL_SIZE * 2, height * CELL_SIZE + CELL_SIZE * 2, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width * CELL_SIZE + CELL_SIZE * 2, height * CELL_SIZE * 2);

        Maze maze = new Maze(width, height, 0, 0, DIRECTION.UP);

        List<Cell> mainPath = PathGenerator.generateMainPath(maze);
        while (PathGenerator.findDeadPath(maze)) {
        }

        graphics.setColor(Color.BLACK);
        new MazeDrawer(graphics, CELL_SIZE, CELL_SIZE, CELL_SIZE).draw(maze);

        if (showMainPath) {
            int currentX = CELL_SIZE * 3 / 2;
            int currentY = CELL_SIZE / 2;
            graphics.setColor(Color.RED);
            graphics.drawLine(currentX, currentY, currentX + DIRECTION.DOWN.getMoveX() * CELL_SIZE, currentY + DIRECTION.DOWN.getMoveY() * CELL_SIZE);
            new PathDrawer(graphics, width, CELL_SIZE).drawPath(mainPath);
        }

        ByteArrayOutputStream output = new ByteArrayOutputStream(width * height * 3);
        ImageIO.write(image, "png", output);
        return ResponseEntity.ok().body(output.toByteArray());
    }

}

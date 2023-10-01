package edu.school21.printer.logic;

import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Logic {
    private String fileLink;

    public Logic(String link) {
        fileLink = link;
    }

    public int[][] ImageToChar() throws IOException {
        BufferedImage image = ImageIO.read(new File(fileLink));
        int width = image.getWidth();
        int height = image.getHeight();
        int[][] result = new int[width][height];
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                Color c = new Color(image.getRGB(i, j));
                if (c.equals(Color.black)) {
                    result[i][j] = 1;
                }
            }
        }
        return result;
    }
}

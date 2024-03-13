package RacoonRush.map;

import java.awt.image.BufferedImage;

public class Tile {
    public BufferedImage image;
    public boolean collision;

    public boolean collectible;

    public Tile(BufferedImage image, boolean collision) {
        this.image = image;
        this.collision = collision;
    }
}

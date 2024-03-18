package RacoonRush.map.tile;

import RacoonRush.entity.Player;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Wall class is a subclass of Tile class. It is used to create wall tiles that the player cannot pass through.
 */
public class Wall extends Tile {

    /**
     * Constructor for Wall class
     * @param images ArrayList of BufferedImages
     */
    public Wall(ArrayList<BufferedImage> images) {
        super(images);
    }

    /**
     * Method to check if a player collides with the wall
     * @return false
     */
    @Override
    public boolean onCollide(Player player) {
        return false;
    }

    /**
     * Method to get the image of the wall
     * @param x x-coordinate of the tile
     * @param y y-coordinate of the tile
     * @param animationFrame animation frame
     * @return BufferedImage
     */
    @Override
    public BufferedImage getImage(int x, int y, int animationFrame) {
        return images.get((x * y + 3) % images.size());
    }
}

package RacoonRush.map.tile;

import RacoonRush.entity.Entity;

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
    public boolean onCollide(Entity entity) {
        return false;
    }

    /**
     * Method to get the image of the wall
     * Utilizes the x and y coordinates to get the correct image, animation frame is not used and can be null
     * @param x x-coordinate of the tile
     * @param y y-coordinate of the tile
     * @param animationFrame animation frame, not used
     * @return BufferedImage
     */
    @Override
    public BufferedImage getImage(int x, int y, int animationFrame) {
        return images.get((x * y + 3) % images.size());
    }
}

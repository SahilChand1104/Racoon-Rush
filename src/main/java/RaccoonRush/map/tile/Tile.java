package RaccoonRush.map.tile;

import RaccoonRush.entity.Entity;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Tile class is an abstract class that is used to create the different types of tiles in the game.
 */
public abstract class Tile {
    protected final ArrayList<BufferedImage> images;

    /**
     * Constructor for Tile class
     * @param images images of the tile
     */
    public Tile(ArrayList<BufferedImage> images) {
        this.images = images;
    }

    /**
     * Method to get the image of the tile
     * Not all parameters are used by each subclass, but all parameters are included for flexibility
     * @param x x-coordinate of the tile
     * @param y y-coordinate of the tile
     * @param animationFrame animation frame of the tile
     * @return BufferedImage of the tile
     */
    public abstract BufferedImage getImage(int x, int y, int animationFrame);

    public boolean onCollide(Entity entity) {
        return true;
    }
}

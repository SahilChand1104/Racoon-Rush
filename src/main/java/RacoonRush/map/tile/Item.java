package RacoonRush.map.tile;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Item class is a subclass of Tile class. It is used to create items that the player can collect.
 * It has a score value and a boolean to check if it has been collected.
 */
public class Item extends Tile {
    private final TileType type;
    private boolean collected;

    /**
     * Constructor for Item class
     * @param images ArrayList of BufferedImages
     * @param type type of the item
     */
    public Item(ArrayList<BufferedImage> images, TileType type) {
        super(images);
        this.type = type;
        collected = false;
    }

    /**
     * Method to get the image of the item
     * Utilizes the animation frame to get the correct image, x and y coordinates are not used and can be null
     * @param x x-coordinate of the item, not used
     * @param y y-coordinate of the item, not used
     * @param animationFrame animation frame of the item
     * @return BufferedImage of the item
     */
    @Override
    public BufferedImage getImage(int x, int y, int animationFrame) {
        if (images == null) { return null; }
        return collected ? null : images.get(animationFrame % images.size());
    }

    /**
     * Method to get the type of the item
     * @return the type of the item
     */
    public TileType getType() {
        return type;
    }

    /**
     * Method to check if the item has been collected
     * If it has been collected, it will not be rendered
     * @return boolean of whether the item has been collected
     */
    public boolean isCollected() {
        return collected;
    }

    /**
     * Method to set the collection status of the item
     */
    public void setCollected(boolean status) {
        collected = status;
    }
}

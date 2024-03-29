package RacoonRush.entity;

import RacoonRush.util.Config;
import RacoonRush.game.GamePanel;
import RacoonRush.util.Move;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EnumMap;

/**
 * Abstract class for the entities in the game
 */
public abstract class Entity {
    protected GamePanel gamePanel;
    protected int worldX, worldY, speed;
    protected Move direction;
    protected ArrayList<EnumMap<Move, BufferedImage>> images;
    protected Rectangle hitbox;

    /**
     * Constructor for the entity
     * @param gamePanel the gamePanel
     * @param worldX the x coordinate in the world
     * @param worldY the y coordinate in the world
     * @param speed the speed of the entity
     * @param direction the direction the entity is facing
     * @param images the images of the entity
     */
    public Entity(GamePanel gamePanel, int worldX, int worldY, int speed, Move direction, ArrayList<EnumMap<Move, BufferedImage>> images) {
        this.gamePanel = gamePanel;
        this.worldX = worldX;
        this.worldY = worldY;
        this.speed = speed;
        this.direction = direction;
        this.images = images;
        Config config = gamePanel.getConfig();
        hitbox = new Rectangle(config.tileSize() / 6, config.tileSize() / 3, config.tileSize() * 2 / 3, config.tileSize() * 2 / 3);
    }

    /**
     * Updates the entity's movement
     */
    public abstract void update();

    /**
     * Draws the entity
     * @param g2 the graphics object
     */
    public abstract void draw(Graphics2D g2);

    /**
     * Returns the image at the given index and direction
     * @param index the index of the image
     * @param move the direction of the image
     * @return the image at the given index and direction
     */
    protected BufferedImage getImage(int index, Move move) {
        return images.get(index).get(move);
    }

    /**
     * Returns the hitbox of the entity
     * @return the hitbox of the entity
     */
    public Rectangle getWorldHitbox() {
        return new Rectangle(worldX + hitbox.x, worldY + hitbox.y, hitbox.width, hitbox.height);
    }

    /**
     * Returns the tile index in the horizontal direction of the left side of the entity
     * @param offsetX the x offset, typically the entity's speed or 0
     * @return the tile index in the horizontal direction of the left side of the entity
     */
    public int leftColumn(int offsetX) {
        return (worldX + hitbox.x + offsetX) / gamePanel.getConfig().tileSize();
    }

    /**
     * Returns the tile index in the horizontal direction of the right side of the entity
     * @param offsetX the x offset, typically the entity's speed or 0
     * @return the tile index in the horizontal direction of the right side of the entity
     */
    public int rightColumn(int offsetX) {
        return (worldX + hitbox.x + hitbox.width + offsetX) / gamePanel.getConfig().tileSize();
    }

    /**
     * Returns the tile index in the vertical direction of the top side of the entity
     * @param offsetY the y offset, typically the entity's speed or 0
     * @return the tile index in the vertical direction of the top side of the entity
     */
    public int topRow(int offsetY) {
        return (worldY + hitbox.y + offsetY) / gamePanel.getConfig().tileSize();
    }

    /**
     * Returns the tile index in the vertical direction of the bottom side of the entity
     * @param offsetY the y offset, typically the entity's speed or 0
     * @return the tile index in the vertical direction of the bottom side of the entity
     */
    public int bottomRow(int offsetY) {
        return (worldY + hitbox.y + hitbox.height + offsetY) / gamePanel.getConfig().tileSize();
    }

    /**
     * Returns the x coordinate in the world
     * @return the x coordinate in the world
     */
    public int getWorldX() {
        return worldX;
    }

    /**
     * Returns the y coordinate in the world
     * @return the y coordinate in the world
     */
    public int getWorldY() {
        return worldY;
    }

    /**
     * Returns the speed of the entity
     * @return the speed of the entity
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Returns the direction the entity is facing
     * @return the direction the entity is facing
     */
    public Move getDirection() {
        return direction;
    }
}

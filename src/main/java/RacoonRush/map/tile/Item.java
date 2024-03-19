package RacoonRush.map.tile;

import RacoonRush.entity.Entity;
import RacoonRush.entity.Player;
import RacoonRush.entity.enemy.Enemy;
import RacoonRush.game.GamePanel;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Item class is a subclass of Tile class. It is used to create items that the player can collect.
 * It has a score value and a boolean to check if it has been collected.
 */
public class Item extends Tile {
    private final int score;

    private final GamePanel gamePanel;
    private boolean collected;

    /**
     * Constructor for Item class
     * @param images ArrayList of BufferedImages
     * @param score score value of the item
     * @param gamePanel GamePanel object
     */
    public Item(ArrayList<BufferedImage> images, int score, GamePanel gamePanel) {
        super(images);
        this.gamePanel = gamePanel;
        this.score = score;
        collected = false;
    }

    /**
     * Method to collect the item if a player collides with it
     * @return true
     */
    @Override
    public boolean onCollide(Entity entity) {
        if (entity instanceof Player player && !collected) {
            collected = true;
            player.updateScore(score);

            if (score == 10) {
                gamePanel.PlaySoundEffect(1);
            } else if (score == -20) {
                gamePanel.PlaySoundEffect(2);
            } else if (score == 50) {
                gamePanel.PlaySoundEffect(1);
            } else if (score == 0 && gamePanel.getPlayer().getDonutsLeft() == 0) {
                gamePanel.winGame();
            }

        }

        return true;
    }

    /**
     * Method to get the image of the item
     * @param x x-coordinate of the item
     * @param y y-coordinate of the item
     * @param animationFrame animation frame of the item
     * @return BufferedImage of the item
     */
    @Override
    public BufferedImage getImage(int x, int y, int animationFrame) {
        if (images == null) { return null; }
        return collected ? null : images.get(animationFrame % images.size());
    }

    /**
     * Method to set the collection status of the item
     */
    public void setCollected(boolean status) {
        collected = status;
    }
}

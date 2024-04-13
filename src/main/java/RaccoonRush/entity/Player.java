package RaccoonRush.entity;

import RaccoonRush.entity.enemy.Enemy;
import RaccoonRush.game.*;
import RaccoonRush.game.menu.MenuKey;
import RaccoonRush.util.CollisionDetector;
import RaccoonRush.util.KeyHandler;
import RaccoonRush.util.Move;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EnumMap;

/**
 * Class for the player entity
 * This class should only have one instance
 */
public class Player extends Entity implements GameManager {
    public final int screenX, screenY, invincibilityDuration;
    private int invincibilityFrames;


    /**
     * Constructor for the player entity
     *
     * @param images the images of the entity
     * @param worldX the x coordinate in the world
     * @param worldY the y coordinate in the world
     * @param speed the speed of the entity
     * @param direction the direction the entity is facing
     */
    public Player(ArrayList<EnumMap<Move, BufferedImage>> images, int worldX, int worldY, int speed, Move direction) {
        super(images, worldX, worldY, speed, direction);

        // Centered in the bottom right of the screen
        screenX = GamePanel.config.screenWidth() / 2 - GamePanel.config.tileSize() / 2;
        screenY = GamePanel.config.screenHeight() / 2 - GamePanel.config.tileSize() / 2;
        invincibilityDuration = GamePanel.config.FPS() * 2;
    }

    /**
     * Updates the player's actions
     */
    @Override
    public void update() {
        GamePanel gamePanel = GamePanel.getInstance();

        // Use the Menu KeyHandler to check if the game needs to be paused
        if (gamePanel.getMenuKeyHandler().get(MenuKey.PAUSE)) {
            gamePanel.pauseGame();
        }

        // Use the Game KeyHandler for player movement
        KeyHandler keyHandler = gamePanel.getKeyHandler();

        if (!keyHandler.get(Move.UP) && !keyHandler.get(Move.DOWN) && !keyHandler.get(Move.LEFT) && !keyHandler.get(Move.RIGHT)) {
            return;
        }

        CollisionDetector collisionDetector = gamePanel.getCollisionDetector();

        // Movement changes world coordinates, imitating a camera since the player is technically never moving on the screen
        if (keyHandler.get(Move.UP) && collisionDetector.move(this, Move.UP)) {
            direction = Move.UP;
            worldY -= speed;
        } else if (keyHandler.get(Move.DOWN) && collisionDetector.move(this, Move.DOWN)) {
            direction = Move.DOWN;
            worldY += speed;
        } else if (keyHandler.get(Move.LEFT) && collisionDetector.move(this, Move.LEFT)) {
            direction = Move.LEFT;
            worldX -= speed;
        } else if (keyHandler.get(Move.RIGHT) && collisionDetector.move(this, Move.RIGHT)) {
            direction = Move.RIGHT;
            worldX += speed;
        }
        invincibilityFrames = isInvincible() ? invincibilityFrames - 1 : invincibilityFrames;
    }

    /**
     * Draws the player
     * @param g2 the graphics2D object
     */
    @Override
    public void draw(Graphics2D g2) {
        int animationFrame = GamePanel.getInstance().getKeyHandler().get(direction) ? GamePanel.getInstance().getPlayerAnimationFrame() : 0;
        g2.drawImage(
                images.get(animationFrame).get(direction),
                screenX,
                screenY,
                GamePanel.config.tileSize(),
                GamePanel.config.tileSize(),
                null
        );
    }

    /**
     * Checks if the player is invincible based on the invincibility frames they have remaining, 1 invincibility frame is lost per update
     * @return boolean value of whether the play has more than 0 invincibility frames remaining
     */
    public boolean isInvincible() {
        return invincibilityFrames > 0;
    }

    /**
     * @param enemy the enemy that the player has collided with
     */
    public void onCollide(Enemy enemy) {
        GamePanel.getInstance().stopGame(false); // For now, lose game as per assignment instructions, but can be removed if desired
        // updateScore(-enemy.getDamage());
        invincibilityFrames = invincibilityDuration;
    }

    /**
     * Returns the screen x position
     * @return the screen x integer position
     */
    public int getScreenX() {
        return screenX;
    }

    /**
     * Returns the screen y position
     * @return the screen y integer position
     */
    public int getScreenY() {
        return screenY;
    }
}

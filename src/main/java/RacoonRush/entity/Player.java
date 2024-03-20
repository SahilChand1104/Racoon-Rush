package RacoonRush.entity;

import RacoonRush.entity.enemy.Enemy;
import RacoonRush.game.*;
import RacoonRush.game.menu.UIKeyHandler;
import RacoonRush.game.menu.UI_Pressed;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EnumMap;

/**
 * Class for the player entity
 * This class should only have one instance
 */
public class Player extends Entity implements Manager {
    public final int screenX, screenY, invincibilityDuration;
    private int invincibilityFrames;

    /**
     * Constructor for the player
     * @param gamePanel the gamePanel
     */
    public Player(GamePanel gamePanel, int worldX, int worldY, int speed, Move direction, ArrayList<EnumMap<Move, BufferedImage>> images) {
        super(gamePanel, worldX, worldY, speed, direction, images);

        // Centered in the middle of the map
        Config config = gamePanel.getConfig();
        screenX = config.screenWidth() / 2 - config.tileSize() / 2;
        screenY = config.screenHeight() / 2 - config.tileSize() / 2;
        invincibilityDuration = 120;
    }

    /**
     * Updates the player's actions
     */
    @Override
    public void update() {
        // Use the game keyhandler for player movement
        KeyHandler keyHandler = gamePanel.getKeyHandler();
        // Use the UI keyhandler for pausing/playing the game
        UIKeyHandler uiKeyHandler = gamePanel.getUIKeyHandler();
        CollisionDetector collisionDetector = gamePanel.getCollisionDetector();
        if (uiKeyHandler.get(UI_Pressed.PAUSE)) {
            gamePanel.openMenu();
        }
        if (!keyHandler.get(Move.UP) && !keyHandler.get(Move.DOWN) && !keyHandler.get(Move.LEFT) && !keyHandler.get(Move.RIGHT)) {
            return;
        }

        // Movement causes world coordinates to change, imitating a camera
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
        int animationFrame = gamePanel.getKeyHandler().get(direction) ? gamePanel.getPlayerAnimationFrame() : 0;
        g2.drawImage(
                images.get(animationFrame).get(direction),
                screenX,
                screenY,
                gamePanel.getConfig().tileSize(),
                gamePanel.getConfig().tileSize(),
                null
        );
    }

    public boolean isInvincible() {
        return invincibilityFrames > 0;
    }

    public void onCollide(Enemy enemy) {
        gamePanel.loseGame(); // For now, lose game as per assignment instructions, but can be removed if desired
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

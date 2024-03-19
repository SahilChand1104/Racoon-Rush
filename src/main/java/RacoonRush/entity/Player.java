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
    private int score, invincibilityFrames, donutsLeft;

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
        score = 0;
        invincibilityDuration = 120;
        score = 0;
        donutsLeft = 0;
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
        if (score < 0) {
            gamePanel.loseGame();
        }
        invincibilityFrames = isInvincible() ? invincibilityFrames - 1 : invincibilityFrames;
    }

    /**
     * Draws the player
     * @param g2 the graphics2D object
     * @param animationFrame the animation frame for the player
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
        updateScore(-enemy.getDamage());
        invincibilityFrames = invincibilityDuration;
    }

    /**
     * Updates the score
     * @param score the score
     */
    public void updateScore(int score) {
        Scoreboard scoreboard = gamePanel.getScoreboard();
        this.score += score;
        scoreboard.updateScore(score);
        if (score == 10) {
            donutsLeft--;
            if (donutsLeft == 0) {
                scoreboard.showMessage("+10 points! Hurry to the exit!");
            } else if (donutsLeft == 1) {
                scoreboard.showMessage("+10 points! 1 more donuts left.");
            } else {
                scoreboard.showMessage("+10 points! " + donutsLeft + " more donuts left.");
            }
        } else if (score == -20) {
            scoreboard.showMessage("-20 points...");
        } else if (score == 50) {
            scoreboard.showMessage("+50 points! You found Uncle Fatih's lost pizza!");
        }
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

    /**
     * Add a donut to the donutsLeft count
     */
    public void addDonutsLeft() { donutsLeft++; }
    /**
     * Remove a donut from the donutsLeft count
     */
    public int getDonutsLeft() { return donutsLeft; }
    /**
     * Returns the score
     * @return the score
     */
    public int getScore() { return score; }
    /**
     * Resets the score
     */
    public void resetScore() { score = 0; }
}

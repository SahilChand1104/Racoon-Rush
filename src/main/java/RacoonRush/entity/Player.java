package RacoonRush.entity;

import RacoonRush.game.*;
import RacoonRush.game.menu.UIKeyHandler;
import RacoonRush.game.menu.UI_Pressed;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Class for the player entity
 * This class should only have one instance
 */
public class Player extends Entity {
    private final GamePanel gamePanel;

    public final int screenX, screenY;
    private int score;
    private int donutsLeft;

    /**
     * Constructor for the player
     * @param gamePanel the gamePanel
     */
    public Player(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        // Centered in the middle of the map
        Config config = gamePanel.getConfig();
        screenX = config.screenWidth() / 2 - config.tileSize() / 2;
        screenY = config.screenHeight() / 2 - config.tileSize() / 2;

        hitbox = new Rectangle(config.tileSize() / 6, config.tileSize() / 3, config.tileSize() * 2 / 3, config.tileSize() * 2 / 3);

        donutsLeft = 0;

        setDefaultValues();
    }

    /**
     * Sets the default values for the player
     */
    public void setDefaultValues() {
        // Starting position of the player
        Config config = gamePanel.getConfig();
        worldX = config.tileSize() * (config.maxWorldCol() - 2);
        worldY = config.tileSize() * (config.maxWorldRow() - 2);

        speed = 4;
        dir = Move.DOWN;

        score = 0;
    }

    /**
     * Returns the left edge of the player
     * @param offsetX the x offset
     * @return the left column
     */
    public int leftColumn(int offsetX) {
        return (worldX + hitbox.x + offsetX) / gamePanel.getConfig().tileSize();
    }

    /**
     * Returns the right edge of the player
     * @param offsetX the x offset
     * @return the right column
     */
    public int rightColumn(int offsetX) {
        return (worldX + hitbox.x + hitbox.width + offsetX) / gamePanel.getConfig().tileSize();
    }

    /**
     * Returns the top edge of the player
     * @param offsetY the y offset
     * @return the top row
     */
    public int topRow(int offsetY) {
        return (worldY + hitbox.y + offsetY) / gamePanel.getConfig().tileSize();
    }

    /**
     * Returns the bottom edge of the player
     * @param offsetY the y offset
     * @return the bottom row
     */
    public int bottomRow(int offsetY) {
        return (worldY + hitbox.y + hitbox.height + offsetY) / gamePanel.getConfig().tileSize();
    }

    /**
     * Updates the player's actions
     */
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
            dir = Move.UP;
            worldY -= speed;
        } else if (keyHandler.get(Move.DOWN) && collisionDetector.move(this, Move.DOWN)) {
            dir = Move.DOWN;
            worldY += speed;
        } else if (keyHandler.get(Move.LEFT) && collisionDetector.move(this, Move.LEFT)) {
            dir = Move.LEFT;
            worldX -= speed;
        } else if (keyHandler.get(Move.RIGHT) && collisionDetector.move(this, Move.RIGHT)) {
            dir = Move.RIGHT;
            worldX += speed;
        }
        if (score < 0) {
            gamePanel.loseGame();
        }
    }

    /**
     * Draws the player
     * @param g2 the graphics2D object
     * @param animationFrame the animation frame for the player
     */
    public void draw(Graphics2D g2, int animationFrame) {
        ImageLoader imageLoader = gamePanel.getImageLoader();
        animationFrame = gamePanel.getKeyHandler().get(dir) ? animationFrame : 0;
        BufferedImage image = imageLoader.getPlayerImage(dir, animationFrame);
        g2.drawImage(image, screenX, screenY, gamePanel.getConfig().tileSize(), gamePanel.getConfig().tileSize(), null);
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
     * Returns the world x position
     * @return the world x integer position
     */
    public int getWorldX() {
        return worldX;
    }
    /**
     * Returns the world y position
     * @return the world y integer position
     */
    public int getWorldY() {
        return worldY;
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

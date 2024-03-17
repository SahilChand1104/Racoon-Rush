package RacoonRush.entity;

import RacoonRush.game.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity implements Manager {
    private final Config config;

    public final int screenX, screenY;
    private int score;

    public Player(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        config = gamePanel.getConfig();

        hitbox = new Rectangle(config.tileSize() / 6, config.tileSize() / 3, config.tileSize() * 2 / 3, config.tileSize() * 2 / 3);
        images = gamePanel.getImageLoader().getPlayerImages();

        // Centered in the middle of the map
        screenX = config.screenWidth() / 2 - config.tileSize() / 2;
        screenY = config.screenHeight() / 2 - config.tileSize() / 2;

        setDefaultValues();
    }

    public void setDefaultValues() {
        // Starting position of the player
        worldX = config.tileSize() * (config.maxWorldCol() - 2);
        worldY = config.tileSize() * (config.maxWorldRow() - 2);

        speed = 4;
        dir = Move.DOWN;
    }

    public int leftColumn(int offsetX) {
        return (worldX + hitbox.x + offsetX) / config.tileSize();
    }

    public int rightColumn(int offsetX) {
        return (worldX + hitbox.x + hitbox.width + offsetX) / config.tileSize();
    }

    public int topRow(int offsetY) {
        return (worldY + hitbox.y + offsetY) / config.tileSize();
    }

    public int bottomRow(int offsetY) {
        return (worldY + hitbox.y + hitbox.height + offsetY) / config.tileSize();
    }

    public void update() {
        KeyHandler keyHandler = gamePanel.getKeyHandler();
        CollisionDetector collisionDetector = gamePanel.getCollisionDetector();
        if (!keyHandler.get(Move.UP) && !keyHandler.get(Move.DOWN) && !keyHandler.get(Move.LEFT) && !keyHandler.get(Move.RIGHT)) {
            return;
        }

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
    }

    public void draw(Graphics2D g2) {
        int animationFrame = gamePanel.getKeyHandler().get(dir) ? gamePanel.getPlayerAnimationFrame() : 0;
        g2.drawImage(images.get(animationFrame).get(dir), screenX, screenY, config.tileSize(), config.tileSize(), null);
    }

    public void updateScore(int score) {
        this.score += score;
    }


    public int getScreenX() {
        return screenX;
    }
    public int getScreenY() {
        return screenY;
    }
}

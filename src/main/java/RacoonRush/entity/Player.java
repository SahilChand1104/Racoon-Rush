package RacoonRush.entity;

import RacoonRush.game.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    private final GamePanel gp;
    private final Config config;

    public final int screenX, screenY;

    public Player(GamePanel gp) {
        this.gp = gp;
        this.config = gp.getConfig();

        // Centered in the middle of the map
        screenX = config.screenWidth() / 2 - config.tileSize() / 2;
        screenY = config.screenHeight() / 2 - config.tileSize() / 2;

        hitbox = new Rectangle(config.tileSize() / 6, config.tileSize() / 3, config.tileSize() * 2 / 3, config.tileSize() * 2 / 3);

        setDefaultValues();
    }

    public void setDefaultValues() {
        // Starting position of the player
        worldX = config.tileSize() * (config.maxWorldCol() - 1) - 1*config.tileSize();
        worldY = config.tileSize() * (config.maxWorldRow() - 1) - 1*config.tileSize();

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
        KeyHandler keyHandler = gp.getKeyHandler();
        CollisionDetector collisionDetector = gp.getCollisionDetector();
        if (!keyHandler.get(Move.UP) && !keyHandler.get(Move.DOWN) && !keyHandler.get(Move.LEFT) && !keyHandler.get(Move.RIGHT)) {
            return;
        }

        if (keyHandler.get(Move.UP) && collisionDetector.playerCanMove(this, Move.UP)) {
            dir = Move.UP;
            worldY -= speed;
        } else if (keyHandler.get(Move.DOWN) && collisionDetector.playerCanMove(this, Move.DOWN)) {
            dir = Move.DOWN;
            worldY += speed;
        } else if (keyHandler.get(Move.LEFT) && collisionDetector.playerCanMove(this, Move.LEFT)) {
            dir = Move.LEFT;
            worldX -= speed;
        } else if (keyHandler.get(Move.RIGHT) && collisionDetector.playerCanMove(this, Move.RIGHT)) {
            dir = Move.RIGHT;
            worldX += speed;
        }
    }

    public void draw(Graphics2D g2, int animationFrame) {
        ImageLoader imageLoader = gp.getImageLoader();
        animationFrame = gp.getKeyHandler().get(dir) ? animationFrame : 0;
        BufferedImage image = imageLoader.getPlayerImage(dir, animationFrame);
        g2.drawImage(image, screenX, screenY, config.tileSize(), config.tileSize(), null);
    }

    public int getWorldX() {
        return worldX;
    }
    public int getWorldY() {
        return worldY;
    }
    public int getScreenX() {
        return screenX;
    }
    public int getScreenY() {
        return screenY;
    }
}

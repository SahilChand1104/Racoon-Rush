package RacoonRush.entity;

import RacoonRush.entity.enemy.Enemy;
import RacoonRush.game.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EnumMap;

public class Player extends Entity implements Manager {
    public final int screenX, screenY, invincibilityDuration;
    private int score, invincibilityFrames;

    public Player(GamePanel gamePanel, int worldX, int worldY, int speed, Move direction, ArrayList<EnumMap<Move, BufferedImage>> images) {
        super(gamePanel, worldX, worldY, speed, direction, images);

        // Centered in the middle of the map
        Config config = gamePanel.getConfig();
        screenX = config.screenWidth() / 2 - config.tileSize() / 2;
        screenY = config.screenHeight() / 2 - config.tileSize() / 2;
        score = 0;
        invincibilityDuration = 120;
    }

    @Override
    public void update() {
        KeyHandler keyHandler = gamePanel.getKeyHandler();
        CollisionDetector collisionDetector = gamePanel.getCollisionDetector();
        if (!keyHandler.get(Move.UP) && !keyHandler.get(Move.DOWN) && !keyHandler.get(Move.LEFT) && !keyHandler.get(Move.RIGHT)) {
            return;
        }

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

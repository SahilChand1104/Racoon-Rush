package RacoonRush.entity;

import RacoonRush.game.Config;
import RacoonRush.game.GamePanel;
import RacoonRush.game.ImageLoader;
import RacoonRush.game.Move;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Donut extends Entity {
    final GamePanel gamePanel;

    public Donut(GamePanel gamePanel, int x, int y) {
        this.gamePanel = gamePanel;
        this.worldX = x;
        this.worldY = y;
        this.speed = 0;
        this.dir = Move.DOWN;
        Config config = gamePanel.getConfig();
        this.hitbox = new Rectangle(config.tileSize(), config.tileSize());
    }

    public void draw(Graphics2D g2, int x, int y, int animationFrame) {
        Config config = gamePanel.getConfig();
        ImageLoader imageLoader = gamePanel.getImageLoader();
        BufferedImage image = imageLoader.getDonutImage(animationFrame);
        g2.drawImage(image, x, y, config.tileSize(), config.tileSize(), null);
    }
}
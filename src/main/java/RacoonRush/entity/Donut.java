package RacoonRush.entity;

import RacoonRush.game.Config;
import RacoonRush.game.GamePanel;
import RacoonRush.game.ImageLoader;
import RacoonRush.game.Move;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Donut extends Entity{
    final GamePanel gp;
    final Config config;

    public Donut(GamePanel gp, int x, int y) {
        this.config = gp.getConfig();
        this.gp = gp;
        this.worldX = x;
        this.worldY = y;
        this.speed = 0;
        this.dir = Move.DOWN;
        this.hitbox = new Rectangle(x, y, 32, 32);
    }

    public void draw(Graphics2D g2, int x, int y, int animationFrame) {
        ImageLoader imageLoader = gp.getImageLoader();
        BufferedImage image = imageLoader.getDonutImage(animationFrame);
        g2.drawImage(image, x, y, config.tileSize(), config.tileSize(), null);
    }
}
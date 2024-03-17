package RacoonRush.entity.enemy;

import RacoonRush.entity.Entity;
import RacoonRush.game.Config;
import RacoonRush.game.GamePanel;
import RacoonRush.game.Move;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EnumMap;

public abstract class Enemy extends Entity {
    private final Config config;
    protected int damage, cooldown;
    public Enemy(GamePanel gamePanel, int worldX, int worldY, int speed, int damage, int cooldown, Rectangle hitbox, ArrayList<EnumMap<Move, BufferedImage>> images) {
        this.gamePanel = gamePanel;
        config = gamePanel.getConfig();
        this.worldX = worldX;
        this.worldY = worldY;
        this.speed = speed;
        this.damage = damage;
        this.cooldown = cooldown;
        this.hitbox = hitbox;
        this.images = images;
    }
    public abstract void ability();

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

}

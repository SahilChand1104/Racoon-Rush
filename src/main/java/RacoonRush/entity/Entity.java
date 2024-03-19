package RacoonRush.entity;

import RacoonRush.game.Config;
import RacoonRush.game.GamePanel;
import RacoonRush.game.Move;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EnumMap;

public abstract class Entity {
    protected GamePanel gamePanel;
    protected int worldX, worldY, speed;
    protected Move direction;
    protected ArrayList<EnumMap<Move, BufferedImage>> images;
    protected Rectangle hitbox;

    public Entity(GamePanel gamePanel, int worldX, int worldY, int speed, Move direction, ArrayList<EnumMap<Move, BufferedImage>> images) {
        this.gamePanel = gamePanel;
        this.worldX = worldX;
        this.worldY = worldY;
        this.speed = speed;
        this.direction = direction;
        this.images = images;
        Config config = gamePanel.getConfig();
        hitbox = new Rectangle(config.tileSize() / 6, config.tileSize() / 3, config.tileSize() * 2 / 3, config.tileSize() * 2 / 3);
    }

    public abstract void update();

    public abstract void draw(Graphics2D g2);

    public int getWorldX() {
        return worldX;
    }
    public int getWorldY() {
        return worldY;
    }

    public int getSpeed() {
        return speed;
    }
    public Move getDirection() {
        return direction;
    }

    public BufferedImage getImage(int index, Move move) {
        return images.get(index).get(move);
    }

    public Rectangle getWorldHitbox() {
        return new Rectangle(worldX + hitbox.x, worldY + hitbox.y, hitbox.width, hitbox.height);
    }

    public int leftColumn(int offsetX) {
        return (worldX + hitbox.x + offsetX) / gamePanel.getConfig().tileSize();
    }

    public int rightColumn(int offsetX) {
        return (worldX + hitbox.x + hitbox.width + offsetX) / gamePanel.getConfig().tileSize();
    }

    public int topRow(int offsetY) {
        return (worldY + hitbox.y + offsetY) / gamePanel.getConfig().tileSize();
    }

    public int bottomRow(int offsetY) {
        return (worldY + hitbox.y + hitbox.height + offsetY) / gamePanel.getConfig().tileSize();
    }
}

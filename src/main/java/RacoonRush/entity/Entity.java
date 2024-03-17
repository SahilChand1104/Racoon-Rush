package RacoonRush.entity;

import RacoonRush.game.GamePanel;
import RacoonRush.game.Move;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EnumMap;

public abstract class Entity {
    protected GamePanel gamePanel;
    protected int worldX, worldY, speed;

    protected Move dir;
    protected Rectangle hitbox;

    protected ArrayList<EnumMap<Move, BufferedImage>> images;

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

    public BufferedImage getImage(int index, Move move) {
        return images.get(index).get(move);
    }
}

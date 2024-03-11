package RacoonRush.entity;

import RacoonRush.game.Move;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {
    public int worldX, worldY;
    public int speed;

    public BufferedImage[][] images;

    public Move dir;
    public Rectangle hitbox;

    public abstract int leftColumn();
    public abstract int rightColumn();
    public abstract int topRow();
    public abstract int bottomRow();
}

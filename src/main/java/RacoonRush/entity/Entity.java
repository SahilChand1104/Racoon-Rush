package RacoonRush.entity;

import RacoonRush.game.Move;

import java.awt.*;

public abstract class Entity {
    public int worldX, worldY;
    public int speed;

    public Move dir;
    public Rectangle hitbox;
}

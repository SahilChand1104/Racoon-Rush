package RacoonRush.entity.enemy;

import RacoonRush.game.GamePanel;
import RacoonRush.game.Move;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EnumMap;

public class Lurker extends Enemy {
    public Lurker(GamePanel gamePanel, int worldX, int worldY, int speed, int damage, int cooldown, Rectangle hitbox, ArrayList<EnumMap<Move, BufferedImage>> images) {
        super(gamePanel, worldX, worldY, speed, damage, cooldown, hitbox, images);
    }

    @Override
    public void ability() {

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g2) {

    }
}

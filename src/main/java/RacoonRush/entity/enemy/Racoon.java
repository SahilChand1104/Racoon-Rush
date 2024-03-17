package RacoonRush.entity.enemy;

import RacoonRush.entity.Player;
import RacoonRush.game.CollisionDetector;
import RacoonRush.game.Config;
import RacoonRush.game.GamePanel;
import RacoonRush.game.Move;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EnumMap;

public class Racoon extends Enemy {
    public Racoon(GamePanel gamePanel, int worldX, int worldY, int speed, int damage, int cooldown, Rectangle hitbox, ArrayList<EnumMap<Move, BufferedImage>> images) {
        super(gamePanel, worldX, worldY, speed, damage, cooldown, hitbox, images);
        dir = Move.DOWN;
    }

    @Override
    public void ability() {

    }

    @Override
    public void update() {
        CollisionDetector collisionDetector = gamePanel.getCollisionDetector();
        if (collisionDetector.move(this, dir)) {
            switch (dir) {
                case UP:
                    worldY -= speed;
                    break;
                case DOWN:
                    worldY += speed;
                    break;
                case LEFT:
                    worldX -= speed;
                    break;
                case RIGHT:
                    worldX += speed;
                    break;
            }
        } else {
            dir = collisionDetector.nextDirection(this);
            System.out.println("New direction: " + dir);
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        Config config = gamePanel.getConfig();
        Player player = gamePanel.getPlayer();
        g2.drawImage(getImage(gamePanel.getPlayerAnimationFrame(), dir),
                worldX - hitbox.width - player.getWorldX() + player.getScreenX(),
                worldY - hitbox.height - player.getWorldY() + player.getScreenX(),
                config.tileSize(),
                config.tileSize(),
                null
        );
    }


}

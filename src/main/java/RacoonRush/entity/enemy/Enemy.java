package RacoonRush.entity.enemy;

import RacoonRush.entity.Entity;
import RacoonRush.entity.Player;
import RacoonRush.game.CollisionDetector;
import RacoonRush.game.Config;
import RacoonRush.game.GamePanel;
import RacoonRush.game.Move;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EnumMap;

public abstract class Enemy extends Entity {
    protected int damage, abilityDuration, abilityCooldownDuration, abilityCooldownFrames;
    protected boolean abilityActive;

    public Enemy(GamePanel gamePanel, int worldX, int worldY, int speed, Move direction, ArrayList<EnumMap<Move, BufferedImage>> images,
                 int damage, int abilityDuration, int abilityCooldownDuration) {
        super(gamePanel, worldX, worldY, speed, direction, images);
        this.damage = damage;
        this.abilityDuration = abilityDuration;
        this.abilityCooldownDuration = abilityCooldownDuration;
        abilityCooldownFrames = abilityCooldownDuration;
        abilityActive = false;
    }

    public abstract void activateAbility();
    public abstract void deactivateAbility();

    @Override
    public void update() {
        CollisionDetector collisionDetector = gamePanel.getCollisionDetector();
        if (collisionDetector.move(this, direction)) {
            switch (direction) {
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
            direction = collisionDetector.nextDirection(this);
        }
        if (abilityActive) {
            abilityDuration--;
            if (abilityDuration == 0) {
                deactivateAbility();
                abilityActive = false;
                abilityCooldownFrames = abilityCooldownDuration;
            }
        } else {
            abilityCooldownFrames--;
            if (abilityCooldownFrames == 0) {
                activateAbility();
                abilityActive = true;
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        Config config = gamePanel.getConfig();
        Player player = gamePanel.getEntityManager().getPlayer();
        g2.drawImage(getImage(gamePanel.getPlayerAnimationFrame(), direction),
                worldX - player.getWorldX() + player.getScreenX(),
                worldY - player.getWorldY() + player.getScreenY(),
                config.tileSize(),
                config.tileSize(),
                null
        );
    }

    public int getDamage() {
        return damage;
    }
}

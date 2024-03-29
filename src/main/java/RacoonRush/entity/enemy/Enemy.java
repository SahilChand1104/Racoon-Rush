package RacoonRush.entity.enemy;

import RacoonRush.entity.Entity;
import RacoonRush.entity.Player;
import RacoonRush.util.CollisionDetector;
import RacoonRush.util.Config;
import RacoonRush.game.GamePanel;
import RacoonRush.util.Move;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EnumMap;

/**
 * Abstract class for the enemies in the game
 * Enemy types are
 */
public abstract class Enemy extends Entity {
    protected final int damage, abilityDuration, abilityCooldownDuration;
    protected int abilityFrames, abilityCooldownFrames;
    protected boolean abilityActive;

    /**
     * Constructor for the enemy
     * @param gamePanel the gamePanel
     * @param worldX the x coordinate in the world
     * @param worldY the y coordinate in the world
     * @param speed the speed of the entity
     * @param direction the direction the entity is facing
     * @param images the images of the entity
     * @param damage the damage the enemy deals
     * @param abilityDuration the duration of the ability
     * @param abilityCooldownDuration the cooldown duration of the ability
     */
    public Enemy(GamePanel gamePanel, int worldX, int worldY, int speed, Move direction, ArrayList<EnumMap<Move, BufferedImage>> images,
                 int damage, int abilityDuration, int abilityCooldownDuration) {
        super(gamePanel, worldX, worldY, speed, direction, images);
        this.damage = damage;
        this.abilityDuration = abilityDuration;
        this.abilityCooldownDuration = abilityCooldownDuration;
        abilityFrames = abilityDuration;
        abilityCooldownFrames = abilityCooldownDuration;
        abilityActive = false;
    }

    /**
     * Method to activate its ability
     */
    public abstract void activateAbility();

    /**
     * Method to deactivate its ability
     */
    public abstract void deactivateAbility();

    /**
     * Updates the enemy's movement
     */
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
            abilityFrames--;
            if (abilityFrames == 0) {
                deactivateAbility();
                abilityActive = false;
                abilityCooldownFrames = abilityCooldownDuration;
            }
        } else {
            abilityCooldownFrames--;
            if (abilityCooldownFrames == 0) {
                activateAbility();
                abilityActive = true;
                abilityFrames = abilityDuration;
            }
        }
    }

    /**
     * Draws the enemy
     * @param g2 the graphics object to draw the enemy
     */
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

    /**
     * Returns the damage the enemy deals
     * @return the damage the enemy deals
     */
    public int getDamage() {
        return damage;
    }
}

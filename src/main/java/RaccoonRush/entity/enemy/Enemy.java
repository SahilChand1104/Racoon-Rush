package RaccoonRush.entity.enemy;

import RaccoonRush.entity.Entity;
import RaccoonRush.entity.Player;
import RaccoonRush.util.CollisionDetector;
import RaccoonRush.game.GamePanel;
import RaccoonRush.util.Move;

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
     *
     * @param images                  the images of the entity
     * @param worldX                  the x coordinate in the world
     * @param worldY                  the y coordinate in the world
     * @param speed                   the speed of the entity
     * @param direction               the direction the entity is facing
     * @param damage                  the damage the enemy deals
     * @param abilityDuration         the duration of the ability
     * @param abilityCooldownDuration the cooldown duration of the ability
     */
    public Enemy(ArrayList<EnumMap<Move, BufferedImage>> images, int worldX, int worldY, int speed, Move direction,
                 int damage, int abilityDuration, int abilityCooldownDuration) {
        super(images, worldX, worldY, speed, direction);
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
        CollisionDetector collisionDetector = GamePanel.getInstance().getCollisionDetector();
        if (collisionDetector.move(this, direction)) {
            worldY += switch(direction) {
                case UP -> -speed;
                case DOWN -> speed;
                default -> 0;
            };
            worldX += switch(direction) {
                case LEFT -> -speed;
                case RIGHT -> speed;
                default -> 0;
            };
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
        Player player = GamePanel.getInstance().getEntityManager().getPlayer();
        g2.drawImage(getImage(GamePanel.getInstance().getPlayerAnimationFrame(), direction),
                worldX - player.getWorldX() + player.getScreenX(),
                worldY - player.getWorldY() + player.getScreenY(),
                GamePanel.config.tileSize(),
                GamePanel.config.tileSize(),
                null
        );
    }

    /**
     * Set the direction the enemy is facing
     * @param direction the direction to set
     */
    public void setDirection(Move direction) {
        this.direction = direction;
    }

    /**
     * Returns the damage the enemy deals
     * @return the damage the enemy deals
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Returns the ability active status
     * @return boolean indicating if the ability is active
     */
    public boolean isAbilityActive() {
        return abilityActive;
    }

    /**
     * Returns the ability frames remaining
     * @return the ability frames remaining
     */
    public int getAbilityFrames() {
        return abilityFrames;
    }

    /**
     * Returns the ability cooldown frames remaining
     * @return the ability cooldown frames remaining
     */
    public int getAbilityCooldownFrames() {
        return abilityCooldownFrames;
    }
}

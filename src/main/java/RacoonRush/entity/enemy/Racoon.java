package RacoonRush.entity.enemy;

import RacoonRush.game.GamePanel;
import RacoonRush.util.Move;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EnumMap;

/**
 * Class for the racoon enemy
 */
public class Racoon extends Enemy {
    /**
     * Constructor for the racoon enemy
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
    public Racoon(GamePanel gamePanel, int worldX, int worldY, int speed, Move direction, ArrayList<EnumMap<Move, BufferedImage>> images,
                  int damage, int abilityDuration, int abilityCooldownDuration) {
        super(gamePanel, worldX, worldY, speed, direction, images, damage, abilityDuration, abilityCooldownDuration);
    }

    /**
     * Method to activate its ability
     */
    @Override
    public void activateAbility() {
        speed *= 2;
    }

    /**
     * Method to deactivate its ability
     */
    @Override
    public void deactivateAbility() {
        speed /= 2;
    }

}

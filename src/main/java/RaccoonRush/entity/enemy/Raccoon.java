package RaccoonRush.entity.enemy;

import RaccoonRush.game.GamePanel;
import RaccoonRush.util.Move;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EnumMap;

/**
 * Class for the racoon enemy
 */
public class Raccoon extends Enemy {
    /**
     * Constructor for the racoon enemy
     *
     * @param gamePanel               the gamePanel
     * @param images                  the images of the entity
     * @param worldX                  the x coordinate in the world
     * @param worldY                  the y coordinate in the world
     * @param speed                   the speed of the entity
     * @param direction               the direction the entity is facing
     * @param damage                  the damage the enemy deals
     * @param abilityDuration         the duration of the ability
     * @param abilityCooldownDuration the cooldown duration of the ability
     */
    public Raccoon(GamePanel gamePanel, ArrayList<EnumMap<Move, BufferedImage>> images, int worldX, int worldY, int speed, Move direction,
                   int damage, int abilityDuration, int abilityCooldownDuration) {
        super(gamePanel, images, worldX, worldY, speed, direction, damage, abilityDuration, abilityCooldownDuration);
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

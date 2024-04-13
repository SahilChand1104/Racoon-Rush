package RaccoonRush.entity.enemy;

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
     * @param images                  the images of the entity
     * @param worldX                  the x coordinate in the world
     * @param worldY                  the y coordinate in the world
     */
    public Raccoon(ArrayList<EnumMap<Move, BufferedImage>> images, int worldX, int worldY) {
        super(images, worldX, worldY);
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

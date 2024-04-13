package RaccoonRush.entity.enemy;

import RaccoonRush.util.Move;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EnumMap;

public class Caffeine extends Enemy {
    public Caffeine(ArrayList<EnumMap<Move, BufferedImage>> images, int worldX, int worldY) {
        super(images, worldX, worldY);
    }

    @Override
    public void activateAbility() {
        speed *= 2;
    }

    @Override
    public void deactivateAbility() {
        speed /= 2;
    }
}

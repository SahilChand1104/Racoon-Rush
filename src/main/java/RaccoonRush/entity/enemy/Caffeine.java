package RaccoonRush.entity.enemy;

import RaccoonRush.game.GamePanel;
import RaccoonRush.util.Move;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EnumMap;

public class Caffeine extends Enemy {
    public Caffeine(GamePanel gamePanel, ArrayList<EnumMap<Move, BufferedImage>> images, int worldX, int worldY, int speed, Move direction,
                    int damage, int abilityDuration, int abilityCooldownDuration) {
        super(gamePanel, images, worldX, worldY, speed, direction, damage, abilityDuration, abilityCooldownDuration);
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

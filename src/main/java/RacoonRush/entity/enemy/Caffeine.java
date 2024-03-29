package RacoonRush.entity.enemy;

import RacoonRush.game.GamePanel;
import RacoonRush.util.Move;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EnumMap;

public class Caffeine extends Enemy {
    public Caffeine(GamePanel gamePanel, int worldX, int worldY, int speed, Move direction, ArrayList<EnumMap<Move, BufferedImage>> images,
                  int damage, int abilityDuration, int abilityCooldownDuration) {
        super(gamePanel, worldX, worldY, speed, direction, images, damage, abilityDuration, abilityCooldownDuration);
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

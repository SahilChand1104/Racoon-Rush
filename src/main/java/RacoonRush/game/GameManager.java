package RacoonRush.game;

import java.awt.*;

/**
 * Interface for the game manager
 * Game managers update and draw one or more objects, this can be the menu, player, map, etc.
 */
public interface GameManager {
    /**
     * Update the objects managed by the game manager
     */
    void update();

    /**
     * Draw the objects managed by the game manager
     * @param g2 Graphics2D object to draw the objects onto
     */
    void draw(Graphics2D g2);
}

package RaccoonRush.game.menu.component;

import RaccoonRush.game.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Represents a component in the menu
 */
public class MenuBG extends MenuComponent {

    protected final BufferedImage image;
    /**
    * Constructor for a Menu Background
    *
    * @param gamePanel the gamePanel
    * @param x         the x position of the component
    * @param y         the y position of the component
    */
    public MenuBG(GamePanel gamePanel, int x, int y) {
        super(gamePanel, x, y);
        this.image = null;
    }

    /**
     * Constructor for a Menu Background
     * @param gamePanel
     * @param x
     * @param y
     * @param image
     */
    public MenuBG(GamePanel gamePanel, int x, int y, BufferedImage image) {
        super(gamePanel, x, y);
        this.image = image;
    }

    /**
    * Draws the component
    * @param g2 the graphics2D object
    */
    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(image, x, y, null);
    }

    /**
    * Returns the type of the component
    * @return ComponentType
    */
    @Override
    public ComponentType getType() {
        return ComponentType.BG;
    }
}

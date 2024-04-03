package RaccoonRush.game.menu.component;

import RaccoonRush.game.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Represents a component in the menu
 */
public class MenuComponent {
    protected final GamePanel gamePanel;
    protected final int x, y;
    protected final BufferedImage image;

    /**
     * Constructor for the MenuComponent
     *
     * @param gamePanel the gamePanel
     * @param x         the x position of the component
     * @param y         the y position of the component
     * @param image     the image of the component
     */
    public MenuComponent(GamePanel gamePanel, int x, int y, BufferedImage image) {
        this.gamePanel = gamePanel;
        this.x = x;
        this.y = y;
        this.image = image;
    }

    /**
     * Draws the component
     * @param g2 the graphics2D object
     */
    public void draw(Graphics2D g2) {
        g2.drawImage(image, x, y, null);
    }

    /**
     * Returns the x position of the component
     * @return int
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y position of the component
     * @return int
     */
    public int getY() {
        return y;
    }
}

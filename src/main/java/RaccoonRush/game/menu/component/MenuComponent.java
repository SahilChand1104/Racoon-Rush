package RaccoonRush.game.menu.component;

import java.awt.*;

/**
 * Represents a component in the menu
 */
public class MenuComponent {
    protected final int x, y;

    /**
     * Constructor for the MenuComponent
     *
     * @param x         the x position of the component
     * @param y         the y position of the component
     */
    public MenuComponent(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Draws the component
     * @param g2 the graphics2D object
     */
    public void draw(Graphics2D g2) {
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

    /**
     * Returns the type of the component
     * @return ComponentType
     */
    public ComponentType getType() { return ComponentType.NONE;}
}

package RaccoonRush.game.menu.component;

import RaccoonRush.game.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Represents a button component in the menu
 */
public class MenuButton extends MenuComponent {
    protected final BufferedImage selectedImage;
    private final ButtonType buttonType;
    private boolean selected;

    /**
     * Constructor for the MenuButton
     *
     * @param gamePanel     the gamePanel
     * @param x             the x position of the component
     * @param y             the y position of the component
     * @param image         the image of the component
     * @param selectedImage the selected image of the component
     * @param buttonType    the type of the button component
     */
    public MenuButton(GamePanel gamePanel, int x, int y, BufferedImage image, BufferedImage selectedImage, ButtonType buttonType) {
        super(gamePanel, x, y, image);
        this.selectedImage = selectedImage;
        this.buttonType = buttonType;
    }

    /**
     * Draws the component
     * @param g2 the graphics2D object
     */
    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(selected ? selectedImage : image, x, y, null);
    }

    /**
     * Updates the components to be selected or not
     * @param selected the boolean value to set the component to
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Returns whether the component is selected
     * @return boolean
     */
    public boolean isSelected() {return selected;}

    /**
     * Returns the type of the component
     * @return ComponentType
     */
    public ButtonType getButtonType() {
        return buttonType;
    }
}

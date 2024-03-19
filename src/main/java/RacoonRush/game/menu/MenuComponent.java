package RacoonRush.game.menu;

import RacoonRush.game.GamePanel;
import RacoonRush.game.GameState;
import RacoonRush.game.ImageLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class MenuComponent {

    private final GamePanel gamePanel;

    private final int x;
    private final int y;

    private final ComponentType type;

    private  BufferedImage image;


    private boolean selected;

    /**
     * Constructor for the MenuComponent
     * @param gamePanel the gamePanel
     * @param x the x position of the component
     * @param y the y position of the component
     * @param type the type of the component
     * @param image the image of the component
     */
    public MenuComponent(GamePanel gamePanel, int x, int y, ComponentType type, BufferedImage image) {
        this.gamePanel = gamePanel;

        this.x = x;
        this.y = y;
        this.type = type;

        selected = false;

        this.image = image;

        if (type == ComponentType.PLAY) {
            selected = true;
        }


    }

    /**
     * Draws the component
     * @param g2 the graphics2D object
     */
    public void draw(Graphics2D g2) {
        ImageLoader imageLoader = gamePanel.getImageLoader();

        // Draw the component according to its type since different components have different sizes and positions
        if (type == ComponentType.BG) {
            g2.drawImage(image, x, y, Math.max(gamePanel.getHeight(), gamePanel.getWidth()) , Math.max(gamePanel.getHeight(), gamePanel.getWidth()), null);
        } else if (type == ComponentType.BANNER) {
            g2.drawImage(image, x, y, gamePanel.getWidth(), image.getHeight(),null);
        } else if (type == ComponentType.PLAY) {
            if (selected) {
                g2.drawImage(imageLoader.getMenuSelectedImage(type), x, y, null);
            } else {
                g2.drawImage(imageLoader.getMenuImage(type), x, y, null);
            }
        } else if (type == ComponentType.SETTINGS) {
            int offset = image.getWidth()/2;
            int xpos = gamePanel.getWidth()/2 - offset+15;
            if (selected) {
                g2.drawImage(imageLoader.getMenuSelectedImage(type), xpos, y, null);
            } else {
                g2.drawImage(imageLoader.getMenuImage(type), xpos, y, null);
            }
        }
    }

    /**
     * Updates the components to be selected or not
     * @param selected
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Returns the type of the component
     * @return ComponentType
     */
    public ComponentType getType() {
        return type;
    }

    /**
     * Does an action depending on the type of the component
     * If the component is a play button, it starts the game
     * If the component is a settings button, it opens the settings menu
     */
    public void doAction() {
        if (type == ComponentType.PLAY) {
            if (!gamePanel.isGameRunning()) {
                gamePanel.startGame();
            } else {
                gamePanel.closeMenu();
            }
        } else if (type == ComponentType.SETTINGS) {
            gamePanel.getMenuUI().setMenuState(MenuState.SETTINGS);
        }
    }
}

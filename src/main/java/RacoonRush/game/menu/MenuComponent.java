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

    public void draw(Graphics2D g2) {
        ImageLoader imageLoader = gamePanel.getImageLoader();
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

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public ComponentType getType() {
        return type;
    }

    private BufferedImage loadImage(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

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

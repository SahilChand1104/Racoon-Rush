package RacoonRush.game.menu;

import RacoonRush.game.GamePanel;

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

    public MenuComponent(GamePanel gamePanel, int x, int y, ComponentType type) {
        this.gamePanel = gamePanel;

        this.x = x;
        this.y = y;
        this.type = type;

        selected = false;

        if (type == ComponentType.BG) {
            image = loadImage("/menu/menu_bg.png");
        } else if (type == ComponentType.BANNER) {
            image = loadImage("/menu/menu_title_v2.png");
        } else if (type == ComponentType.PLAY) {
            image = loadImage("/menu/menu_label_play_0.png");
        } else if (type == ComponentType.SETTINGS) {
            image = loadImage("/menu/menu_label_settings_0.png");
        } else {
            image = null;
        }

    }

    public void update() {
        if (type == ComponentType.PLAY) {
            if (selected) {
                image = loadImage("/menu/menu_label_play_1.png");
            } else {
                image = loadImage("/menu/menu_label_play_0.png");
            }
        } else if (type == ComponentType.SETTINGS) {
            if (selected) {
                image = loadImage("/menu/menu_label_settings_1.png");
            } else {
                image = loadImage("/menu/menu_label_settings_0.png");
            }
        }
    }

    public void draw(Graphics2D g2) {
        if (type == ComponentType.BG) {
            g2.drawImage(image, x, y, Math.max(gamePanel.getHeight(), gamePanel.getWidth()) , Math.max(gamePanel.getHeight(), gamePanel.getWidth()), null);
        } else if (type == ComponentType.BANNER) {
            g2.drawImage(image, x, y, gamePanel.getWidth(), image.getHeight(),null);
        } else if (type == ComponentType.PLAY || type == ComponentType.SETTINGS) {
            g2.drawImage(image, x, y, null);
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

}

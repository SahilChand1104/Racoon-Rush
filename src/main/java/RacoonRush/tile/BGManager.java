package RacoonRush.tile;

import RacoonRush.game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Background manager class
 */
public class BGManager extends MapManager{

    private BufferedImage background;

    public BGManager(GamePanel gp) {
        super(gp);
        try {
            background = ImageIO.read(getClass().getResourceAsStream("/maps/background_img.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(background, getScreenCoordinate(0, getPlayerWorldX(), getPlayerScreenX()), getScreenCoordinate(0, getPlayerWorldY(), getPlayerScreenY()), 768, 768, null);
        g2.drawImage(background, getScreenCoordinate(0, getPlayerWorldX(), getPlayerScreenX())+768, getScreenCoordinate(0, getPlayerWorldY(), getPlayerScreenY()), 768, 768, null);
        g2.drawImage(background, getScreenCoordinate(0, getPlayerWorldX(), getPlayerScreenX()), getScreenCoordinate(0, getPlayerWorldY(), getPlayerScreenY())+768, 768, 768, null);
        g2.drawImage(background, getScreenCoordinate(0, getPlayerWorldX(), getPlayerScreenX())+768, getScreenCoordinate(0, getPlayerWorldY(), getPlayerScreenY())+768, 768, 768, null);
    }





}

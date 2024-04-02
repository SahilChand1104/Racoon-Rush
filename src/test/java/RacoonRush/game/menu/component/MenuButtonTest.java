package RacoonRush.game.menu.component;
import RacoonRush.game.GamePanel;
import RacoonRush.game.menu.component.MenuButton;
import RacoonRush.game.menu.component.ButtonType;
import org.junit.jupiter.api.Assertions;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MenuButtonTest {

    @org.junit.jupiter.api.Test
    void testGetButtonTypePLAY() {
        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        BufferedImage selectedImage = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        GamePanel gamePanel = new GamePanel();

        MenuButton menuButton = new MenuButton(gamePanel, 0, 0, image, selectedImage, ButtonType.PLAY);
        Assertions.assertEquals(ButtonType.PLAY, menuButton.getButtonType());
    }

    @org.junit.jupiter.api.Test
    void testGetButtonTypeSETTINGS() {
        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        BufferedImage selectedImage = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        GamePanel gamePanel = new GamePanel();

        MenuButton menuButton = new MenuButton(gamePanel, 0, 0, image, selectedImage, ButtonType.SETTINGS);
        Assertions.assertEquals(ButtonType.SETTINGS, menuButton.getButtonType());
    }


    @org.junit.jupiter.api.Test
    void testSetSelected() {
        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        BufferedImage selectedImage = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        GamePanel gamePanel = new GamePanel();

        MenuButton menuButton = new MenuButton(gamePanel, 0, 0, image, selectedImage, ButtonType.PLAY);

        menuButton.setSelected(true);
        Assertions.assertTrue(menuButton.isSelected());

        menuButton.setSelected(false);
        Assertions.assertFalse(menuButton.isSelected());
    }

    @org.junit.jupiter.api.Test
    void testDrawPosition() {
        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        BufferedImage selectedImage = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        GamePanel gamePanel = new GamePanel();

        MenuButton menuButton = new MenuButton(gamePanel, 100, 20, image, selectedImage, ButtonType.PLAY);

        Assertions.assertEquals(100, menuButton.getX());
        Assertions.assertEquals(20, menuButton.getY());

        MenuButton menuButton2 = new MenuButton(gamePanel, -50, 0, image, selectedImage, ButtonType.PLAY);

        Assertions.assertEquals(-50, menuButton2.getX());
        Assertions.assertEquals(0, menuButton2.getY());
    }

    @org.junit.jupiter.api.Test
    void testDrawImage() {
        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        BufferedImage selectedImage = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        GamePanel gamePanel = new GamePanel();

        MenuButton menuButton = new MenuButton(gamePanel, 0, 0, image, selectedImage, ButtonType.PLAY);
        Graphics2D g2 = image.createGraphics();
        menuButton.draw(g2);

        Assertions.assertEquals(image, menuButton.image);
        Assertions.assertEquals(0, menuButton.getX());
        Assertions.assertEquals(0, menuButton.getY());

        g2.dispose();
    }

    @org.junit.jupiter.api.Test
    void testDrawSelectedImage() {
        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        BufferedImage selectedImage = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        GamePanel gamePanel = new GamePanel();

        MenuButton menuButton = new MenuButton(gamePanel, 0, 0, image, selectedImage, ButtonType.PLAY);
        Graphics2D g2 = selectedImage.createGraphics();
        menuButton.setSelected(true);
        menuButton.draw(g2);

        Assertions.assertEquals(selectedImage, menuButton.selectedImage);
        Assertions.assertEquals(0, menuButton.getX());
        Assertions.assertEquals(0, menuButton.getY());

        g2.dispose();
    }

}

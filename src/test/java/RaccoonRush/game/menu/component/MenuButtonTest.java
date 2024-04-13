package RaccoonRush.game.menu.component;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class MenuButtonTest {
    @Test
    void testGetButtonTypePLAY() {
        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        BufferedImage selectedImage = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);

        MenuButton menuButton = new MenuButton(0, 0, image, selectedImage, ButtonType.PLAY);
        assertEquals(ButtonType.PLAY, menuButton.getButtonType());
    }

    @Test
    void testGetButtonTypeSETTINGS() {
        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        BufferedImage selectedImage = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);

        MenuButton menuButton = new MenuButton(0, 0, image, selectedImage, ButtonType.SETTINGS);
        assertEquals(ButtonType.SETTINGS, menuButton.getButtonType());
    }


    @Test
    void testSetSelected() {
        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        BufferedImage selectedImage = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);

        MenuButton menuButton = new MenuButton(0, 0, image, selectedImage, ButtonType.PLAY);

        menuButton.setSelected(true);
        assertTrue(menuButton.isSelected());

        menuButton.setSelected(false);
        assertFalse(menuButton.isSelected());
    }

    @Test
    void testDrawPosition() {
        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        BufferedImage selectedImage = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);

        MenuButton menuButton = new MenuButton(100, 20, image, selectedImage, ButtonType.PLAY);

        assertEquals(100, menuButton.getX());
        assertEquals(20, menuButton.getY());

        MenuButton menuButton2 = new MenuButton(-50, 0, image, selectedImage, ButtonType.PLAY);

        assertEquals(-50, menuButton2.getX());
        assertEquals(0, menuButton2.getY());
    }

    @Test
    void testDrawImage() {
        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        BufferedImage selectedImage = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);

        MenuButton menuButton = new MenuButton(0, 0, image, selectedImage, ButtonType.PLAY);
        Graphics2D g2 = image.createGraphics();
        menuButton.draw(g2);

        assertEquals(image, menuButton.getUnselectedImage());
        assertEquals(0, menuButton.getX());
        assertEquals(0, menuButton.getY());

        g2.dispose();
    }

    @Test
    void testDrawSelectedImage() {
        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        BufferedImage selectedImage = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);

        MenuButton menuButton = new MenuButton(0, 0, image, selectedImage, ButtonType.PLAY);
        Graphics2D g2 = selectedImage.createGraphics();
        menuButton.setSelected(true);
        menuButton.draw(g2);

        assertEquals(selectedImage, menuButton.getSelectedImage());
        assertEquals(0, menuButton.getX());
        assertEquals(0, menuButton.getY());

        g2.dispose();
    }

}

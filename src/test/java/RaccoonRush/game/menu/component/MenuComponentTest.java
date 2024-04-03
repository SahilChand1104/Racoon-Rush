package RaccoonRush.game.menu.component;

import RaccoonRush.game.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MenuComponentTest {
    @Test
    void testGetComponentPosition() {
        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        GamePanel gamePanel = new GamePanel();

        MenuComponent menuComponent = new MenuComponent(gamePanel, 0, 0, image);
        assertEquals(0, menuComponent.getX());
        assertEquals(0, menuComponent.getY());
    }

    @Test
    void testGetComponentImage() {
        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        GamePanel gamePanel = new GamePanel();

        MenuComponent menuComponent = new MenuComponent(gamePanel, 0, 0, image);
        assertEquals(image, menuComponent.image);
    }

    @Test
    void testDrawComponent() {
        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        GamePanel gamePanel = new GamePanel();

        MenuComponent menuComponent = new MenuComponent(gamePanel, 0, 0, image);
        Graphics2D g2 = image.createGraphics();
        menuComponent.draw(g2);

        assertEquals(image, menuComponent.image);
        assertEquals(0, menuComponent.getX());
        assertEquals(0, menuComponent.getY());

        g2.dispose();
    }

}

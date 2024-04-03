package RaccoonRush.game.menu.component;
import RaccoonRush.game.GamePanel;
import org.junit.jupiter.api.Assertions;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MenuComponentTest {

    @org.junit.jupiter.api.Test
    void testGetComponentPosition() {
        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        GamePanel gamePanel = new GamePanel();

        MenuComponent menuComponent = new MenuComponent(gamePanel, 0, 0, image);
        Assertions.assertEquals(0, menuComponent.getX());
        Assertions.assertEquals(0, menuComponent.getY());
    }

    @org.junit.jupiter.api.Test
    void testGetComponentImage() {
        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        GamePanel gamePanel = new GamePanel();

        MenuComponent menuComponent = new MenuComponent(gamePanel, 0, 0, image);
        Assertions.assertEquals(image, menuComponent.image);
    }

    @org.junit.jupiter.api.Test
    void testDrawComponent() {
        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        GamePanel gamePanel = new GamePanel();

        MenuComponent menuComponent = new MenuComponent(gamePanel, 0, 0, image);
        Graphics2D g2 = image.createGraphics();
        menuComponent.draw(g2);

        Assertions.assertEquals(image, menuComponent.image);
        Assertions.assertEquals(0, menuComponent.getX());
        Assertions.assertEquals(0, menuComponent.getY());

        g2.dispose();
    }

}

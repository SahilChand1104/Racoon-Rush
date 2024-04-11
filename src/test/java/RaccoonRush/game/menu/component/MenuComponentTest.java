package RaccoonRush.game.menu.component;

import RaccoonRush.game.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MenuComponentTest {
    @Test
    void testGetComponentPosition() {
        GamePanel gamePanel = new GamePanel();

        MenuComponent menuComponent = new MenuComponent(gamePanel, 0, 0);
        assertEquals(0, menuComponent.getX());
        assertEquals(0, menuComponent.getY());
    }

    @Test
    void testGetComponentType() {
        GamePanel gamePanel = new GamePanel();

        MenuComponent menuComponent = new MenuComponent(gamePanel, 0, 0);
        assertEquals(ComponentType.NONE, menuComponent.getType());
    }

}

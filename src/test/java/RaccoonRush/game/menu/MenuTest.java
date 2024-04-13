package RaccoonRush.game.menu;

import RaccoonRush.game.menu.component.MenuBanner;
import RaccoonRush.game.menu.component.MenuBG;
import RaccoonRush.game.menu.component.MenuButton;
import RaccoonRush.game.menu.component.MenuLabel;
import RaccoonRush.game.GamePanel;
import RaccoonRush.game.menu.component.ButtonType;
import RaccoonRush.game.menu.component.MenuComponent;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class MenuTest {
    @Test
    void testMenuCreated() {
        GamePanel gamePanel = new GamePanel();
        Menu menu = gamePanel.getMenu();
        assertNotNull(menu);
    }

    @Test
    void testMenuComponentsLoaded() {
        GamePanel gamePanel = new GamePanel();
        Menu menu = gamePanel.getMenu();

        ArrayList<MenuComponent> components = menu.getComponents();
        components.forEach(Assertions::assertNotNull);
        assertEquals(4, components.size());
        assertEquals(0, components.getFirst().getX());
        assertEquals(0, components.getFirst().getY());
    }

    @Test
    void testMenuButtonComponentsLoaded() {
        GamePanel gamePanel = new GamePanel();
        Menu menu = gamePanel.getMenu();

        ArrayList<MenuButton> buttonComponents = menu.getSelectables();
        buttonComponents.forEach(Assertions::assertNotNull);
        assertEquals(ButtonType.PLAY, buttonComponents.get(0).getButtonType());
        assertEquals(ButtonType.SETTINGS, buttonComponents.get(1).getButtonType());
    }

    @Test
    void testMenuUpdateW() {
        GamePanel gamePanel = new GamePanel();
        Menu menu = gamePanel.getMenu();
        MenuKeyHandler menuKeyHandler = gamePanel.getMenuKeyHandler();

        KeyEvent e = new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_W, 'W');
        menuKeyHandler.keyPressed(e);
        menu.update();

        assertEquals(0, menu.getButtonComponentIndex());
    }

    @Test
    void testMenuUpdateS() {
        GamePanel gamePanel = new GamePanel();
        Menu menu = gamePanel.getMenu();

        KeyEvent e = new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_S, 'S');
        gamePanel.getMenuKeyHandler().keyPressed(e);
        menu.update();

        assertEquals(1, menu.getButtonComponentIndex());
    }

    @Test
    void testMenuUpdateEnter() {
        GamePanel gamePanel = new GamePanel();
        Menu menu = gamePanel.getMenu();
        
        KeyEvent e = new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, 'E');
        gamePanel.getMenuKeyHandler().keyPressed(e);
        menu.update();
        
        assertEquals(MenuState.MAIN, menu.getMenuState());
    }

    @Test
    void testMenuExitSettings() {
        GamePanel gamePanel = new GamePanel();
        Menu menu = gamePanel.getMenu();

        MenuKeyHandler menuKeyHandler = gamePanel.getMenuKeyHandler();
        KeyEvent s = new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_S, 'S');
        KeyEvent enter = new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, 'E');
        KeyEvent escape = new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ESCAPE, 'E');
        
        menuKeyHandler.keyPressed(s);
        menu.update();
        menuKeyHandler.keyPressed(enter);
        menu.update();
        
        assertEquals(MenuState.SETTINGS, menu.getMenuState());
        
        menuKeyHandler.keyPressed(escape);
        menu.update();
        
        assertEquals(MenuState.MAIN, menu.getMenuState());
    }

    @Test
    void testMenuStopGameTrue() {
        GamePanel gamePanel = new GamePanel();
        Menu menu = gamePanel.getMenu();

        menu.stopGame(true);

        assertEquals(MenuState.GAMEOVER, menu.getMenuState());
    }

    @Test
    void testMenuStopGameFalse() {
        GamePanel gamePanel = new GamePanel();
        Menu menu = gamePanel.getMenu();

        menu.stopGame(false);

        assertEquals(MenuState.GAMEOVER, menu.getMenuState());
    }

    @Test
    void testMenuDraw() {
        GamePanel gamePanel = new GamePanel();
        Menu menu = gamePanel.getMenu();

        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        menu.draw(g2);

        assertNotNull(menu);

        g2.dispose();
    }

    @Test
    void testMenuSettingsDraw() {
        GamePanel gamePanel = new GamePanel();
        Menu menu = gamePanel.getMenu();
        MenuKeyHandler menuKeyHandler = gamePanel.getMenuKeyHandler();

        KeyEvent s = new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_S, 'S');
        KeyEvent enter = new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, 'E');

        menuKeyHandler.keyPressed(s);
        menu.update();
        menuKeyHandler.keyPressed(enter);
        menu.update();

        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        menu.draw(g2);

        assertNotNull(menu);

        g2.dispose();
    }

    @Test
    void testMenuWinGameDraw() {
        GamePanel gamePanel = new GamePanel();
        Menu menu = gamePanel.getMenu();

        menu.stopGame(true);

        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        menu.draw(g2);

        assertNotNull(menu);

        g2.dispose();
    }

    @Test
    void testMenuLoseGameDraw() {
        GamePanel gamePanel = new GamePanel();
        Menu menu = gamePanel.getMenu();

        menu.stopGame(false);

        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        menu.draw(g2);

        assertNotNull(menu);

        g2.dispose();
    }
    @Test
    void testMenuBannerDraw() {
        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();

        MenuBanner menuBanner = new MenuBanner(10, 20, image);
        menuBanner.draw(g2);

        assertNotNull(menuBanner);
        assertEquals(10, menuBanner.getX());
        assertEquals(20, menuBanner.getY());

        g2.dispose();
    }

    @Test
    void testMenuBGDraw() {
        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();

        MenuBG menuBG = new MenuBG(30, 40, image);
        menuBG.draw(g2);

        assertNotNull(menuBG);
        assertEquals(30, menuBG.getX());
        assertEquals(40, menuBG.getY());

        g2.dispose();
    }

    @Test
    void testMenuButtonDrawUnselected() {
        BufferedImage unselectedImage = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        BufferedImage selectedImage = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = unselectedImage.createGraphics();

        MenuButton menuButton = new MenuButton(10, 20, unselectedImage, selectedImage, ButtonType.PLAY);
        menuButton.draw(g2);

        assertNotNull(menuButton);
        assertFalse(menuButton.isSelected());
        assertEquals(10, menuButton.getX());
        assertEquals(20, menuButton.getY());

        g2.dispose();
    }

    @Test
    void testMenuButtonDrawSelected() {
        BufferedImage unselectedImage = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        BufferedImage selectedImage = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = selectedImage.createGraphics();

        MenuButton menuButton = new MenuButton(30, 40, unselectedImage, selectedImage, ButtonType.SETTINGS);
        menuButton.setSelected(true);
        menuButton.draw(g2);

        assertNotNull(menuButton);
        assertTrue(menuButton.isSelected());
        assertEquals(30, menuButton.getX());
        assertEquals(40, menuButton.getY());

        g2.dispose();
    }

    @Test
    void testMenuLabelDraw() {
        String text = "Test Label";
        Font font = new Font("Arial", Font.PLAIN, 12);
        Color color = Color.BLACK;

        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();

        MenuLabel menuLabel = new MenuLabel(10, 20, text, font, color);
        menuLabel.draw(g2);

        assertNotNull(menuLabel);
        assertEquals(10, menuLabel.getX());
        assertEquals(20, menuLabel.getY());

        g2.dispose();
    }
}

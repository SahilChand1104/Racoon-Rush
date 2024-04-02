package RacoonRush.game.menu;

import RacoonRush.game.GamePanel;
import RacoonRush.game.menu.component.ButtonType;
import RacoonRush.game.menu.component.ComponentType;
import RacoonRush.game.menu.component.MenuButton;
import RacoonRush.game.menu.component.MenuComponent;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EnumMap;

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

        EnumMap<ComponentType, MenuComponent> components = menu.getComponents();
        components.forEach((key, value) -> assertNotNull(value));
        assertEquals(4, components.size());
        assertEquals(0, components.get(ComponentType.BG).getX());
        assertEquals(0, components.get(ComponentType.BG).getY());
    }

    @Test
    void testMenuButtonComponentsLoaded() {
        GamePanel gamePanel = new GamePanel();
        Menu menu = gamePanel.getMenu();

        ArrayList<MenuButton> buttonComponents = menu.getButtonComponents();
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
}

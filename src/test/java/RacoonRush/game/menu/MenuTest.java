package RacoonRush.game.menu;

import static org.junit.jupiter.api.Assertions.*;
import RacoonRush.game.GameManager;
import RacoonRush.util.Config;
import RacoonRush.game.GamePanel;
import RacoonRush.util.ImageLoader;
import RacoonRush.game.menu.component.ButtonType;
import RacoonRush.game.menu.component.ComponentType;
import RacoonRush.game.menu.component.MenuButton;
import RacoonRush.game.menu.component.MenuComponent;
import RacoonRush.game.menu.Menu;
import RacoonRush.game.menu.MenuState;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;


public class MenuTest {

    @org.junit.jupiter.api.Test
    void testMenuCreated() {
        GamePanel gamePanel = new GamePanel();
        Menu menu = new Menu(gamePanel);
        assertNotNull(menu);
    }

    @org.junit.jupiter.api.Test
    void testMenuComponentsLoaded() {
        GamePanel gamePanel = new GamePanel();
        Menu menu = new Menu(gamePanel);
        menu.loadComponents();

        EnumMap<ComponentType, MenuComponent> components = menu.getComponents();
        components.forEach((key, value) -> assertNotNull(value));
        assertEquals(4, components.size());
        assertEquals(0, components.get(ComponentType.BG).getX());
        assertEquals(0, components.get(ComponentType.BG).getY());

    }

    @org.junit.jupiter.api.Test
    void testMenuButtonComponentsLoaded() {
        GamePanel gamePanel = new GamePanel();
        Menu menu = new Menu(gamePanel);
        menu.loadComponents();

        ArrayList<MenuButton> buttonComponents = menu.getButtonComponents();
        buttonComponents.forEach((button) -> assertNotNull(button));
        assertEquals(ButtonType.PLAY, buttonComponents.get(0).getButtonType());
        assertEquals(ButtonType.SETTINGS, buttonComponents.get(1).getButtonType());

    }

    @org.junit.jupiter.api.Test
    void testMenuUpdateW() {
        GamePanel gamePanel = new GamePanel();
        Menu menu = new Menu(gamePanel);
        menu.loadComponents();

        //MenuKeyHandler menuKeyHandler = new MenuKeyHandler();
        //KeyEvent e = new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_W, 'W');
        //menuKeyHandler.keyPressed(e);
        MenuKeyHandler menuKeyHandler = gamePanel.getUIKeyHandler();
        KeyEvent e = new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_W, 'W');
        menuKeyHandler.keyPressed(e);
        menu.update();
        assertEquals(0, menu.getButtonComponentIndex());

    }

    @org.junit.jupiter.api.Test
    void testMenuUpdateS() {
        GamePanel gamePanel = new GamePanel();
        Menu menu = new Menu(gamePanel);
        menu.loadComponents();

        //MenuKeyHandler menuKeyHandler = new MenuKeyHandler();
        //KeyEvent e = new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_S, 'S');
        //menuKeyHandler.keyPressed(e);
        MenuKeyHandler menuKeyHandler = gamePanel.getUIKeyHandler();
        KeyEvent e = new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_S, 'S');
        menuKeyHandler.keyPressed(e);
        menu.update();
        assertEquals(1, menu.getButtonComponentIndex());

    }

    @org.junit.jupiter.api.Test
    void testMenuUpdateEnter() {
        GamePanel gamePanel = new GamePanel();
        Menu menu = new Menu(gamePanel);
        menu.loadComponents();

        //MenuKeyHandler menuKeyHandler = new MenuKeyHandler();
        //KeyEvent e = new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, 'E');
        //menuKeyHandler.keyPressed(e);
        MenuKeyHandler menuKeyHandler = gamePanel.getUIKeyHandler();
        KeyEvent e = new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, 'E');
        menuKeyHandler.keyPressed(e);
        menu.update();
        assertEquals(MenuState.MAIN, menu.getMenuState());


    }

    @org.junit.jupiter.api.Test
    void testMenuExitSettings() {
        GamePanel gamePanel = new GamePanel();
        Menu menu = new Menu(gamePanel);
        menu.loadComponents();

        MenuKeyHandler menuKeyHandler = gamePanel.getUIKeyHandler();
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

    @org.junit.jupiter.api.Test
    void testMenuStopGameTrue() {
        GamePanel gamePanel = new GamePanel();
        Menu menu = new Menu(gamePanel);
        menu.stopGame(true);
        assertEquals(MenuState.GAMEOVER, menu.getMenuState());

    }

    @org.junit.jupiter.api.Test
    void testMenuStopGameFalse() {
        GamePanel gamePanel = new GamePanel();
        Menu menu = new Menu(gamePanel);
        menu.stopGame(false);
        assertEquals(MenuState.GAMEOVER, menu.getMenuState());
    }

    @org.junit.jupiter.api.Test
    void testMenuDraw() {
        GamePanel gamePanel = new GamePanel();
        Menu menu = new Menu(gamePanel);
        MenuKeyHandler menuKeyHandler = gamePanel.getUIKeyHandler();
        menu.loadComponents();
        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        menu.draw(g2);
        assertNotNull(menu);

        g2.dispose();
    }

    @org.junit.jupiter.api.Test
    void testMenuSettingsDraw() {
        GamePanel gamePanel = new GamePanel();
        Menu menu = new Menu(gamePanel);
        MenuKeyHandler menuKeyHandler = gamePanel.getUIKeyHandler();
        menu.loadComponents();

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
    }

    @org.junit.jupiter.api.Test
    void testMenuWinGameDraw() {
        GamePanel gamePanel = new GamePanel();
        Menu menu = new Menu(gamePanel);
        menu.loadComponents();
        menu.stopGame(true);
        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        menu.draw(g2);
        assertNotNull(menu);

        g2.dispose();

    }

    @org.junit.jupiter.api.Test
    void testMenuLoseGameDraw() {
        GamePanel gamePanel = new GamePanel();
        Menu menu = new Menu(gamePanel);
        menu.loadComponents();
        menu.stopGame(false);
        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        menu.draw(g2);
        assertNotNull(menu);

        g2.dispose();

    }



}

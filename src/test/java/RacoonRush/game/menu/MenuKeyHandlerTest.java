package RacoonRush.game.menu;

import RacoonRush.game.GamePanel;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class MenuKeyHandlerTest {

    void checkKeysPressed(MenuKeyHandler menuKeyHandler, MenuKey key) {
        for (MenuKey menuKey : MenuKey.values()) {
            if (menuKey == key) {
                assertTrue(menuKeyHandler.get(menuKey));
            } else {
                assertFalse(menuKeyHandler.get(menuKey));
            }
        }
    }

    void checkKeysReleased(MenuKeyHandler menuKeyHandler, MenuKey key) {
        for (MenuKey menuKey : MenuKey.values()) {
            assertFalse(menuKeyHandler.get(menuKey));
        }
    }

    @org.junit.jupiter.api.Test
    void testKeyWPressed() {
        GamePanel gamePanel = new GamePanel();
        MenuKeyHandler menuKeyHandler = new MenuKeyHandler();

        KeyEvent e = new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_W, 'W');
        menuKeyHandler.keyPressed(e);
        checkKeysPressed(menuKeyHandler, MenuKey.UP);

    }

    @org.junit.jupiter.api.Test
    void testKeySPressed() {
        GamePanel gamePanel = new GamePanel();
        MenuKeyHandler menuKeyHandler = new MenuKeyHandler();

        KeyEvent e = new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_S, 'S');
        menuKeyHandler.keyPressed(e);
        checkKeysPressed(menuKeyHandler, MenuKey.DOWN);

    }

    @org.junit.jupiter.api.Test
    void testKeyAPressed() {
        GamePanel gamePanel = new GamePanel();
        MenuKeyHandler menuKeyHandler = new MenuKeyHandler();

        KeyEvent e = new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_A, 'A');
        menuKeyHandler.keyPressed(e);
        checkKeysPressed(menuKeyHandler, MenuKey.LEFT);

    }

    @org.junit.jupiter.api.Test
    void testKeyDPressed() {
        GamePanel gamePanel = new GamePanel();
        MenuKeyHandler menuKeyHandler = new MenuKeyHandler();

        KeyEvent e = new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_D, 'D');
        menuKeyHandler.keyPressed(e);
        checkKeysPressed(menuKeyHandler, MenuKey.RIGHT);

    }

    @org.junit.jupiter.api.Test
    void testKeyEnterPressed() {
        GamePanel gamePanel = new GamePanel();
        MenuKeyHandler menuKeyHandler = new MenuKeyHandler();

        KeyEvent e = new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, 'E');
        menuKeyHandler.keyPressed(e);
        checkKeysPressed(menuKeyHandler, MenuKey.ENTER);

    }

    @org.junit.jupiter.api.Test
    void testKeyEscapePressed() {
        GamePanel gamePanel = new GamePanel();
        MenuKeyHandler menuKeyHandler = new MenuKeyHandler();

        KeyEvent e = new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ESCAPE, 'E');
        menuKeyHandler.keyPressed(e);
        checkKeysPressed(menuKeyHandler, MenuKey.ESCAPE);
    }

    @org.junit.jupiter.api.Test
    void testKeyPPressed() {
        GamePanel gamePanel = new GamePanel();
        MenuKeyHandler menuKeyHandler = new MenuKeyHandler();

        KeyEvent e = new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_P, 'P');
        menuKeyHandler.keyPressed(e);
        checkKeysPressed(menuKeyHandler, MenuKey.PAUSE);
    }

    @org.junit.jupiter.api.Test
    void testKeyWReleased() {
        GamePanel gamePanel = new GamePanel();
        MenuKeyHandler menuKeyHandler = new MenuKeyHandler();

        KeyEvent e = new KeyEvent(gamePanel, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_W, 'W');
        menuKeyHandler.keyReleased(e);
        checkKeysReleased(menuKeyHandler, MenuKey.UP);

    }

    @org.junit.jupiter.api.Test
    void testKeySReleased() {
        GamePanel gamePanel = new GamePanel();
        MenuKeyHandler menuKeyHandler = new MenuKeyHandler();

        KeyEvent e = new KeyEvent(gamePanel, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_S, 'S');
        menuKeyHandler.keyReleased(e);
        checkKeysReleased(menuKeyHandler, MenuKey.DOWN);

    }

    @org.junit.jupiter.api.Test
    void testKeyAReleased() {
        GamePanel gamePanel = new GamePanel();
        MenuKeyHandler menuKeyHandler = new MenuKeyHandler();

        KeyEvent e = new KeyEvent(gamePanel, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_A, 'A');
        menuKeyHandler.keyReleased(e);
        checkKeysReleased(menuKeyHandler, MenuKey.LEFT);

    }

    @org.junit.jupiter.api.Test
    void testKeyDReleased() {
        GamePanel gamePanel = new GamePanel();
        MenuKeyHandler menuKeyHandler = new MenuKeyHandler();

        KeyEvent e = new KeyEvent(gamePanel, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_D, 'D');
        menuKeyHandler.keyReleased(e);
        checkKeysReleased(menuKeyHandler, MenuKey.RIGHT);

    }

    @org.junit.jupiter.api.Test
    void testKeyEnterReleased() {
        GamePanel gamePanel = new GamePanel();
        MenuKeyHandler menuKeyHandler = new MenuKeyHandler();

        KeyEvent e = new KeyEvent(gamePanel, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, 'E');
        menuKeyHandler.keyReleased(e);
        checkKeysReleased(menuKeyHandler, MenuKey.ENTER);

    }

    @org.junit.jupiter.api.Test
    void testKeyEscapeReleased() {
        GamePanel gamePanel = new GamePanel();
        MenuKeyHandler menuKeyHandler = new MenuKeyHandler();

        KeyEvent e = new KeyEvent(gamePanel, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_ESCAPE, 'E');
        menuKeyHandler.keyReleased(e);
        checkKeysReleased(menuKeyHandler, MenuKey.ESCAPE);
    }

    @org.junit.jupiter.api.Test
    void testKeyPReleased() {
        GamePanel gamePanel = new GamePanel();
        MenuKeyHandler menuKeyHandler = new MenuKeyHandler();

        KeyEvent e = new KeyEvent(gamePanel, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_P, 'P');
        menuKeyHandler.keyReleased(e);
        checkKeysReleased(menuKeyHandler, MenuKey.PAUSE);
    }

    @org.junit.jupiter.api.Test
    void testKeyTyped() {
        GamePanel gamePanel = new GamePanel();
        MenuKeyHandler menuKeyHandler = new MenuKeyHandler();

        KeyEvent e = new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_P, 'P');
        menuKeyHandler.keyTyped(e);
        for (MenuKey menuKey : MenuKey.values()) {
            assertFalse(menuKeyHandler.get(menuKey));
        }
    }

}

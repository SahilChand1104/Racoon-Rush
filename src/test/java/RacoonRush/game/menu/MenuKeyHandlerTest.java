package RacoonRush.game.menu;

import RacoonRush.game.GamePanel;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

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

    @Test
    void testKeyWPressed() {
        GamePanel gamePanel = new GamePanel();
        MenuKeyHandler menuKeyHandler = gamePanel.getMenuKeyHandler();

        KeyEvent e = new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_W, 'W');
        menuKeyHandler.keyPressed(e);
        checkKeysPressed(menuKeyHandler, MenuKey.UP);
    }

    @Test
    void testKeySPressed() {
        GamePanel gamePanel = new GamePanel();
        MenuKeyHandler menuKeyHandler = gamePanel.getMenuKeyHandler();

        KeyEvent e = new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_S, 'S');
        menuKeyHandler.keyPressed(e);
        checkKeysPressed(menuKeyHandler, MenuKey.DOWN);
    }

    @Test
    void testKeyAPressed() {
        GamePanel gamePanel = new GamePanel();
        MenuKeyHandler menuKeyHandler = gamePanel.getMenuKeyHandler();

        KeyEvent e = new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_A, 'A');
        menuKeyHandler.keyPressed(e);
        checkKeysPressed(menuKeyHandler, MenuKey.LEFT);
    }

    @Test
    void testKeyDPressed() {
        GamePanel gamePanel = new GamePanel();
        MenuKeyHandler menuKeyHandler = gamePanel.getMenuKeyHandler();

        KeyEvent e = new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_D, 'D');
        menuKeyHandler.keyPressed(e);
        checkKeysPressed(menuKeyHandler, MenuKey.RIGHT);
    }

    @Test
    void testKeyEnterPressed() {
        GamePanel gamePanel = new GamePanel();
        MenuKeyHandler menuKeyHandler = gamePanel.getMenuKeyHandler();

        KeyEvent e = new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, 'E');
        menuKeyHandler.keyPressed(e);
        checkKeysPressed(menuKeyHandler, MenuKey.ENTER);
    }

    @Test
    void testKeyEscapePressed() {
        GamePanel gamePanel = new GamePanel();
        MenuKeyHandler menuKeyHandler = gamePanel.getMenuKeyHandler();

        KeyEvent e = new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ESCAPE, 'E');
        menuKeyHandler.keyPressed(e);
        checkKeysPressed(menuKeyHandler, MenuKey.ESCAPE);
    }

    @Test
    void testKeyPPressed() {
        GamePanel gamePanel = new GamePanel();
        MenuKeyHandler menuKeyHandler = gamePanel.getMenuKeyHandler();

        KeyEvent e = new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_P, 'P');
        menuKeyHandler.keyPressed(e);
        checkKeysPressed(menuKeyHandler, MenuKey.PAUSE);
    }

    @Test
    void testKeyWReleased() {
        GamePanel gamePanel = new GamePanel();
        MenuKeyHandler menuKeyHandler = gamePanel.getMenuKeyHandler();

        KeyEvent e = new KeyEvent(gamePanel, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_W, 'W');
        menuKeyHandler.keyReleased(e);
        checkKeysReleased(menuKeyHandler, MenuKey.UP);
    }

    @Test
    void testKeySReleased() {
        GamePanel gamePanel = new GamePanel();
        MenuKeyHandler menuKeyHandler = gamePanel.getMenuKeyHandler();

        KeyEvent e = new KeyEvent(gamePanel, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_S, 'S');
        menuKeyHandler.keyReleased(e);
        checkKeysReleased(menuKeyHandler, MenuKey.DOWN);
    }

    @Test
    void testKeyAReleased() {
        GamePanel gamePanel = new GamePanel();
        MenuKeyHandler menuKeyHandler = gamePanel.getMenuKeyHandler();

        KeyEvent e = new KeyEvent(gamePanel, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_A, 'A');
        menuKeyHandler.keyReleased(e);
        checkKeysReleased(menuKeyHandler, MenuKey.LEFT);
    }

    @Test
    void testKeyDReleased() {
        GamePanel gamePanel = new GamePanel();
        MenuKeyHandler menuKeyHandler = gamePanel.getMenuKeyHandler();

        KeyEvent e = new KeyEvent(gamePanel, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_D, 'D');
        menuKeyHandler.keyReleased(e);
        checkKeysReleased(menuKeyHandler, MenuKey.RIGHT);
    }

    @Test
    void testKeyEnterReleased() {
        GamePanel gamePanel = new GamePanel();
        MenuKeyHandler menuKeyHandler = gamePanel.getMenuKeyHandler();

        KeyEvent e = new KeyEvent(gamePanel, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, 'E');
        menuKeyHandler.keyReleased(e);
        checkKeysReleased(menuKeyHandler, MenuKey.ENTER);
    }

    @Test
    void testKeyEscapeReleased() {
        GamePanel gamePanel = new GamePanel();
        MenuKeyHandler menuKeyHandler = gamePanel.getMenuKeyHandler();

        KeyEvent e = new KeyEvent(gamePanel, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_ESCAPE, 'E');
        menuKeyHandler.keyReleased(e);
        checkKeysReleased(menuKeyHandler, MenuKey.ESCAPE);
    }

    @Test
    void testKeyPReleased() {
        GamePanel gamePanel = new GamePanel();
        MenuKeyHandler menuKeyHandler = gamePanel.getMenuKeyHandler();

        KeyEvent e = new KeyEvent(gamePanel, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_P, 'P');
        menuKeyHandler.keyReleased(e);
        checkKeysReleased(menuKeyHandler, MenuKey.PAUSE);
    }

    @Test
    void testKeyTyped() {
        GamePanel gamePanel = new GamePanel();
        MenuKeyHandler menuKeyHandler = gamePanel.getMenuKeyHandler();

        KeyEvent e = new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_P, 'P');
        menuKeyHandler.keyTyped(e);
        for (MenuKey menuKey : MenuKey.values()) {
            assertFalse(menuKeyHandler.get(menuKey));
        }
    }
}

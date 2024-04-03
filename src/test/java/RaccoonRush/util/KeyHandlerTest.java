package RaccoonRush.util;

import RaccoonRush.game.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;
public class KeyHandlerTest {
    private GamePanel gamePanel;

    @BeforeEach
    void setUp() {
        gamePanel = new GamePanel();
    }

    @Test
    void testGet() {
        assertFalse(gamePanel.getKeyHandler().get(Move.UP));
        assertFalse(gamePanel.getKeyHandler().get(Move.DOWN));
        assertFalse(gamePanel.getKeyHandler().get(Move.LEFT));
        assertFalse(gamePanel.getKeyHandler().get(Move.RIGHT));
    }

    @Test
    void testKeyUp() {
        KeyEvent e = new KeyEvent(new Button(), KeyEvent.KEY_PRESSED, System.nanoTime(), 0, KeyEvent.VK_W, 'w');
        gamePanel.getKeyHandler().keyPressed(e);
        assertTrue(gamePanel.getKeyHandler().get(Move.UP));
        gamePanel.getKeyHandler().keyReleased(e);
        assertFalse(gamePanel.getKeyHandler().get(Move.UP));
    }

    @Test
    void testKeyDown() {
        KeyEvent e = new KeyEvent(new Button(), KeyEvent.KEY_PRESSED, System.nanoTime(), 0, KeyEvent.VK_S, 'S');
        gamePanel.getKeyHandler().keyPressed(e);
        assertTrue(gamePanel.getKeyHandler().get(Move.DOWN));
        gamePanel.getKeyHandler().keyReleased(e);
        assertFalse(gamePanel.getKeyHandler().get(Move.DOWN));
    }

    @Test
    void testKeyLeft() {
        KeyEvent e = new KeyEvent(new Button(), KeyEvent.KEY_PRESSED, System.nanoTime(), 0, KeyEvent.VK_A, 'A');
        gamePanel.getKeyHandler().keyPressed(e);
        assertTrue(gamePanel.getKeyHandler().get(Move.LEFT));
        gamePanel.getKeyHandler().keyReleased(e);
        assertFalse(gamePanel.getKeyHandler().get(Move.LEFT));
    }

    @Test
    void testKeyRight() {
        KeyEvent e = new KeyEvent(new Button(), KeyEvent.KEY_PRESSED, System.nanoTime(), 0, KeyEvent.VK_D, 'D');
        gamePanel.getKeyHandler().keyPressed(e);
        assertTrue(gamePanel.getKeyHandler().get(Move.RIGHT));
        gamePanel.getKeyHandler().keyReleased(e);
        assertFalse(gamePanel.getKeyHandler().get(Move.RIGHT));
    }
}

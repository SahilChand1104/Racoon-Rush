package RacoonRush.util;

import RacoonRush.game.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;
public class KeyHandlerTest {
    private GamePanel fakeGamePanel;

    @BeforeEach
    void setUp() {
        fakeGamePanel = new GamePanel();
    }

    @Test
    void testGet() {
        assertFalse(fakeGamePanel.getKeyHandler().get(Move.UP));
        assertFalse(fakeGamePanel.getKeyHandler().get(Move.DOWN));
        assertFalse(fakeGamePanel.getKeyHandler().get(Move.LEFT));
        assertFalse(fakeGamePanel.getKeyHandler().get(Move.RIGHT));
    }

    @Test
    void testKeyUp() {
        KeyEvent e = new KeyEvent(new Button(), KeyEvent.KEY_PRESSED, System.nanoTime(), 0, KeyEvent.VK_W, 'w');
        fakeGamePanel.getKeyHandler().keyPressed(e);
        assertTrue(fakeGamePanel.getKeyHandler().get(Move.UP));
        fakeGamePanel.getKeyHandler().keyReleased(e);
        assertFalse(fakeGamePanel.getKeyHandler().get(Move.UP));
    }

    @Test
    void testKeyDown() {
        KeyEvent e = new KeyEvent(new Button(), KeyEvent.KEY_PRESSED, System.nanoTime(), 0, KeyEvent.VK_S, 'S');
        fakeGamePanel.getKeyHandler().keyPressed(e);
        assertTrue(fakeGamePanel.getKeyHandler().get(Move.DOWN));
        fakeGamePanel.getKeyHandler().keyReleased(e);
        assertFalse(fakeGamePanel.getKeyHandler().get(Move.DOWN));
    }

    @Test
    void testKeyLeft() {
        KeyEvent e = new KeyEvent(new Button(), KeyEvent.KEY_PRESSED, System.nanoTime(), 0, KeyEvent.VK_A, 'A');
        fakeGamePanel.getKeyHandler().keyPressed(e);
        assertTrue(fakeGamePanel.getKeyHandler().get(Move.LEFT));
        fakeGamePanel.getKeyHandler().keyReleased(e);
        assertFalse(fakeGamePanel.getKeyHandler().get(Move.LEFT));
    }

    @Test
    void testKeyRight() {
        KeyEvent e = new KeyEvent(new Button(), KeyEvent.KEY_PRESSED, System.nanoTime(), 0, KeyEvent.VK_D, 'D');
        fakeGamePanel.getKeyHandler().keyPressed(e);
        assertTrue(fakeGamePanel.getKeyHandler().get(Move.RIGHT));
        fakeGamePanel.getKeyHandler().keyReleased(e);
        assertFalse(fakeGamePanel.getKeyHandler().get(Move.RIGHT));
    }
}

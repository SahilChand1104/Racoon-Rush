package RaccoonRush.util;

import RaccoonRush.game.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class GameTimeTest {
    private GamePanel gamePanel;
    @BeforeEach
    void setUp() {
        gamePanel = new GamePanel();
    }

    @Test
    void testStartTimer() {
        gamePanel.getGameTime().startTimer();
        assertTrue(gamePanel.getGameTime().isRunning());
    }

    @Test
    void testStopTimer() {
        gamePanel.getGameTime().startTimer();
        gamePanel.getGameTime().stopTimer();
        assertFalse(gamePanel.getGameTime().isRunning());
    }

    @Test
    void testPauseTimer() {
        gamePanel.getGameTime().startTimer();
        gamePanel.getGameTime().pauseTimer();
        assertFalse(gamePanel.getGameTime().isRunning());
    }

    @Test
    void testResumeTimer() {
        gamePanel.getGameTime().startTimer();
        gamePanel.getGameTime().pauseTimer();
        gamePanel.getGameTime().resumeTimer();
        assertTrue(gamePanel.getGameTime().isRunning());
    }

    @Test
    void testGetFormattedTime() {
        String time = gamePanel.getGameTime().getFormattedTime();
        assertEquals("00:00", time);
    }

    @Test
    public void testConstructor() throws InterruptedException {
        GameTime gameTime = new GameTime(gamePanel.getScoreboard());

        gameTime.startTimer();

        Thread.sleep(1500); // Wait for 1.5 seconds

        assertTrue(gameTime.isRunning(), "Timer should be running after starting");
        assertTrue(gameTime.getFormattedTime().matches("\\d\\d:\\d\\d"), "Formatted time should match pattern mm:ss");
    }
}

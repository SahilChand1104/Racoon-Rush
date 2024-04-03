package RacoonRush.util;

import RacoonRush.game.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class GameTimeTest {
    private GamePanel fakeGamePanel;
    @BeforeEach
    void setUp() {
        fakeGamePanel = new GamePanel();
    }

    @Test
    void testStartTimer() {
        fakeGamePanel.getGameTime().startTimer();
        assertTrue(fakeGamePanel.getGameTime().isRunning());
    }

    @Test
    void testStopTimer() {
        fakeGamePanel.getGameTime().startTimer();
        fakeGamePanel.getGameTime().stopTimer();
        assertFalse(fakeGamePanel.getGameTime().isRunning());
    }

    @Test
    void testPauseTimer() {
        fakeGamePanel.getGameTime().startTimer();
        fakeGamePanel.getGameTime().pauseTimer();
        assertFalse(fakeGamePanel.getGameTime().isRunning());
    }

    @Test
    void testResumeTimer() {
        fakeGamePanel.getGameTime().startTimer();
        fakeGamePanel.getGameTime().pauseTimer();
        fakeGamePanel.getGameTime().resumeTimer();
        assertTrue(fakeGamePanel.getGameTime().isRunning());
    }

    @Test
    void testGetFormattedTime() {
        String time = fakeGamePanel.getGameTime().getFormattedTime();
        assertEquals("00:00", time);
    }

    @Test
    public void testConstructor() throws InterruptedException {
        GameTime gameTime = new GameTime(fakeGamePanel);

        gameTime.startTimer();

        Thread.sleep(1500); // Wait for 1.5 seconds

        assertTrue(gameTime.isRunning(), "Timer should be running after starting");
        assertTrue(gameTime.getFormattedTime().matches("\\d\\d:\\d\\d"), "Formatted time should match pattern mm:ss");
    }

}

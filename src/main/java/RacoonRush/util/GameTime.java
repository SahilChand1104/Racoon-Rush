package RacoonRush.util;

import RacoonRush.game.GamePanel;

import javax.swing.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * GameTime class is used to keep track of the time elapsed during the game.
 */
public class GameTime {
    private final GamePanel gamePanel;
    private final Timer timer;
    private long startTime;
    private long pausedTime;
    private long totalTime;

    /**
     * Constructor for GameTime
     * @param gamePanel the game panel
     */
    public GameTime(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timer.isRunning()) {
                    updateTimeLabel();
                    totalTime = System.nanoTime() - startTime;
                }
            }
        });
    }

    /**
     * Starts the timer
     */
    public void startTimer() {
        startTime = System.nanoTime();
        timer.start();
    }

    /**
     * Pauses the timer
     */
    public void pauseTimer() {
        if (timer.isRunning()) {
            pausedTime = System.nanoTime();
            timer.stop();
        }
    }

    /**
     * Resumes the timer
     */
    public void resumeTimer() {
        if (!timer.isRunning()) {
            startTime += System.nanoTime() - pausedTime;
            timer.start();
        }
    }

    /**
     * Stops the timer
     */
    public void stopTimer() {
        timer.stop();
    }

    /**
     * Updates the time on the scoreboard
     */
    private void updateTimeLabel() {
        long elapsedTime = System.nanoTime() - startTime;
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        String formattedTime = sdf.format(new Date(elapsedTime / 1_000_000));
        gamePanel.getScoreboard().updateTimer(formattedTime);
    }

    /**
     * Formats the time
     * @return the formatted time as a string
     */
    public String getFormattedTime() {
        int totalSeconds = (int) (totalTime / 1_000_000_000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds - seconds) / 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}

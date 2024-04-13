package RaccoonRush.util;

import javax.swing.*;
import java.awt.event.*;

/**
 * GameTime class is used to keep track of the time elapsed during the game.
 */
public class GameTime {
    private final Timer timer;
    private long startTime;
    private long pausedTime;
    private long totalTime;

    /**
     * Constructor for GameTime
     */
    public GameTime(Scoreboard scoreboard) {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timer.isRunning()) {
                    scoreboard.updateTimer(System.nanoTime() - startTime);
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
     * Formats the time
     * @return the formatted time as a string
     */
    public String getFormattedTime() {
        int totalSeconds = (int) (totalTime / 1_000_000_000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds - seconds) / 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    /**
     * Checks if the timer is running
     * @return true if the timer is running, false otherwise
     */
    public boolean isRunning() {
        return timer.isRunning();
    }
}

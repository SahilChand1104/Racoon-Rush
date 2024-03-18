package RacoonRush.game;

import javax.swing.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GameTime {
    private Timer timer;
    private long startTime;
    private long pausedTime;
    private long totalTime;
    private boolean isPaused;
    Scoreboard scoreboard;
    public GameTime(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isPaused) {
                    updateTimeLabel(scoreboard);
                    totalTime = System.nanoTime() - startTime;
                }
            }
        });
    }

    public void startTimer() {
        startTime = System.nanoTime();
        timer.start();
    }

    public void pauseTimer() {
        if (!isPaused) {
            isPaused = true;
            pausedTime = System.nanoTime();
            timer.stop();
        }
    }

    public void resumeTimer() {
        if (isPaused) {
            isPaused = false;
            startTime += System.nanoTime() - pausedTime;
            timer.start();
        }
    }

    public void stopTimer() {
        timer.stop();
        startTime = 0;
        pausedTime = 0;
        totalTime = 0;
        isPaused = false;
    }

    private void updateTimeLabel(Scoreboard sb) {
        long elapsedTime = System.nanoTime() - startTime;
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        String formattedTime = sdf.format(new Date(elapsedTime/1_000_000));
        sb.updateTimer(formattedTime);
    }

    private void displayTotalTime(long totalTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        String formattedTotalTime = sdf.format(new Date(totalTime / 1000));
    }

    public String formatTime(long time) {
        long seconds = time / 1_000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public long getTime() {
        return (totalTime / 1_000_000);
    }
}

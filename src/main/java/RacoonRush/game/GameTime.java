package RacoonRush.game;

import javax.swing.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GameTime {
    private Timer timer;
    private long startTime;
    private long pausedTime;
    private boolean isPaused;
    Scoreboard scoreboard;
    public GameTime(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isPaused) {
                    updateTimeLabel(scoreboard);
                }
            }
        });
    }

    public void startTimer() {
        startTime = System.currentTimeMillis();
        timer.start();
    }

    public void pauseTimer() {
        if (!isPaused) {
            isPaused = true;
            pausedTime = System.currentTimeMillis();
            timer.stop();
        }
    }

    public void resume() {
        if (isPaused) {
            isPaused = false;
            startTime += System.currentTimeMillis() - pausedTime;
            timer.start();
        }
    }

    public void stop() {
        timer.stop();
        long totalTime = System.currentTimeMillis() - startTime;
        displayTotalTime(totalTime);
    }

    public void reset(Scoreboard sb) {
        timer.stop();
        startTime = 0;
        pausedTime = 0;
        isPaused = false;
        sb.updateTimer(formatTime(0));
    }

    private void updateTimeLabel(Scoreboard sb) {
        long elapsedTime = System.currentTimeMillis() - startTime;
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        String formattedTime = sdf.format(new Date(elapsedTime));
        sb.updateTimer(formattedTime);
    }

    private void displayTotalTime(long totalTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        String formattedTotalTime = sdf.format(new Date(totalTime));
        JOptionPane.showMessageDialog(null, "Total time: " + formattedTotalTime);
    }

    private String formatTime(long time) {
        long seconds = time / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}

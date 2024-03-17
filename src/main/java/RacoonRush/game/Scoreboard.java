package RacoonRush.game;

import RacoonRush.entity.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Scoreboard extends JPanel {
    private JLabel scoreLabel;
    private JLabel timerLabel;
    private JLabel messageLabel;

    public Scoreboard() {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.BLACK);

        scoreLabel = new JLabel("Score: 0");
        timerLabel = new JLabel("Time: 00:00");
        messageLabel = new JLabel(" ");

        // Set font and size for labels
        try {
            Font labelFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResource("/font/VCR_OSD_MONO_1.001.ttf").openStream());
            scoreLabel.setFont(labelFont.deriveFont(Font.BOLD, 24f));
            timerLabel.setFont(labelFont.deriveFont(Font.BOLD, 24f));
            messageLabel.setFont(labelFont.deriveFont(Font.BOLD, 24f));

        } catch (IOException | FontFormatException e) {
            // Handle font loading exception
            e.printStackTrace();
        }

        // Set text color for labels
        scoreLabel.setForeground(Color.BLACK); // Set text color
        timerLabel.setForeground(Color.BLACK); // Set text color
        messageLabel.setForeground(Color.BLACK); // Set text color

        // Center text alignment for labels
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(scoreLabel);
        add(timerLabel);
        add(messageLabel);
    }

    public void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    public void updateTimer(String timeInSeconds) {
        timerLabel.setText("Time: " + timeInSeconds);
    }

    public void showMessage(String message) {
        messageLabel.setText(message);
        // Create a javax.swing.Timer to clear the message after 3 seconds
        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageLabel.setText(" "); // Clear the message
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Create a gradient paint for the background
        GradientPaint gradient = new GradientPaint(
                0, 0, Color.PINK,
                getWidth(), getHeight(), Color.ORANGE);

        // Set the paint and fill the background with the gradient
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.dispose();
    }
}
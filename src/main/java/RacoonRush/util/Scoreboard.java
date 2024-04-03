package RacoonRush.util;

import RacoonRush.game.GamePanel;
import RacoonRush.map.tile.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Scoreboard class for the game
 */
public class Scoreboard extends JPanel {
    private final GamePanel gamePanel;
    private final JLabel scoreLabel;
    private final JLabel timerLabel;
    private final JLabel messageLabel;

    /**
     * Constructor for the Scoreboard
     */
    public Scoreboard(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.BLACK);

        scoreLabel = new JLabel("Score: 0");
        timerLabel = new JLabel("Time: 00:00");
        messageLabel = new JLabel(" ");

        // Set font and size for labels
        try {
            Font font = Font
                    .createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/font/VCR_OSD_MONO_1.001.ttf"))
                    .deriveFont(Font.BOLD, 24f);
            scoreLabel.setFont(font);
            timerLabel.setFont(font);
            messageLabel.setFont(font);
        } catch (IOException | FontFormatException e) {
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

        setVisible(false);
    }

    /**
     * Update the score on the scoreboard
     */
    public void updateScore() {
        if (isVisible()) {
            scoreLabel.setText("Score: " + gamePanel.getScore());
        }
    }

    /**
     * Update the timer on the scoreboard
     * @param timeInSeconds The new time in seconds
     */
    public void updateTimer(String timeInSeconds) {
        if (isVisible()) {
            timerLabel.setText("Time: " + timeInSeconds);
        }
    }

    /**
     * Show a message on the scoreboard
     * @param message The string message to show
     */
    public void showMessage(String message) {
        if (isVisible()) {
            messageLabel.setText(message);
            // Create a javax.swing.Timer to clear the message after 3 seconds
            Timer timer = new Timer(3000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    messageLabel.setText(" "); // Clear the message
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    /**
     * Returns the text displayed on the score label.
     * @return The text displayed on the score label.
     */
    public String getScoreLabelText() {
        return scoreLabel.getText();
    }

    /**
     * Returns the text displayed on the timer label.
     * @return The text displayed on the timer label.
     */
    public String getTimerLabelText() {
        return timerLabel.getText();
    }

    /**
     * Returns the text displayed on the message label.
     * @return The text displayed on the message label.
     */
    public String getMessageLabelText() {
        return messageLabel.getText();
    }


    /**
     * Paint the background of the scoreboard with a gradient
     * @param g The Graphics object to paint with
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        // Create a gradient paint for the background
        GradientPaint gradient = new GradientPaint(
                0, 0, Color.MAGENTA, getWidth(), getHeight(), Color.ORANGE
        );

        // Set the paint and fill the background with the gradient
        g2.setPaint(gradient);
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.dispose();
    }

    public void collectItemMessage(Item item, int donutsLeft) {
        if (!isVisible()) {
            return;
        }
        showMessage(switch (item.getType()) {
            case DONUT -> "+10 points! " + switch (donutsLeft) {
                case 0 -> "Hurry to the exit!";
                case 1 -> "1 more donut left.";
                default -> donutsLeft + " donuts remaining.";
            };
            case LEFTOVER -> "-20 points...";
            case PIZZA -> "+50 points! You found Uncle Fatih's lost pizza!";
            default -> "";
        });
        updateScore();
    }
}
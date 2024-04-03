package RaccoonRush.game;

import RaccoonRush.util.Scoreboard;

import javax.swing.*;
import java.awt.*;

/**
 * Main class for the game
 */
public class Main {
    public static void main(String[] args) {
        Taskbar taskbar = Taskbar.getTaskbar();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image iconImage = toolkit.getImage("src/main/resources/menu/icon.png");
        try {
            taskbar.setIconImage(iconImage);
        } catch (UnsupportedOperationException e) {
            System.out.println("The os does not support setting the icon for the taskbar");
        }
        // Make a new window
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setTitle("Raccoon Rush");
        window.setIconImage(iconImage);

        // Create a new game panel
        GamePanel gamePanel = new GamePanel();
        // Create a new scoreboard
        Scoreboard scoreboard = gamePanel.getScoreboard();

        // Create a new panel to hold the game and the scoreboard
        JPanel fullGamePanel = new JPanel(new BorderLayout());

        // Add the scoreboard and the game to the panel
        fullGamePanel.add(scoreboard, BorderLayout.SOUTH);
        fullGamePanel.add(gamePanel, BorderLayout.CENTER);

        // Add the panel to the window
        window.add(fullGamePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // Start the game
        gamePanel.startGame();
    }
}
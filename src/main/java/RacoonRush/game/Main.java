package RacoonRush.game;


import javax.swing.*;
import java.awt.*;

/**
 * Main class for the game
 */
public class Main {
    public static void main(String[] args) {
        // Make a new window
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setTitle("Racoon Rush");

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

        // Load the map and start the game thread
        gamePanel.loadMap("/maps/world_map.txt");
        gamePanel.startGameThread();
    }
}
package RacoonRush.game;


import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setTitle("Racoon Rush");




        GamePanel gamePanel = new GamePanel();
        Scoreboard scoreboard = gamePanel.getScoreboard();

        JPanel fullGamePanel = new JPanel(new BorderLayout());

        fullGamePanel.add(scoreboard, BorderLayout.SOUTH);
        fullGamePanel.add(gamePanel, BorderLayout.CENTER);

        window.add(fullGamePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.loadMap("/maps/world_map.txt");
        gamePanel.startGameThread();
    }
}
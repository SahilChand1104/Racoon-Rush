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

        JPanel contentPane = new JPanel(new BorderLayout());

        contentPane.add(scoreboard, BorderLayout.SOUTH);
        contentPane.add(gamePanel, BorderLayout.CENTER);

        window.setContentPane(contentPane);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.loadMap("/maps/world_map.txt");
        gamePanel.startGameThread();

        GameTime time = new GameTime(scoreboard);
        time.startTimer();
    }
}
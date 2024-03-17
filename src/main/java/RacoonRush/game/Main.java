package RacoonRush.game;


import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setTitle("Racoon Rush");
        window.setLocationRelativeTo(null);
        window.setVisible(true);



        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        gamePanel.loadMap("/maps/world_map.txt");
        gamePanel.startGameThread();
    }
}
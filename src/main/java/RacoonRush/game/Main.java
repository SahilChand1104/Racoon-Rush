package RacoonRush.game;

import RacoonRush.menu.Menu;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setTitle("Racoon Rush");

        Menu menu = new Menu();
        window.add(menu);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);


        menu.startMenuThread();


        // This loop stops the main function from loading up the game until the menu is done running
        while (menu.isRunning()) {
            // do nothing
        }

        menu.stopMenuThread();
        window.remove(menu);




        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        gamePanel.loadMap("/maps/world_map.txt");
        gamePanel.startGameThread();
    }
}
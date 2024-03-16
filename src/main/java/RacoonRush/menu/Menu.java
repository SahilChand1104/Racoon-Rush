package RacoonRush.menu;

import RacoonRush.game.Config;
import RacoonRush.game.KeyHandler;

import javax.swing.*;
import java.awt.*;

public class Menu extends JPanel implements Runnable {

    public final Config config = new Config(16, 3, 16, 12, 32, 32, 60, 5);

    private final UI ui;

    private Thread menuThread;

    private KeyHandler keyHandler;

    public Menu() {
        ui = new UI(this);
        this.setPreferredSize(new Dimension(config.screenWidth(), config.screenHeight()));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.keyHandler = new KeyHandler();
        this.setFocusable(true);
    }

    public void startMenuThread() {
        menuThread = new Thread(this);
        menuThread.start();
    }

    public void stopMenuThread() {
        menuThread.interrupt();
        this.setFocusable(false);

    }

    @Override
    public void run() {
        double drawInterval = 1_000_000_000.0 / config.FPS();
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (menuThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }

        }


    }

    public void update() {
        ui.update();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        ui.draw(g2);
        g2.dispose();
    }

    public boolean isMenuRunning() {
        return menuThread != null;
    }

    public KeyHandler getKeyHandler() {
        return keyHandler;
    }
}

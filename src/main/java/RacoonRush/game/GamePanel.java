package RacoonRush.game;

import RacoonRush.entity.Entity;
import RacoonRush.entity.Player;
import RacoonRush.tile.MapManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    private final Config config = new Config(16, 3, 16, 12, 32, 32, 60, 5);
    private final MapManager mapManager;
    private final KeyHandler keyHandler;
    private final CollisionDetector collisionDetector;
    private final Player player;
    private Thread gameThread;
    private int animationFrame;

    public GamePanel() {
        mapManager = new MapManager(this);
        keyHandler = new KeyHandler();
        collisionDetector = new CollisionDetector(this);
        player = new Player(this);

        this.setPreferredSize(new Dimension(config.screenWidth(), config.screenHeight()));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void loadMap(String filePath) {
        mapManager.loadMap(filePath);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public boolean hasCollision(int row, int column) {
        return mapManager.hasCollision(row, column);
    }

    public boolean isNotColliding(Entity entity, Move direction) {
        return !collisionDetector.checkCollision(entity, direction);
    }

    @Override
    public void run() {
        double drawInterval = 1_000_000_000.0 / config.FPS();
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        int animationCounter = 0;
        int animationInterval = config.FPS() / config.animationFPS();
        animationFrame = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {
                animationCounter++;
                if (animationCounter % animationInterval == 0) {
                    animationFrame = (animationFrame == 0) ? 1 : 0;
                    animationCounter = 0;
                }
                update();
                repaint();
                delta--;
            }

        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        mapManager.draw(g2);
        player.draw(g2, animationFrame);
        g2.dispose();
    }

    public Config getConfig() {
        return config;
    }

    public KeyHandler getKeyHandler() {
        return keyHandler;
    }

    public Player getPlayer() {
        return player;
    }
}

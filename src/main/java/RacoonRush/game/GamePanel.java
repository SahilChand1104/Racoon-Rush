package RacoonRush.game;

import RacoonRush.entity.Entity;
import RacoonRush.entity.Player;
import RacoonRush.tile.MapManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    // Screen settings
    private final int originalTileSize = 16;
    private final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // World Settings
    public final int maxWorldCol = 16;
    public final int maxWorldRow = 16;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    // FPS
    final int FPS = 60;

    private final MapManager mapManager;
    private final KeyHandler keyHandler;
    private final CollisionDetector collisionDetector;
    private Thread gameThread;
    public Player player;

    public GamePanel() {
        mapManager = new MapManager(this);
        keyHandler = new KeyHandler();
        collisionDetector = new CollisionDetector(this);
        player = new Player(this, keyHandler);

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
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
        double drawInterval = 1_000_000_000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
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
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        mapManager.draw(g2);
        player.draw(g2);
        g2.dispose();
    }
}

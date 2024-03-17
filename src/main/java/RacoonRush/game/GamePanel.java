package RacoonRush.game;

import RacoonRush.entity.Player;
import RacoonRush.game.menu.UI;
import RacoonRush.map.MapManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    private final Config config = new Config(16, 3, 16, 12, 32, 32, 60, 5);
    private final ImageLoader imageLoader;
    private final MapManager mapManager;
    private final KeyHandler keyHandler;
    private final CollisionDetector collisionDetector;
    private final Player player;
    private final UI ui;
    private GameState gameState;
    private Thread gameThread;
    private int playerAnimationFrame;
    private int collectibleAnimationFrame;

    public GamePanel() {
        imageLoader = new ImageLoader(this);
        keyHandler = new KeyHandler();
        mapManager = new MapManager(this);
        collisionDetector = new CollisionDetector(this);
        player = new Player(this);
        ui = new UI(this);
        gameState = GameState.MENU;

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



    @Override
    public void run() {
        double drawInterval = 1_000_000_000.0 / config.FPS();
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        int animationCounter = 0;
        int animationInterval = config.FPS() / config.animationFPS();
        playerAnimationFrame = 0;
        collectibleAnimationFrame = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {
                animationCounter++;
                if (animationCounter % animationInterval == 0) {
                    playerAnimationFrame = (playerAnimationFrame == 0) ? 1 : 0;
                    animationCounter = 0;
                    if (collectibleAnimationFrame < 11) {
                        collectibleAnimationFrame++;
                    } else {
                        collectibleAnimationFrame = 0;
                    }
                }
                update();
                repaint();
                delta--;
            }

        }
    }

    public void update() {
        switch (gameState) {
            case MENU:
                ui.update();
                break;
            case PLAY:
                player.update();
                break;
            case QUIT:
                gameThread = null;
                System.exit(0);
                break;
            default:
                break;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        switch (gameState) {
            case MENU:
                ui.draw(g2);
                break;
            case PLAY:
                mapManager.draw(g2);
                player.draw(g2, playerAnimationFrame);
                break;
            default:
                break;
        }
        g2.dispose();
    }

    public Config getConfig() {
        return config;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public KeyHandler getKeyHandler() {
        return keyHandler;
    }

    public MapManager getMapManager() {
        return mapManager;
    }

    public CollisionDetector getCollisionDetector() {
        return collisionDetector;
    }

    public Player getPlayer() {
        return player;
    }

    public int getPlayerAnimationFrame() {
        return playerAnimationFrame;
    }

    public int getCollectibleAnimationFrame() {
        return collectibleAnimationFrame;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void openMenu() {
        gameState = GameState.MENU;
    }

    public void startGame() {
        gameState = GameState.PLAY;
    }

    public void stopGame() {
        gameState = GameState.QUIT;
    }

    public UI getMenuUI() {
        return ui;
    }

}

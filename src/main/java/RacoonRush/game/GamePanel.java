package RacoonRush.game;

import RacoonRush.entity.Player;
import RacoonRush.game.menu.UI;
import RacoonRush.game.menu.UIKeyHandler;
import RacoonRush.map.MapManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    private final Config config = new Config(16, 3, 16, 12, 32, 32, 60, 5);
    private final ImageLoader imageLoader;
    private final MapManager mapManager;
    private final KeyHandler keyHandler;
    private final Sound sound;
    private final UIKeyHandler uiKeyHandler;
    private final CollisionDetector collisionDetector;
    private final Player player;
    private final UI ui;
    private GameState gameState;
    private final Scoreboard scoreboard;
    private Thread gameThread;
    private int playerAnimationFrame;
    private int collectibleAnimationFrame;
    private GameTime time;

    private boolean gameRunning = false;

    public GamePanel() {
        imageLoader = new ImageLoader(this);
        keyHandler = new KeyHandler();
        sound  = new Sound();
        mapManager = new MapManager(this);
        collisionDetector = new CollisionDetector(this);
        scoreboard = new Scoreboard();
        player = new Player(this, scoreboard);
        playMusic(0);
        uiKeyHandler = new UIKeyHandler();
        ui = new UI(this);
        gameState = GameState.MENU;
        time = new GameTime(scoreboard);

        this.setPreferredSize(new Dimension(config.screenWidth(), config.screenHeight()));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.addKeyListener(uiKeyHandler);
        this.setFocusable(true);
        disableScoreboard();
    }

    public void loadMap(String filePath) {
        mapManager.loadMap(filePath);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
        time.startTimer();
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

    public void playMusic(int i ){
        sound.setFile(i);
        sound.play();
        sound.loop();
    }
    public void stopMusic(){
        sound.stop();
    }
    public void PlaySoundEffect(int i){
        sound.setFile(i);
        sound.play();
    }

    public void disableScoreboard() {
        scoreboard.setVisible(false);
        time.stopTimer();
        scoreboard.reset();
        scoreboard.setDisabled(true);
        player.resetScore();
    }

    public void enableScoreboard() {
        scoreboard.setVisible(true);
        time.startTimer();
        scoreboard.reset();
        scoreboard.setDisabled(false);
        player.resetScore();
    }

    public void hideScoreboard() {
        time.pauseTimer();
        scoreboard.setVisible(false);
        scoreboard.setDisabled(true);
    }

    public void showScoreboard() {
        time.resumeTimer();
        scoreboard.setVisible(true);
        scoreboard.setDisabled(false);
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

    public UIKeyHandler getUIKeyHandler() {
        return uiKeyHandler;
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

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public boolean isGameRunning() {
        return gameRunning;
    }

    public void openMenu() {
        hideScoreboard();
        gameState = GameState.MENU;
    }

    public void closeMenu() {
        showScoreboard();
        gameState = GameState.PLAY;
    }

    public void startGame() {
        gameRunning = true;
        enableScoreboard();
        gameState = GameState.PLAY;
    }

    public void stopGame() {
        gameState = GameState.QUIT;
    }

    public UI getMenuUI() {
        return ui;
    }



}

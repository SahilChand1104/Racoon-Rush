package RaccoonRush.game;

import RaccoonRush.entity.EntityManager;
import RaccoonRush.game.menu.Menu;
import RaccoonRush.game.menu.MenuKeyHandler;
import RaccoonRush.util.*;
import RaccoonRush.map.MapManager;
import RaccoonRush.map.tile.Item;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents the main manager for of the game.
 * It extends JPanel and implements Runnable.
 * It is used to manage the game loop, the game state, and the game components.
 */
public class GamePanel extends JPanel implements Runnable {
    private static GamePanel instance;
    public static Config config;
    private final ImageLoader imageLoader;
    private final MapManager mapManager;
    private final EntityManager entityManager;
    private final KeyHandler keyHandler;
    private final SoundManager soundManager;
    private final MenuKeyHandler menuKeyHandler;
    private final CollisionDetector collisionDetector;
    private final Menu menu;
    private GameState gameState;
    private final Scoreboard scoreboard;
    private Thread gameThread;
    private int playerAnimationFrame;
    private int itemAnimationFrame;
    private final GameTime gameTime;
    private int score;

    /**
     * Constructor for the GamePanel class
     */
    public GamePanel() {
        instance = this;
        config = new Config(16, 3, 16, 12, 32, 32, 60, 5, 4, 2, 1, Move.DOWN);
        imageLoader = new ImageLoader();
        keyHandler = new KeyHandler();
        mapManager = new MapManager(this);
        entityManager = new EntityManager(this);
        collisionDetector = new CollisionDetector();
        soundManager = new SoundManager();
        scoreboard = new Scoreboard();
        gameTime = new GameTime(scoreboard);
        menuKeyHandler = new MenuKeyHandler();
        menu = new Menu(this);
        gameState = GameState.MENU;

        this.setPreferredSize(new Dimension(config.screenWidth(), config.screenHeight()));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.addKeyListener(menuKeyHandler);
        this.setFocusable(true);
    }

    /**
     * Method to start the game
     */
    public void startGame() {
        gameThread = new Thread(this);
        gameThread.start();
        soundManager.playSoundLoop(0);
    }

    /**
     * Starts playing the game by setting the game state to PLAY and enabling the scoreboard.
     * Will resume the game if it was paused.
     */
    public void playGame() {
        if (gameState == GameState.MENU) {
            mapManager.loadMap("/maps/world_map.txt");
            gameTime.startTimer();
            score = 0;
        } else if (gameState == GameState.PAUSE) {
            gameTime.resumeTimer();
        }

        scoreboard.setVisible(true);
        gameState = GameState.PLAY;
    }

    /**
     * Sets the game state to PAUSE and hides the scoreboard.
     */
    public void pauseGame() {
        gameTime.pauseTimer();
        scoreboard.setVisible(false);
        gameState = GameState.PAUSE;
    }

    /**
     * Displays the gameover screen by setting the game state to GAMEOVER and hiding the scoreboard.
     * @param winStatus whether the game has been won
     */
    public void stopGame(boolean winStatus) {
        gameTime.stopTimer();
        scoreboard.setVisible(false);
        menu.stopGame(winStatus);
        gameState = GameState.GAMEOVER;
    }

    /**
     * Implementation of the run method from the Runnable interface
     */
    @Override
    public void run() {
        double drawInterval = 1_000_000_000.0 / config.FPS();
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        int animationCounter = 0;
        int animationInterval = config.FPS() / config.animationFPS();
        playerAnimationFrame = 0;
        itemAnimationFrame = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {
                if (++animationCounter % animationInterval == 0) {
                    playerAnimationFrame = (playerAnimationFrame == 0) ? 1 : 0;
                    itemAnimationFrame = (itemAnimationFrame < 11) ? itemAnimationFrame + 1 : 0;
                    animationCounter = 0;
                }
                update();
                repaint();
                delta--;
            }
        }
    }

    /**
     * Method to update the game based on the current game state
     */
    public void update() {
        switch (gameState) {
            case MENU, PAUSE, GAMEOVER:
                menu.update();
                break;
            case PLAY:
                mapManager.update();
                entityManager.update();
                if (score < 0) { stopGame(false); }
                break;
        }
    }

    /**
     * Method to render the game based on the current game state
     * @param g the graphics object
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        switch (gameState) {
            case MENU, PAUSE, GAMEOVER:
                menu.draw(g2);
                break;
            case PLAY:
                mapManager.draw(g2);
                entityManager.draw(g2);
                break;
        }
        g2.dispose();
    }

    /**
     * Updates the score
     * @param item the item to update the score, each item has a different score value
     */
    public void updateScore(Item item) {
        score += switch (item.getType()) {
            case DONUT -> 10;
            case LEFTOVER -> -20;
            case PIZZA -> 50;
            default -> 0;
        };
    }

    /**
     * Method to set the game state
     * @param gameState the game state to set
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Method to get the instance of the GamePanel
     * @return the instance of the GamePanel
     */
    public static GamePanel getInstance() {
        return instance;
    }

    /**
     * Method to get the game state
     * @return the game state
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Method to get the GamePanel's image loader
     * @return the image loader object
     */
    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    /**
     * Method to get the GamePanel's key handler
     * @return the key handler object
     */
    public KeyHandler getKeyHandler() {
        return keyHandler;
    }

    /**
     * Method to get the GamePanel's Menu key handler
     * This is used for handling key events in the menu
     * @return the sound object
     */
    public MenuKeyHandler getMenuKeyHandler() {
        return menuKeyHandler;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    /**
     * Method to get the GamePanel's map manager
     * @return the map manager object
     */
    public MapManager getMapManager() {
        return mapManager;
    }

    /**
     * Method to get the GamePanel's map manager
     * @return the map manager object
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * Method to get the GamePanel's collision detector
     * @return the collision object
     */
    public CollisionDetector getCollisionDetector() {
        return collisionDetector;
    }

    /**
     * Returns the scoreboard object.
     * @return The scoreboard object.
     */
    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    /**
     * Returns the Menu object associated with the game menu.
     * @return The Menu object associated with the game menu.
     */
    public Menu getMenu() {
        return menu;
    }

    public GameTime getGameTime() {
        return gameTime;
    }

    /**
     * Returns the current frame index for the player animation.
     * @return The current frame index for the player animation.
     */
    public int getPlayerAnimationFrame() {
        return playerAnimationFrame;
    }

    /**
     * Returns the current frame index for the item animation.
     * @return The current frame index for the item animation.
     */
    public int getItemAnimationFrame() {
        return itemAnimationFrame;
    }

    /**
     * Returns the current score.
     * @return the current score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Returns the game thread.
     * @return the game thread.
     */
    public Thread getGameThread() {
        return gameThread;
    }
}

package RacoonRush.game;

import RacoonRush.entity.EntityManager;
import RacoonRush.game.menu.MenuState;
import RacoonRush.game.menu.UI;
import RacoonRush.game.menu.UIKeyHandler;
import RacoonRush.map.MapManager;
import RacoonRush.map.tile.Item;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents the manager for all aspects of the game.
 * It extends JPanel and implements Runnable.
 * The fields include gameOverMessage, winMessage, config, imageLoader, mapManager, keyHandler, sound, uiKeyHandler, collisionDetector, player, ui, gameState, scoreboard, gameThread, playerAnimationFrame, collectibleAnimationFrame, time, pizzas, numPizzas, pizzaSpawn, and labelFont.
 * The methods include loadMap, startGameThread, run, update, paintComponent, playMusic, stopMusic, PlaySoundEffect, disableScoreboard, enableScoreboard, hideScoreboard, showScoreboard, getConfig, getImageLoader, getKeyHandler, getUIKeyHandler, getMapManager, getCollisionDetector, getPlayer, getPlayerAnimationFrame, getCollectibleAnimationFrame, getScoreboard, setGameState, getGameState, isGameRunning, openMenu, closeMenu, startGame, stopGame, winGame, loseGame, getMenuUI, and addPizzas.
 */
public class GamePanel extends JPanel implements Runnable {
    private final Config config = new Config(16, 3, 16, 12, 32, 32, 60, 5);
    private final ImageLoader imageLoader;
    private final MapManager mapManager;
    private final EntityManager entityManager;
    private final KeyHandler keyHandler;
    private final SoundManager soundManager;
    private final UIKeyHandler uiKeyHandler;
    private final CollisionDetector collisionDetector;
    private final UI ui;
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
        imageLoader = new ImageLoader(this);
        keyHandler = new KeyHandler();
        mapManager = new MapManager(this);
        entityManager = new EntityManager(this);
        collisionDetector = new CollisionDetector(this);
        soundManager = new SoundManager();
        scoreboard = new Scoreboard(this);
        uiKeyHandler = new UIKeyHandler();
        ui = new UI(this);
        gameState = GameState.MENU;
        gameTime = new GameTime(this);
        score = 0;

        this.setPreferredSize(new Dimension(config.screenWidth(), config.screenHeight()));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.addKeyListener(uiKeyHandler);
        this.setFocusable(true);
    }

    /**
     * Method to load the map from a file
     * @param filePath the file path of the map
     */
    public void loadMap(String filePath) {
        mapManager.loadMap(filePath);
    }

    /**
     * Method to start the game thread
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
        soundManager.playMusic(0);
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
                ui.update();
                break;
            case PLAY:
                mapManager.update();
                entityManager.update();
                if (score < 0) { loseGame(); }
                break;
        }
    }

    /**
     * Method to render the game based on the current game state
     * @param g the graphics object
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        switch (gameState) {
            case MENU, PAUSE, GAMEOVER:
                ui.draw(g2);
                break;
            case PLAY:
                mapManager.draw(g2);
                entityManager.draw(g2);
                break;
        }
        g2.dispose();
    }

    /**
     * Method to get the game panel's constant values like tilesize, screen width, screen height, etc.
     * @return the config object
     */
    public Config getConfig() {
        return config;
    }

    /**
     * Method to get the gamepanel's image loader
     * @return the image loader object
     */
    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    /**
     * Method to get the gamepanel's key handler
     * @return the key handler object
     */
    public KeyHandler getKeyHandler() {
        return keyHandler;
    }

    /**
     * Method to get the gamepanel's UI key handler
     * This is used for handling key events in the menu
     * @return the sound object
     */
    public UIKeyHandler getUIKeyHandler() {
        return uiKeyHandler;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    /**
     * Method to get the gamepanel's map manager
     * @return the map manager object
     */
    public MapManager getMapManager() {
        return mapManager;
    }

    /**
     * Method to get the gamepanel's map manager
     * @return the map manager object
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * Method to get the gamepanel's collision detector
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
     * Returns the UI object associated with the game menu.
     * @return The UI object associated with the game menu.
     */
    public UI getMenuUI() {
        return ui;
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
     * Checks whether the game is currently running.
     * @return True if the game is running, false otherwise.
     */
    public boolean isGameRunning() {
        return gameState == GameState.PLAY || gameState == GameState.PAUSE;
    }

    /**
     * Sets the game state to MENU and hides the scoreboard.
     */
    public void openMenu() {
        gameTime.pauseTimer();
        scoreboard.setVisible(false);
        gameState = GameState.PAUSE;
    }

    /**
     * Sets the game state to PLAY and shows the scoreboard.
     */
    public void closeMenu() {
        gameTime.resumeTimer();
        scoreboard.setVisible(true);
        gameState = GameState.PLAY;
    }

    /**
     * Starts the game by setting the game state to PLAY and enabling the scoreboard.
     */
    public void startGame() {
        gameTime.startTimer();
        scoreboard.setVisible(true);
        score = 0;
        gameState = GameState.PLAY;
    }

    /**
     * Indicates that the game has been won by setting the game state to WIN and hiding the scoreboard.
     */
    public void winGame() {
        scoreboard.setVisible(false);
        gameTime.stopTimer();
        ui.setMenuState(MenuState.GAMEOVER, true);
        gameState = GameState.GAMEOVER;
    }

    /**
     * Indicates that the game has been lost by setting the game state to GAMEOVER and hiding the scoreboard.
     */
    public void loseGame() {
        scoreboard.setVisible(false);
        gameTime.stopTimer();
        ui.setMenuState(MenuState.GAMEOVER, false);
        gameState = GameState.GAMEOVER;
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

    public int getScore() {
        return score;
    }
}

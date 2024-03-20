package RacoonRush.game;

import RacoonRush.entity.EntityManager;
import RacoonRush.game.menu.UI;
import RacoonRush.game.menu.UIKeyHandler;
import RacoonRush.game.menu.UI_Pressed;
import RacoonRush.map.MapManager;
import RacoonRush.map.tile.Item;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * This class represents the manager for all aspects of the game.
 * It extends JPanel and implements Runnable.
 * The fields include gameOverMessage, winMessage, config, imageLoader, mapManager, keyHandler, sound, uiKeyHandler, collisionDetector, player, ui, gameState, scoreboard, gameThread, playerAnimationFrame, collectibleAnimationFrame, time, pizzas, numPizzas, pizzaSpawn, and labelFont.
 * The methods include loadMap, startGameThread, run, update, paintComponent, playMusic, stopMusic, PlaySoundEffect, disableScoreboard, enableScoreboard, hideScoreboard, showScoreboard, getConfig, getImageLoader, getKeyHandler, getUIKeyHandler, getMapManager, getCollisionDetector, getPlayer, getPlayerAnimationFrame, getCollectibleAnimationFrame, getScoreboard, setGameState, getGameState, isGameRunning, openMenu, closeMenu, startGame, stopGame, winGame, loseGame, getMenuUI, and addPizzas.
 */
public class GamePanel extends JPanel implements Runnable {
    private String gameOverMessage = "";
    private String winMessage = "";
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
    private Font labelFont;
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
        try {
            labelFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/font/VCR_OSD_MONO_1.001.ttf"));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

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
            case MENU, PAUSE:
                ui.update();
                break;
            case PLAY:
                mapManager.update();
                entityManager.update();
                if (score < 0) {
                    loseGame();
                }
                break;
            case QUIT:
                gameThread = null;
                System.exit(0);
                break;
            case GAMEOVER:
                if (gameTime.getTime() / 1000 != 0) {
                    gameOverMessage = "You lose! Better luck next time!";
                    gameTime.stopTimer();
                }
                if (uiKeyHandler.get(UI_Pressed.ESCAPE)) {
                    System.exit(0);
                }
                break;
            case WIN:
                if (gameTime.getTime() / 1000 != 0) {
                    winMessage = "You win!\nScore: " + score + "\nTime: " + gameTime.formatTime(gameTime.getTime());
                    gameTime.stopTimer();
                }
                if (uiKeyHandler.get(UI_Pressed.ESCAPE)) {
                    System.exit(0);
                }
                break;
            default:
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
            case MENU, PAUSE:
                ui.draw(g2);
                break;
            case PLAY:
                mapManager.draw(g2);
                entityManager.draw(g2);
                break;
            case GAMEOVER:
                g2.setColor(Color.RED);
                g2.setFont(labelFont.deriveFont(Font.BOLD, 24f));
                FontMetrics fontMetricsLose = g2.getFontMetrics();
                int loseWidth = fontMetricsLose.stringWidth(gameOverMessage);
                int x = (getWidth() - loseWidth) / 2;
                int y = getHeight() / 2;
                g2.drawString(gameOverMessage, x, y);

                String gameOverMessage2 = "Press ESC to quit";
                loseWidth = fontMetricsLose.stringWidth(gameOverMessage2);
                int x1 = (getWidth() - loseWidth) / 2;
                int y1 = getHeight() / 2 + 200;
                g2.drawString(gameOverMessage2, x1, y1);
                break;
            case WIN:
                g2.setColor(Color.GREEN);
                g2.setFont(labelFont.deriveFont(Font.BOLD, 24f));
                FontMetrics fontMetricsWin = g2.getFontMetrics();

                String[] winMessages = winMessage.split("\n");
                int winWidth = fontMetricsWin.stringWidth(winMessages[0]); // Width of "You win!" message
                int winX = (getWidth() - winWidth) / 2;
                int winY = getHeight() / 2;
                g2.drawString(winMessages[0], winX, winY);

                for (int i = 1; i < winMessages.length; i++) {
                    int messageWidth = fontMetricsWin.stringWidth(winMessages[i]);
                    int messageX = (getWidth() - messageWidth) / 2;
                    int messageY = winY + fontMetricsWin.getHeight() * i;
                    g2.drawString(winMessages[i], messageX, messageY);
                }

                gameOverMessage2 = "Press ESC to quit";
                loseWidth = fontMetricsWin.stringWidth(gameOverMessage2);
                x1 = (getWidth() - loseWidth) / 2;
                y1 = getHeight() / 2 + 200;
                g2.drawString(gameOverMessage2, x1, y1);
                break;
            default:
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
     * Stops the game by setting the game state to QUIT and hides the scoreboard.
     */
    public void stopGame() {
        gameTime.stopTimer();
        scoreboard.setVisible(false);
        gameState = GameState.QUIT;
    }

    /**
     * Indicates that the game has been won by setting the game state to WIN and hiding the scoreboard.
     */
    public void winGame() {
        scoreboard.setVisible(false);
        gameState = GameState.WIN;
    }

    /**
     * Indicates that the game has been lost by setting the game state to GAMEOVER and hiding the scoreboard.
     */
    public void loseGame() {
        scoreboard.setVisible(false);
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

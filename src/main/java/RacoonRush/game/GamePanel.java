package RacoonRush.game;

import RacoonRush.entity.Player;
import RacoonRush.game.menu.UI;
import RacoonRush.game.menu.UIKeyHandler;
import RacoonRush.game.menu.UI_Pressed;
import RacoonRush.map.MapManager;
import RacoonRush.map.tile.Item;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

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
    private ArrayList<Item> pizzas;
    private int numPizzas;
    private int pizzaSpawn;
    private Font labelFont;

    private boolean gameRunning = false;

    /**
     * Constructor for the GamePanel class
     */
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
        pizzas = new ArrayList<>();
        numPizzas = 0;
        pizzaSpawn = 0;

        this.setPreferredSize(new Dimension(config.screenWidth(), config.screenHeight()));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.addKeyListener(uiKeyHandler);
        this.setFocusable(true);
        disableScoreboard();
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
        time.startTimer();
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

    /**
     * Method to update the game based on the current game state
     */
    public void update() {
        switch (gameState) {
            case MENU:
                ui.update();
                break;
            case PLAY:
                player.update();
                Random rand = new Random();
                int ranPizza = rand.nextInt(numPizzas);
                for (int i = 0; i < numPizzas; i++) {
                    if (i == ranPizza && pizzaSpawn == config.FPS() * 3) {
                        pizzas.get(i).setCollected(false);
                    }
                    else if (pizzaSpawn == config.FPS() * 3 || pizzaSpawn == rand.nextInt(config.FPS() * 3) + config.FPS()/2) {
                        pizzas.get(i).setCollected(true);
                    }
                }
                if (pizzaSpawn == config.FPS() * 3) {
                    pizzaSpawn = -1;
                }
                else {
                    pizzaSpawn++;
                }
                break;
            case QUIT:
                gameThread = null;
                System.exit(0);
                break;
            case GAMEOVER:
                if (time.getTime()/1000 != 0) {
                    gameOverMessage = "You lose! Better luck next time!";
                    time.stopTimer();
                }
                if (uiKeyHandler.get(UI_Pressed.ESCAPE)) {
                    System.exit(0);
                }
                break;
            case WIN:
                if (time.getTime()/1000 != 0) {
                    winMessage = "You win!\nScore: " + player.getScore() + "\nTime: " + time.formatTime(time.getTime());
                    time.stopTimer();
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
            case MENU:
                ui.draw(g2);
                break;
            case PLAY:
                mapManager.draw(g2);
                player.draw(g2, playerAnimationFrame);
                break;
            case GAMEOVER:
                g2.setColor(Color.RED);
                try {
                    labelFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResource("/font/VCR_OSD_MONO_1.001.ttf").openStream());
                    g2.setFont(labelFont.deriveFont(Font.BOLD, 24f));
                } catch (IOException | FontFormatException e) {
                    // Handle font loading exception
                    e.printStackTrace();
                }
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
                try {
                    labelFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResource("/font/VCR_OSD_MONO_1.001.ttf").openStream());
                    g2.setFont(labelFont.deriveFont(Font.BOLD, 24f));
                } catch (IOException | FontFormatException e) {
                    // Handle font loading exception
                    e.printStackTrace();
                }
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
     * Method to play music
     * @param i the index of the music file
     */
    public void playMusic(int i ){
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    /**
     * Method to stop music
     */
    public void stopMusic(){
        sound.stop();
    }

    /**
     * Method to play sound effect
     * @param i the index of the sound effect file
     */
    public void PlaySoundEffect(int i){
        sound.setFile(i);
        sound.play();
    }

    /**
     * Method to disable the scoreboard
     * This method also stops the timer and resets the player's score
     */
    public void disableScoreboard() {
        scoreboard.setVisible(false);
        time.stopTimer();
        scoreboard.reset();
        scoreboard.setDisabled(true);
        player.resetScore();
    }

    /**
     * Method to enable/reset the scoreboard
     * This method also resets the timer amd the player's score
     */
    public void enableScoreboard() {
        scoreboard.setVisible(true);
        time.startTimer();
        scoreboard.reset();
        scoreboard.setDisabled(false);
        player.resetScore();
    }

    /**
     * Method to hide the scoreboard
     */
    public void hideScoreboard() {
        time.pauseTimer();
        scoreboard.setVisible(false);
        scoreboard.setDisabled(true);
    }

    /**
     * Method to show the scoreboard
     */
    public void showScoreboard() {
        time.resumeTimer();
        scoreboard.setVisible(true);
        scoreboard.setDisabled(false);
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

    /**
     * Method to get the gamepanel's map manager
     * @return the map manager object
     */
    public MapManager getMapManager() {
        return mapManager;
    }

    /**
     * Method to get the gamepanel's collision detector
     * @return the collision object
     */
    public CollisionDetector getCollisionDetector() {
        return collisionDetector;
    }

    /**
     * Method to get the gamepanel's player object
     * @return the player object
     */
    public Player getPlayer() {
        return player;
    }


    /**
     * Returns the current frame index for the player animation.
     * @return The current frame index for the player animation.
     */
    public int getPlayerAnimationFrame() {
        return playerAnimationFrame;
    }

    /**
     * Returns the current frame index for the collectible animation.
     * @return The current frame index for the collectible animation.
     */
    public int getCollectibleAnimationFrame() {
        return collectibleAnimationFrame;
    }

    /**
     * Returns the scoreboard object.
     * @return The scoreboard object.
     */
    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    /**
     * Sets the game state to the provided state.
     * @param gameState The new game state.
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Returns the current game state.
     * @return The current game state.
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Checks whether the game is currently running.
     * @return True if the game is running, false otherwise.
     */
    public boolean isGameRunning() {
        return gameRunning;
    }

    /**
     * Sets the game state to MENU and hides the scoreboard.
     */
    public void openMenu() {
        hideScoreboard();
        gameState = GameState.MENU;
    }

    /**
     * Sets the game state to PLAY and shows the scoreboard.
     */
    public void closeMenu() {
        showScoreboard();
        gameState = GameState.PLAY;
    }

    /**
     * Starts the game by setting the game state to PLAY and enabling the scoreboard.
     */
    public void startGame() {
        gameRunning = true;
        enableScoreboard();
        gameState = GameState.PLAY;
    }

    /**
     * Stops the game by setting the game state to QUIT and hides the scoreboard.
     */
    public void stopGame() {
        hideScoreboard();
        gameState = GameState.QUIT;
    }

    /**
     * Indicates that the game has been won by setting the game state to WIN and hiding the scoreboard.
     */
    public void winGame() {
        hideScoreboard();
        gameState = GameState.WIN;
    }

    /**
     * Indicates that the game has been lost by setting the game state to GAMEOVER and hiding the scoreboard.
     */
    public void loseGame() {
        hideScoreboard();
        gameState = GameState.GAMEOVER;
    }

    /**
     * Returns the UI object associated with the game menu.
     * @return The UI object associated with the game menu.
     */
    public UI getMenuUI() {
        return ui;
    }

    /**
     * Adds a pizza item to the game.
     * @param p The pizza item to be added.
     */
    public void addPizzas(Item p) {
        pizzas.add(p);
        numPizzas++;
    }
}

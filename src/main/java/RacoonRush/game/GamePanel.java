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
                    winMessage = "You won!\nScore: " + player.getScore() + "\nTime: " + time.formatTime(time.getTime());
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
        hideScoreboard();
        gameState = GameState.QUIT;
    }

    public void winGame() {
        hideScoreboard();
        gameState = GameState.WIN;
    }

    public void loseGame() {
        hideScoreboard();
        gameState = GameState.GAMEOVER;
    }

    public UI getMenuUI() {
        return ui;
    }

    public void addPizzas(Item p) {
        pizzas.add(p);
        numPizzas++;
    }
}

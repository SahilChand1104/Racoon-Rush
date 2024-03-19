package RacoonRush.map;

import RacoonRush.entity.Player;
import RacoonRush.game.Config;
import RacoonRush.game.GamePanel;
import RacoonRush.game.Manager;
import RacoonRush.map.tile.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * MapManager class is used to manage the map of the game.
 * It is used to load the map, draw the map, and check for collisions.
 */
public class MapManager implements Manager {

    private final GamePanel gamePanel;
    private final MapLoader mapLoader;
    private Tile[][] map;


    private final BufferedImage[] background;

    /**
     * Constructor for MapManager class
     * @param gamePanel GamePanel object
     */
    public MapManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        mapLoader = new MapLoader(gamePanel);
        // Background is made of 4 background tiles each 768x768 in size that are drawn in a 2x2 grid
        background = new BufferedImage[4];
        for (int i = 0; i < 4; i++) {
            background[i] = gamePanel.getImageLoader().getBackground(i);
        }
    }

    /**
     * Method to get the screen coordinate of a tile
     * This allows the map to be drawn relative to the player's screen, emulating camera movement
     * @param index index of the tile
     * @param world world coordinate
     * @param screen screen coordinate
     * @return screen coordinate
     */
    public int getScreenCoordinate(int index, int world, int screen) {
        Config config = gamePanel.getConfig();
        return index * config.tileSize() - world + screen;
    }

    /**
     * Method to draw the background of the map
     * @param g2 Graphics2D object
     */
    private void drawBackground(Graphics2D g2) {
        Config config = gamePanel.getConfig();
        Player player = gamePanel.getPlayer();
        int worldX = player.getWorldX();
        int worldY = player.getWorldY();
        int screenX = player.getScreenX();
        int screenY = player.getScreenY();
        int size = config.screenWidth();

        g2.drawImage(background[0], getScreenCoordinate(0, worldX, screenX), getScreenCoordinate(0, worldY, screenY), size, size, null);
        g2.drawImage(background[1], getScreenCoordinate(0, worldX, screenX) + size, getScreenCoordinate(0, worldY, screenY), size, size, null);
        g2.drawImage(background[2], getScreenCoordinate(0, worldX, screenX), getScreenCoordinate(0, worldY, screenY) + size, size, size, null);
        g2.drawImage(background[3], getScreenCoordinate(0, worldX, screenX) + size, getScreenCoordinate(0, worldY, screenY) + size, size, size, null);
    }

    /**
     * Method to draw a tile on the screen
     * @param g2 Graphics2D object
     * @param i row index of the tile
     * @param j column index of the tile
     */
    private void drawTile(Graphics2D g2, int i, int j) {
        Config config = gamePanel.getConfig();
        Player player = gamePanel.getPlayer();

        int screenX = getScreenCoordinate(j, player.getWorldX(), player.getScreenX());
        int screenY = getScreenCoordinate(i, player.getWorldY(), player.getScreenY());

        g2.drawImage(map[i][j].getImage(i, j, gamePanel.getItemAnimationFrame()), screenX, screenY, config.tileSize(), config.tileSize(), null);
    }

    /**
     * Method to get the drawing start index of the map
     * This means that the tiles are only drawn if they are within the screen
     * @param world world coordinate
     * @param screen screen coordinate
     * @return start index
     */
    private int getStart(int world, int screen) {
        Config config = gamePanel.getConfig();
        return Math.max((world - screen) / config.tileSize(), 0);
    }

    /**
     * Method to get the drawing end index of the map
     * This means that the tiles are only drawn if they are within the screen
     * @param world world coordinate
     * @param screen screen coordinate
     * @param max maximum index
     * @return end index
     */
    private int getEnd(int world, int screen, int max) {
        Config config = gamePanel.getConfig();
        return Math.min((world + screen) / config.tileSize() + 2, max);
    }

    public void update() {}

    /**
     * Method to draw the map on the screen
     * @param g2 Graphics2D object
     */
    public void draw(Graphics2D g2) {
        Config config = gamePanel.getConfig();
        Player player = gamePanel.getPlayer();
        int startX = getStart(player.getWorldX(), player.getScreenX());
        int startY = getStart(player.getWorldY(), player.getScreenY());
        int endX = getEnd(player.getWorldX(), player.getScreenX(), config.maxWorldCol());
        int endY = getEnd(player.getWorldY(), player.getScreenY(), config.maxWorldRow());

        // draw the background
        drawBackground(g2);

        // draw the tiles over the background
        for (int i = startY; i < endY; i++) {
            for (int j = startX; j < endX; j++) {
                if (map[i][j] != null) {
                    drawTile(g2, i, j);
                }
            }
        }
    }

    /**
     * Method to call the maploader to load the map from a text file
     * Loads the map array with Tile objects
     * @param filePath path to the text file
     */
    public void loadMap(String filePath) {
        map = mapLoader.loadMap(filePath);
    }

    /**
     * Method to check the collision of the player with a tile
     * @param row row index of the tile
     * @param column column index of the tile
     * @return true if the player can move to the tile, false otherwise
     */
    public boolean onCollide(int row, int column) {
        if (row < 0 || column < 0 || row >= map.length || column >= map[row].length) {
            return false;
        }
        if (map[row][column] == null) {
            return true;
        }
        return map[row][column].onCollide(gamePanel.getPlayer());
    }
}
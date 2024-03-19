package RacoonRush.map;

import RacoonRush.entity.Player;
import RacoonRush.game.Config;
import RacoonRush.game.GamePanel;
import RacoonRush.game.Manager;
import RacoonRush.map.tile.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MapManager implements Manager {

    private final GamePanel gamePanel;
    private final MapLoader mapLoader;
    private Tile[][] map;


    private final BufferedImage[] background;

    public MapManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        mapLoader = new MapLoader(gamePanel);
        background = new BufferedImage[4];
        for (int i = 0; i < 4; i++) {
            background[i] = gamePanel.getImageLoader().getBackground(i);
        }
    }

    public int getScreenCoordinate(int index, int world, int screen) {
        Config config = gamePanel.getConfig();
        return index * config.tileSize() - world + screen;
    }

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

    private void drawTile(Graphics2D g2, int i, int j) {
        Config config = gamePanel.getConfig();
        Player player = gamePanel.getPlayer();

        int screenX = getScreenCoordinate(j, player.getWorldX(), player.getScreenX());
        int screenY = getScreenCoordinate(i, player.getWorldY(), player.getScreenY());

        g2.drawImage(map[i][j].getImage(i, j, gamePanel.getItemAnimationFrame()), screenX, screenY, config.tileSize(), config.tileSize(), null);
    }

    private int getStart(int world, int screen) {
        Config config = gamePanel.getConfig();
        return Math.max((world - screen) / config.tileSize(), 0);
    }

    private int getEnd(int world, int screen, int max) {
        Config config = gamePanel.getConfig();
        return Math.min((world + screen) / config.tileSize() + 2, max);
    }

    public void update() {}

    public void draw(Graphics2D g2) {
        Config config = gamePanel.getConfig();
        Player player = gamePanel.getPlayer();
        int startX = getStart(player.getWorldX(), player.getScreenX());
        int startY = getStart(player.getWorldY(), player.getScreenY());
        int endX = getEnd(player.getWorldX(), player.getScreenX(), config.maxWorldCol());
        int endY = getEnd(player.getWorldY(), player.getScreenY(), config.maxWorldRow());

        drawBackground(g2);
        
        for (int i = startY; i < endY; i++) {
            for (int j = startX; j < endX; j++) {
                if (map[i][j] != null) {
                    drawTile(g2, i, j);
                }
            }
        }
    }

    public void loadMap(String filePath) {
        map = mapLoader.loadMap(filePath);
    }

    public boolean onCollide(int row, int column) {
        if (map[row][column] == null) {
            return true;
        }
        return map[row][column].onCollide(gamePanel.getPlayer());
    }
}

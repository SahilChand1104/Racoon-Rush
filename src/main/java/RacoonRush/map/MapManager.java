package RacoonRush.map;

import RacoonRush.entity.Player;
import RacoonRush.game.Config;
import RacoonRush.game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MapManager{

    private final GamePanel gp;
    private final Config config;

    private final TileManager tileManager;
    private final MapLoader mapLoader;
    private TileType[][] map;

    private BufferedImage background;



    public MapManager(GamePanel gp) {
        this.gp = gp;
        config = gp.getConfig();
        tileManager = new TileManager();
        mapLoader = new MapLoader(gp, tileManager);
        try {
            background = ImageIO.read(getClass().getResourceAsStream("/maps/background_img.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getScreenCoordinate(int index, int world, int screen) {
        return index * config.tileSize() - world + screen;
    }

    private void drawBackground(Graphics2D g2) {
        Player player = gp.getPlayer();
        int worldX = player.getWorldX();
        int worldY = player.getWorldY();
        int screenX = player.getScreenX();
        int screenY = player.getScreenY();

        g2.drawImage(background, getScreenCoordinate(0, worldX, screenX), getScreenCoordinate(0, worldY, screenY), 768, 768, null);
        g2.drawImage(background, getScreenCoordinate(0, worldX, screenX)+768, getScreenCoordinate(0, worldY, screenY), 768, 768, null);
        g2.drawImage(background, getScreenCoordinate(0, worldX, screenX), getScreenCoordinate(0, worldY, screenY)+768, 768, 768, null);
        g2.drawImage(background, getScreenCoordinate(0, worldX, screenX)+768, getScreenCoordinate(0, worldY, screenY)+768, 768, 768, null);
    }

    private void drawTile(Graphics2D g2, int i, int j) {
        Player player = gp.getPlayer();
        int screenX = getScreenCoordinate(j, player.getWorldX(), player.getScreenX());
        int screenY = getScreenCoordinate(i, player.getWorldY(), player.getScreenY());
        if ( !map[i][j].equals(TileType.EMPTY) ) {
            g2.drawImage(tileManager.getTileImage(map[i][j]), screenX, screenY, config.tileSize(), config.tileSize(), null);
        }
    }

    private int getStart(int world, int screen) {
        return Math.max((world - screen - config.screenWidth()) / config.tileSize(), 0);
    }

    private int getEnd(int world, int screen, int max) {
        return Math.min((world + screen + config.screenWidth()) / config.tileSize(), max);
    }

    public void draw(Graphics2D g2) {
        Player player = gp.getPlayer();
        int startX = getStart(player.getWorldX(), player.getScreenX());
        int startY = getStart(player.getWorldY(), player.getScreenY());
        int endX = getEnd(player.getWorldX(), player.getScreenX(), config.maxWorldCol());
        int endY = getEnd(player.getWorldY(), player.getScreenY(), config.maxWorldRow());

        drawBackground(g2);
        
        for (int i = startY; i < endY; i++) {
            for (int j = startX; j < endX; j++) {
                drawTile(g2, i, j);
            }
        }
    }

    public void loadMap(String filePath) {
        map = mapLoader.loadMap(filePath);
    }

    public boolean hasCollision(int row, int column) {
        return tileManager.hasCollision(map[row][column]);
    }
}

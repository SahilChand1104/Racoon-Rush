package RacoonRush.tile;

import RacoonRush.game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MapManager{

    private final GamePanel gp;

    private final TileManager tileManager;
    private final MapLoader mapLoader;
    private TileType[][] map;

    private BufferedImage background;



    public MapManager(GamePanel gp) {
        this.gp = gp;
        tileManager = new TileManager();
        mapLoader = new MapLoader(gp, tileManager);
        try {
            background = ImageIO.read(getClass().getResourceAsStream("/maps/background_img.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getScreenCoordinate(int index, int world, int screen) {
        return index * gp.tileSize - world + screen;
    }

    private void drawBackground(Graphics2D g2) {
        int worldX = gp.getPlayerWorldX();
        int worldY = gp.getPlayerWorldY();
        int screenX = gp.getPlayerScreenX();
        int screenY = gp.getPlayerScreenY();

        g2.drawImage(background, getScreenCoordinate(0, worldX, screenX), getScreenCoordinate(0, worldY, screenY), 768, 768, null);
        g2.drawImage(background, getScreenCoordinate(0, worldX, screenX)+768, getScreenCoordinate(0, worldY, screenY), 768, 768, null);
        g2.drawImage(background, getScreenCoordinate(0, worldX, screenX), getScreenCoordinate(0, worldY, screenY)+768, 768, 768, null);
        g2.drawImage(background, getScreenCoordinate(0, worldX, screenX)+768, getScreenCoordinate(0, worldY, screenY)+768, 768, 768, null);
    }

    private void drawTile(Graphics2D g2, int i, int j) {
        int screenX = getScreenCoordinate(j, gp.getPlayerWorldX(), gp.getPlayerScreenX());
        int screenY = getScreenCoordinate(i, gp.getPlayerWorldY(), gp.getPlayerScreenY());
        if ( !map[i][j].equals(TileType.EMPTY) ) {
            g2.drawImage(tileManager.getTileImage(map[i][j]), screenX, screenY, gp.getTileSize(), gp.getTileSize(), null);
        }
    }

    private int getStart(int world, int screen) {
        return Math.max((world - screen - gp.getScreenWidth()) / gp.getTileSize(), 0);
    }

    private int getEnd(int world, int screen, int max) {
        return Math.min((world + screen + gp.getScreenWidth()) / gp.getTileSize(), max);
    }

    public void draw(Graphics2D g2) {

        int startX = getStart(gp.getPlayerWorldX(), gp.getPlayerScreenX());
        int startY = getStart(gp.getPlayerWorldY(), gp.getPlayerScreenY());
        int endX = getEnd(gp.getPlayerWorldX(), gp.getPlayerScreenX(), gp.getMaxWorldCol());
        int endY = getEnd(gp.getPlayerWorldY(), gp.getPlayerScreenY(), gp.getMaxWorldRow());

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

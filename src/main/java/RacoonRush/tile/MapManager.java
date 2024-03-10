package RacoonRush.tile;

import RacoonRush.game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MapManager extends Map{

    private final TileManager tileManager;
    private final MapLoader mapLoader;
    private TileType[][] map;



    public MapManager(GamePanel gp) {
        super(gp);
        tileManager = new TileManager();
        mapLoader = new MapLoader(gp, tileManager);
    }



    private void drawTile(Graphics2D g2, int i, int j) {
        int screenX = getScreenCoordinate(j, getPlayerWorldX(), getPlayerScreenX());
        int screenY = getScreenCoordinate(i, getPlayerWorldY(), getPlayerScreenY());
        if ( !map[i][j].equals(TileType.EMPTY) ) {
            g2.drawImage(tileManager.getTileImage(map[i][j]), screenX, screenY, getTileSize(), getTileSize(), null);
        }
    }

    private int getStart(int world, int screen) {
        return Math.max((world - screen - getScreenWidth()) / getTileSize(), 0);
    }

    private int getEnd(int world, int screen, int max) {
        return Math.min((world + screen + getScreenWidth()) / getTileSize(), max);
    }

    public void draw(Graphics2D g2) {

        int startX = getStart(getPlayerWorldX(), getPlayerScreenX());
        int startY = getStart(getPlayerWorldY(), getPlayerScreenY());
        int endX = getEnd(getPlayerWorldX(), getPlayerScreenX(), getMaxWorldCol());
        int endY = getEnd(getPlayerWorldY(), getPlayerScreenY(), getMaxWorldRow());


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

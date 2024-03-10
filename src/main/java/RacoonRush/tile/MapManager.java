package RacoonRush.tile;

import RacoonRush.game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MapManager {
    private final GamePanel gp;
    private final TileManager tileManager;
    private final MapLoader mapLoader;
    private TileType[][] map;

    private BufferedImage background;

    public MapManager(GamePanel gp) {
        this.gp = gp;
        tileManager = new TileManager();
        mapLoader = new MapLoader(gp, tileManager);
    }

    private int getScreenCoordinate(int index, int world, int screen) {
        return index * gp.tileSize - world + screen;
    }

    private void drawTile(Graphics2D g2, int i, int j) {
        int screenX = getScreenCoordinate(j, gp.player.worldX, gp.player.screenX);
        int screenY = getScreenCoordinate(i, gp.player.worldY, gp.player.screenY);
        if ( !map[i][j].equals(TileType.EMPTY) ) {
            g2.drawImage(tileManager.getTileImage(map[i][j]), screenX, screenY, gp.tileSize, gp.tileSize, null);
        } else {
            // do nothing
        }
    }

    private int getStart(int world, int screen) {
        return Math.max((world - screen - gp.screenWidth) / gp.tileSize, 0);
    }

    private int getEnd(int world, int screen, int max) {
        return Math.min((world + screen + gp.screenWidth) / gp.tileSize, max);
    }

    public void draw(Graphics2D g2) {
        try {
            background = ImageIO.read(getClass().getResourceAsStream("/maps/background_img.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        int startX = getStart(gp.player.worldX, gp.player.screenX);
        int startY = getStart(gp.player.worldY, gp.player.screenY);
        int endX = getEnd(gp.player.worldX, gp.player.screenX, gp.maxWorldCol);
        int endY = getEnd(gp.player.worldY, gp.player.screenY, gp.maxWorldRow);

        g2.drawImage(background, getScreenCoordinate(0, gp.player.worldX, gp.player.screenX), getScreenCoordinate(0, gp.player.worldY, gp.player.screenY), 768, 768, null);

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

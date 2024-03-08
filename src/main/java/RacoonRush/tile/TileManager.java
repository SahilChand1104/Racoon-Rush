package RacoonRush.tile;

import RacoonRush.game.GamePanel;

import java.awt.*;

public class TileManager {
    private final GamePanel gp;
    private final TileFactory tileFactory;
    private final MapLoader mapLoader;
    private TileType[][] map;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tileFactory = new TileFactory();
        mapLoader = new MapLoader(gp, tileFactory);
    }

    private int getScreenCoordinate(int index, int world, int screen) {
        return index * gp.tileSize - world + screen;
    }

    private void drawTile(Graphics2D g2, int i, int j) {
        int screenX = getScreenCoordinate(j, gp.player.worldX, gp.player.screenX);
        int screenY = getScreenCoordinate(i, gp.player.worldY, gp.player.screenY);
        g2.drawImage(tileFactory.getTileImage(map[i][j]), screenX, screenY, gp.tileSize, gp.tileSize, null);
    }

    private int getStart(int world, int screen) {
        return Math.max((world - screen - gp.screenWidth) / gp.tileSize, 0);
    }

    private int getEnd(int world, int screen, int max) {
        return Math.min((world + screen + gp.screenWidth) / gp.tileSize, max);
    }

    public void draw(Graphics2D g2) {
        int startX = getStart(gp.player.worldX, gp.player.screenX);
        int startY = getStart(gp.player.worldY, gp.player.screenY);
        int endX = getEnd(gp.player.worldX, gp.player.screenX, gp.maxWorldCol);
        int endY = getEnd(gp.player.worldY, gp.player.screenY, gp.maxWorldRow);

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
        return tileFactory.hasCollision(map[row][column]);
    }
}

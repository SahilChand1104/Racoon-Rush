package RacoonRush.tile;

import RacoonRush.game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.EnumMap;

public class TileManager {
    private final GamePanel gp;
    private final EnumMap<TileType, Tile> tile;
    private final TileType[][] map;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new EnumMap<>(TileType.class);
        map = new TileType[gp.maxWorldRow][gp.maxWorldCol];

        loadTileImages();
        loadMap("/maps/world_map.txt");
    }

    private void loadTileImages() {
        loadTileImage(TileType.FLOOR, "/tile/floor_v1.png", false);
        loadTileImage(TileType.WALL, "/tile/wall_v1.png", true);
    }

    private void loadTileImage(TileType type, String filePath, boolean isSolid) {
        try {
            tile.put(type, new Tile(ImageIO.read(getClass().getResourceAsStream(filePath)), isSolid));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for (int i = 0; i < gp.maxWorldRow; i++) {
                String[] tokens = br.readLine().split(" ");
                for (int j = 0; j < gp.maxWorldCol; j++) {
                    map[i][j] = TileType.FLOOR.getTileType(Integer.parseInt(tokens[j]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getScreenCoordinate(int index, int world, int screen) {
        return index * gp.tileSize - world + screen;
    }

    private void drawTile(Graphics2D g2, int i, int j) {
        int screenX = getScreenCoordinate(j, gp.player.worldX, gp.player.screenX);
        int screenY = getScreenCoordinate(i, gp.player.worldY, gp.player.screenY);
        g2.drawImage(tile.get(map[i][j]).image, screenX, screenY, gp.tileSize, gp.tileSize, null);
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
}

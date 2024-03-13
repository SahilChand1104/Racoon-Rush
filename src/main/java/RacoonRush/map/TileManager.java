package RacoonRush.map;

import RacoonRush.game.Config;
import RacoonRush.game.GamePanel;
import RacoonRush.game.ImageLoader;

import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.HashMap;

public class TileManager {
    private final GamePanel gamePanel;
    private final HashMap<Integer, TileType> tileTypes;
    private final EnumMap<TileType, Tile> tile;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tileTypes = new HashMap<>();
        tile = new EnumMap<>(TileType.class);
        createTiles();
    }

    private void createTiles() {
        createTile(0, TileType.EMPTY, false);
        createTile(1, TileType.WALL, true);
        createTile(2, TileType.DONUT, false);
    }

    private void createTile(int value, TileType type, boolean isSolid) {
        ImageLoader imageLoader = gamePanel.getImageLoader();
        tileTypes.put(value, type);
        tile.put(type, new Tile(imageLoader.getTileImage(type), isSolid));
    }

    public BufferedImage getTileImage(int value) {
        return getTileImage(getTileType(value));
    }

    public BufferedImage getTileImage(TileType type) {
        Config config = gamePanel.getConfig();
        return tile.get(type).image;
    }

    public TileType getTileType(int value) {
        return tileTypes.get(value);
    }

    public Tile getTile(TileType type) {
        return tile.get(type);
    }

    public boolean hasCollision(TileType type) {
        return tile.get(type).collision;
    }
}

package RacoonRush.tile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.HashMap;

public class TileFactory {
    private final HashMap<Integer, TileType> tileTypes;
    private final EnumMap<TileType, Tile> tile;

    public TileFactory() {
        tileTypes = new HashMap<>();
        tile = new EnumMap<>(TileType.class);
        loadTileImages();
    }

    private void loadTileImages() {
        loadTileImage(0, TileType.FLOOR, "/tile/floor_v1.png", false);
        loadTileImage(1, TileType.WALL, "/tile/wall_v1.png", true);
    }

    private void loadTileImage(int value, TileType type, String filePath, boolean isSolid) {
        try {
            tileTypes.put(value, type);
            tile.put(type, new Tile(ImageIO.read(getClass().getResourceAsStream(filePath)), isSolid));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getTileImage(int value) {
        return getTileImage(getTileType(value));
    }

    public BufferedImage getTileImage(TileType type) {
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

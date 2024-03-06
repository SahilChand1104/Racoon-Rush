package RacoonRush.tile;

import RacoonRush.game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.util.EnumMap;

public class TileManager {
    private final GamePanel gp;
    private final EnumMap<TileType, Tile> tile;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new EnumMap<>(TileType.class);
        getTileImage();
    }

    public void getTileImage() {
        try {
            tile.put(TileType.FLOOR, new Tile(ImageIO.read(getClass().getResourceAsStream("/tile/floor_v1.png"))));
            tile.put(TileType.WALL, new Tile(ImageIO.read(getClass().getResourceAsStream("/tile/wall_v1.png"))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2) {

        g2.drawImage(tile.get(TileType.FLOOR).image, 0, 0, gp.tileSize, gp.tileSize, null);

        for (int i = 0; i < gp.maxScreenRow; i++) {
            for (int j = 0; j < gp.maxScreenCol; j++) {
                if (i == 0 || i == gp.maxScreenRow - 1 || j == 0 || j == gp.maxScreenCol - 1) {
                    g2.drawImage(tile.get(TileType.WALL).image, j * gp.tileSize, i * gp.tileSize, gp.tileSize, gp.tileSize, null);
                } else {
                    g2.drawImage(tile.get(TileType.FLOOR).image, j * gp.tileSize, i * gp.tileSize, gp.tileSize, gp.tileSize, null);
                }
            }
        }
    }
}

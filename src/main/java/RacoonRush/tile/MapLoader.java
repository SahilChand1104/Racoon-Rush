package RacoonRush.tile;

import RacoonRush.game.GamePanel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MapLoader {
    private final GamePanel gp;
    private final TileFactory tf;

    public MapLoader(GamePanel gp, TileFactory tf) {
        this.gp = gp;
        this.tf = tf;
    }

    public TileType[][] loadMap(String filePath) {
        TileType[][] map = new TileType[gp.maxWorldRow][gp.maxWorldCol];
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for (int i = 0; i < gp.maxWorldRow; i++) {
                String[] tokens = br.readLine().split(" ");
                for (int j = 0; j < gp.maxWorldCol; j++) {
                    map[i][j] = tf.getTileType(Integer.parseInt(tokens[j]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }
}

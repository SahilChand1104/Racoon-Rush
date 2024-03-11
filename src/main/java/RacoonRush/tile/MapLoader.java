package RacoonRush.tile;

import RacoonRush.game.GamePanel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MapLoader {

    private final GamePanel gp;
    private final TileManager tf;

    public MapLoader(GamePanel gp, TileManager tf) {
        this.gp = gp;
        this.tf = tf;
    }

    public TileType[][] loadMap(String filePath) {
        TileType[][] map = new TileType[gp.getMaxWorldRow()][gp.getMaxWorldCol()];
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for (int i = 0; i < gp.getMaxWorldRow(); i++) {
                String[] tokens = br.readLine().split(" ");
                for (int j = 0; j < gp.getMaxWorldCol(); j++) {
                    map[i][j] = tf.getTileType(Integer.parseInt(tokens[j]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }
}

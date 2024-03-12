package RacoonRush.map;

import RacoonRush.game.Config;
import RacoonRush.game.GamePanel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MapLoader {

    private final GamePanel gp;
    private final TileManager tm;

    public MapLoader(GamePanel gp, TileManager tm) {
        this.gp = gp;
        this.tm = tm;
    }

    public TileType[][] loadMap(String filePath) {
        Config config = gp.getConfig();
        TileType[][] map = new TileType[config.maxWorldRow()][config.maxWorldCol()];
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for (int i = 0; i < config.maxWorldRow(); i++) {
                String[] tokens = br.readLine().split(" ");
                for (int j = 0; j < config.maxWorldCol(); j++) {
                    map[i][j] = tm.getTileType(Integer.parseInt(tokens[j]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }
}

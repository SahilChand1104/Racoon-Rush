package RacoonRush.map;

import RacoonRush.game.Config;
import RacoonRush.game.GamePanel;
import RacoonRush.map.tile.Tile;
import RacoonRush.map.tile.TileFactory;
import RacoonRush.map.tile.TileType;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapLoader {

    private final GamePanel gamePanel;

    public MapLoader(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public Tile[][] loadMap(String filePath) {
        Config config = gamePanel.getConfig();
        TileFactory tileFactory = new TileFactory(gamePanel);
        Tile[][] map = new Tile[config.maxWorldRow()][config.maxWorldCol()];
        List<TileType> tileTypes = new ArrayList<>(Arrays.asList(TileType.values()));
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for (int i = 0; i < config.maxWorldRow(); i++) {
                String[] tokens = br.readLine().split(" ");
                for (int j = 0; j < config.maxWorldCol(); j++) {
                    map[i][j] = tileFactory.createTile(tileTypes.get(Integer.parseInt(tokens[j])));
                    if (tileTypes.get(Integer.parseInt(tokens[j])) == TileType.DONUT) {
                        gamePanel.getPlayer().addDonutsLeft();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}

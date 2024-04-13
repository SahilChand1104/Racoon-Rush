package RaccoonRush.map;

import RaccoonRush.map.tile.Item;
import RaccoonRush.map.tile.Tile;
import RaccoonRush.map.tile.TileFactory;
import RaccoonRush.map.tile.TileType;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * MapLoader class is used to load and create the map from a text file.
 */
public class MapLoader {
    /**
     * Method to load the map from a text file
     * @param filePath path to the text file
     * @return 2D array of Tile objects
     */
    public Tile[][] loadMap(ItemManager itemManager, String filePath, int maxWorldCol, int maxWorldRow) {
        TileFactory tileFactory = new TileFactory();
        Tile[][] map = new Tile[maxWorldRow][maxWorldCol];
        List<TileType> tileTypes = new ArrayList<>(Arrays.asList(TileType.values()));
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filePath)));
            for (int i = 0; i < maxWorldRow; i++) {
                String[] tokens = br.readLine().split(" ");
                for (int j = 0; j < maxWorldCol; j++) {
                    // the parsed value corresponds to the value of the tileType enum
                    TileType tileType = tileTypes.get(Integer.parseInt(tokens[j]));
                    map[i][j] = tileFactory.createTile(tileType);
                    if (tileType == TileType.DONUT) {
                        itemManager.addDonut();
                    }
                    if (tileType == TileType.PIZZA) {
                        itemManager.addPizza((Item) map[i][j]);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}

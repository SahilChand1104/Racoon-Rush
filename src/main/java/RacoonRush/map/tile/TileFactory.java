package RacoonRush.map.tile;

import RacoonRush.game.GamePanel;
import RacoonRush.game.ImageLoader;

/**
 * TileFactory class is used to create Tile objects based on the TileType.
 * It uses a factory design pattern to create the Tile objects.

 */
public class TileFactory {
    private final GamePanel gamePanel;

    /**
     * Constructor for TileFactory class
     * @param gamePanel GamePanel object
     */
    public TileFactory(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Method to create a Tile object based on the TileType
     * @param type TileType enum
     * @return Tile object
     */
    public Tile createTile(TileType type) {
        ImageLoader imageLoader = gamePanel.getImageLoader();
        return switch (type) {
            case EMPTY -> null;
            case WALL -> new Wall(imageLoader.getWallImages());
            case TREE -> new Wall(imageLoader.getTreeImages());
            case DONUT -> new Item(imageLoader.getDonutImages(), 10, gamePanel);
            case LEFTOVER -> new Item(imageLoader.getLeftoverImages(), -20, gamePanel);
            case PIZZA -> new Item(imageLoader.getPizzaImages(), 50, gamePanel);
            case END -> new Item(null, 0, gamePanel);
        };
    }
}

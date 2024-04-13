package RaccoonRush.map.tile;

import RaccoonRush.game.GamePanel;
import RaccoonRush.util.ImageLoader;

/**
 * TileFactory class is used to create Tile objects based on the TileType.
 * It uses a factory design pattern to create the Tile objects.
 */
public class TileFactory {
    /**
     * Method to create a Tile object based on the TileType
     * @param type TileType enum
     * @return Tile object
     */
    public Tile createTile(TileType type) {
        ImageLoader imageLoader = GamePanel.getInstance().getImageLoader();
        return switch (type) {
            case EMPTY -> null;
            case WALL -> new Wall(imageLoader.getWallImages());
            case TREE -> new Wall(imageLoader.getTreeImages());
            case DONUT -> new Item(imageLoader.getDonutImages(), type);
            case LEFTOVER -> new Item(imageLoader.getLeftoverImages(), type);
            case PIZZA -> new Item(imageLoader.getPizzaImages(), type);
            case END -> new Item(null, type);
        };
    }
}

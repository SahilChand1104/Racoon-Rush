package RacoonRush.map.tile;

import RacoonRush.game.GamePanel;
import RacoonRush.game.ImageLoader;

public class TileFactory {
    private final GamePanel gamePanel;
    public TileFactory(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    public Tile createTile(TileType type) {
        ImageLoader imageLoader = gamePanel.getImageLoader();
        return switch (type) {
            case EMPTY -> null;
            case WALL -> new Wall(imageLoader.getWallImages());
            case TREE -> new Wall(imageLoader.getTreeImages());
            case DONUT -> new Item(imageLoader.getDonutImages(), 10, gamePanel);
            case LEFTOVER -> new Item(imageLoader.getLeftoverImages(), -20, gamePanel);
            case PIZZA -> new Item(imageLoader.getPizzaImages(), 50, gamePanel);
        };
    }
}

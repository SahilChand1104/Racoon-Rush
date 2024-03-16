package RacoonRush.game;

import RacoonRush.entity.Player;
import RacoonRush.map.MapManager;

public class CollisionDetector {
    private final GamePanel gp;
    public CollisionDetector(GamePanel gp) {
        this.gp = gp;
    }

    // Interact with the blocks in the given direction
    // Returns true if the player can move
    public boolean move(Player player, Move direction) {
        MapManager mapManager = gp.getMapManager();
        int offsetX = switch (direction) {
            case LEFT -> -player.speed;
            case RIGHT -> player.speed;
            default -> 0;
        };
        int offsetY = switch (direction) {
            case UP -> -player.speed;
            case DOWN -> player.speed;
            default -> 0;
        };
        int leftColumn = player.leftColumn(offsetX);
        int rightColumn = player.rightColumn(offsetX);
        int topRow = player.topRow(offsetY);
        int bottomRow = player.bottomRow(offsetY);

        return switch (direction) {
            case UP -> mapManager.onCollide(topRow, leftColumn) && mapManager.onCollide(topRow, rightColumn);
            case DOWN -> mapManager.onCollide(bottomRow, leftColumn) && mapManager.onCollide(bottomRow, rightColumn);
            case LEFT -> mapManager.onCollide(topRow, leftColumn) && mapManager.onCollide(bottomRow, leftColumn);
            case RIGHT -> mapManager.onCollide(topRow, rightColumn) && mapManager.onCollide(bottomRow, rightColumn);
        };
    }
}

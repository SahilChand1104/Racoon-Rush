package RacoonRush.game;

import RacoonRush.entity.Player;
import RacoonRush.map.MapManager;

public class CollisionDetector {
    private final GamePanel gp;
    public CollisionDetector(GamePanel gp) {
        this.gp = gp;
    }

    // Check if the player can move in the given direction
    // TODO: Create a function for entity collision detection
    public boolean playerCanMove(Player player, Move direction) {
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
            case UP -> !mapManager.hasCollision(topRow, leftColumn) && !mapManager.hasCollision(topRow, rightColumn);
            case DOWN -> !mapManager.hasCollision(bottomRow, leftColumn) && !mapManager.hasCollision(bottomRow, rightColumn);
            case LEFT -> !mapManager.hasCollision(topRow, leftColumn) && !mapManager.hasCollision(bottomRow, leftColumn);
            case RIGHT -> !mapManager.hasCollision(topRow, rightColumn) && !mapManager.hasCollision(bottomRow, rightColumn);
        };
    }
}

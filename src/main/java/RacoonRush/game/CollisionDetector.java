package RacoonRush.game;

import RacoonRush.entity.Player;
import RacoonRush.map.MapManager;

/**
 * This class represents the collision detector for the game.
 * It is used to detect collisions between the player and the map.
 */
public class CollisionDetector {
    private final GamePanel gp;

    /**
     * This constructor initializes the collision detector with the current game panel.
     * @param gp The game panel.
     */
    public CollisionDetector(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * @param player The player object.
     * @param direction The direction to move.
     * Interact with the blocks in the given direction
     * @return true if the player can move.
     */
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

package RaccoonRush.util;

import RaccoonRush.entity.Entity;
import RaccoonRush.entity.enemy.Enemy;
import RaccoonRush.game.GamePanel;
import RaccoonRush.map.MapManager;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class represents the collision detector for the game.
 * It is used to detect collisions between the player and the map.
 */
public class CollisionDetector {
    /**
     * @param entity The entity object.
     * @param direction The direction to move.
     * Interact with the blocks in the given direction
     * @return true if the entity can move in the given direction.
     */
    public boolean move(Entity entity, Move direction) {
        int speed = entity.getSpeed();
        int offsetX = switch (direction) {
            case LEFT -> -speed;
            case RIGHT -> speed;
            default -> 0;
        };
        int offsetY = switch (direction) {
            case UP -> -speed;
            case DOWN -> speed;
            default -> 0;
        };
        int leftColumn = entity.leftColumn(offsetX);
        int rightColumn = entity.rightColumn(offsetX);
        int topRow = entity.topRow(offsetY);
        int bottomRow = entity.bottomRow(offsetY);

        MapManager mapManager = GamePanel.getInstance().getMapManager();
        return switch (direction) {
            case UP -> mapManager.onCollide(topRow, leftColumn, entity) && mapManager.onCollide(topRow, rightColumn, entity);
            case DOWN -> mapManager.onCollide(bottomRow, leftColumn, entity) && mapManager.onCollide(bottomRow, rightColumn, entity);
            case LEFT -> mapManager.onCollide(topRow, leftColumn, entity) && mapManager.onCollide(bottomRow, leftColumn, entity);
            case RIGHT -> mapManager.onCollide(topRow, rightColumn, entity) && mapManager.onCollide(bottomRow, rightColumn, entity);
        };
    }

    /**
     * This method returns a random direction for the enemy to move based the valid directions.
     * @param enemy The enemy object.
     * @return The next direction for the enemy to move.
     */
    public Move nextDirection(Enemy enemy) {
        ArrayList<Move> moves = new ArrayList<>();
        for (Move move : Move.values()) {
            if (move(enemy, move)) {
                moves.add(move);
            }
        }

        return moves.get(ThreadLocalRandom.current().nextInt(moves.size()));
    }
}

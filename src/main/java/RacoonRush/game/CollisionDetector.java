package RacoonRush.game;

import RacoonRush.entity.Entity;
import RacoonRush.entity.enemy.Enemy;
import RacoonRush.map.MapManager;

import java.util.ArrayList;

public class CollisionDetector {
    private final GamePanel gamePanel;
    public CollisionDetector(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    // Interact with the blocks in the given direction
    // Returns true if the entity can move
    public boolean move(Entity entity, Move direction) {
        MapManager mapManager = gamePanel.getMapManager();
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

        return switch (direction) {
            case UP -> mapManager.onCollide(topRow, leftColumn) && mapManager.onCollide(topRow, rightColumn);
            case DOWN -> mapManager.onCollide(bottomRow, leftColumn) && mapManager.onCollide(bottomRow, rightColumn);
            case LEFT -> mapManager.onCollide(topRow, leftColumn) && mapManager.onCollide(bottomRow, leftColumn);
            case RIGHT -> mapManager.onCollide(topRow, rightColumn) && mapManager.onCollide(bottomRow, rightColumn);
        };
    }

    public Move nextDirection(Enemy enemy) {
        ArrayList<Move> moves = new ArrayList<>();
        for (Move move : Move.values()) {
            if (move(enemy, move)) {
                moves.add(move);
            }
        }

        return moves.get((int) (Math.random() * moves.size()));
    }
}

package RacoonRush.game;

import RacoonRush.entity.Player;
import RacoonRush.entity.enemy.Enemy;
import RacoonRush.map.MapManager;

import java.util.ArrayList;

public class CollisionDetector {
    private final GamePanel gamePanel;
    public CollisionDetector(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    // Interact with the blocks in the given direction
    // Returns true if the player can move
    public boolean move(Player player, Move direction) {
        MapManager mapManager = gamePanel.getMapManager();
        int speed = player.getSpeed();
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

    public boolean move(Enemy enemy, Move direction) {
        MapManager mapManager = gamePanel.getMapManager();
        int speed = enemy.getSpeed();
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
        int leftColumn = enemy.leftColumn(offsetX);
        int rightColumn = enemy.rightColumn(offsetX);
        int topRow = enemy.topRow(offsetY);
        int bottomRow = enemy.bottomRow(offsetY);

        return switch (direction) {
            case UP -> !mapManager.isWall(topRow, leftColumn) && !mapManager.isWall(topRow, rightColumn);
            case DOWN -> !mapManager.isWall(bottomRow, leftColumn) && !mapManager.isWall(bottomRow, rightColumn);
            case LEFT -> !mapManager.isWall(topRow, leftColumn) && !mapManager.isWall(bottomRow, leftColumn);
            case RIGHT -> !mapManager.isWall(topRow, rightColumn) && mapManager.isWall(bottomRow, rightColumn);
        };
    }

    public Move nextDirection(Enemy enemy) {
        Config config = gamePanel.getConfig();
        MapManager mapManager = gamePanel.getMapManager();
        ArrayList<Move> moves = new ArrayList<>();
        int row = enemy.getWorldY() / config.tileSize();
        int column = enemy.getWorldX() / config.tileSize();
        if (!mapManager.isWall(row - 1, column)) {
            moves.add(Move.UP);
        }
        if (!mapManager.isWall(row + 1, column)) {
            moves.add(Move.DOWN);
        }
        if (!mapManager.isWall(row, column - 1)) {
            moves.add(Move.LEFT);
        }
        if (!mapManager.isWall(row, column + 1)) {
            moves.add(Move.RIGHT);
        }
        return moves.get((int) (Math.random() * moves.size()));
    }
}

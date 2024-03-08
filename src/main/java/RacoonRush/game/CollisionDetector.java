package RacoonRush.game;

import RacoonRush.entity.Entity;

public class CollisionDetector {
    private final GamePanel gp;
    public CollisionDetector(GamePanel gp) {
        this.gp = gp;
    }

    public boolean checkCollision(Entity entity, Move direction) {
        int leftColumn = entity.leftColumn();
        int rightColumn = entity.rightColumn();
        int topRow = entity.topRow();
        int bottomRow = entity.bottomRow();

        switch (direction) {
            case UP:
                if (gp.hasCollision(topRow, leftColumn) || gp.hasCollision(topRow, rightColumn)) {
                    return true;
                }
                break;
            case DOWN:
                if (gp.hasCollision(bottomRow, leftColumn) || gp.hasCollision(bottomRow, rightColumn)) {
                    return true;
                }
                break;
            case LEFT:
                if (gp.hasCollision(topRow, leftColumn) || gp.hasCollision(bottomRow, leftColumn)) {
                    return true;
                }
                break;
            case RIGHT:
                if (gp.hasCollision(topRow, rightColumn) || gp.hasCollision(bottomRow, rightColumn)) {
                    return true;
                }
                break;
        }

        return false;
    }
}

package RaccoonRush.util;

import RaccoonRush.entity.Player;
import RaccoonRush.entity.enemy.Raccoon;
import RaccoonRush.game.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CollisionTest {
    private CollisionDetector collisionDetector;
    private Player player;

    @BeforeEach
    void setUp() {
        GamePanel gamePanel = new GamePanel();
        collisionDetector = gamePanel.getCollisionDetector();
        player = gamePanel.getEntityManager().getPlayer();
        gamePanel.getMapManager().loadMap("/maps/world_map.txt");
    }

    @Test
    void testMovePlayerUp() {
        assertTrue(collisionDetector.move(player, Move.UP));
    }

    @Test
    void testMovePlayerDown() {
        assertFalse(collisionDetector.move(player, Move.DOWN));
    }

    @Test
    void testMovePlayerLeft() {
        assertFalse(collisionDetector.move(player, Move.LEFT));
    }

    @Test
    void testMovePlayerRight() {
        assertFalse(collisionDetector.move(player, Move.RIGHT));
    }

    @Test
    void testNextDirection() {
        Raccoon enemy = new Raccoon(GamePanel.getInstance().getImageLoader().getEnemyRacoonImages(), 100, 100);
        assertNotNull(collisionDetector.nextDirection(enemy));
    }
}

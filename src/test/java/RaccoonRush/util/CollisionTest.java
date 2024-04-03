package RaccoonRush.util;

import RaccoonRush.entity.Player;
import RaccoonRush.entity.enemy.Raccoon;
import RaccoonRush.game.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CollisionTest {
    private GamePanel gamePanel;

    @BeforeEach
    void setUp() {
        gamePanel = new GamePanel();
        gamePanel.getMapManager().loadMap("/maps/world_map.txt");
    }

    @Test
    void testMovePlayerUp() {
        Player player = new Player(gamePanel, gamePanel.getImageLoader().getPlayerImages(), 0, 0, 0, Move.UP);
        gamePanel.getMapManager().loadMap("/maps/world_map.txt");
        assertFalse(gamePanel.getCollisionDetector().move(player, Move.UP));
    }

    @Test
    void testMovePlayerDown() {
        Player player = new Player(gamePanel, gamePanel.getImageLoader().getPlayerImages(), 0, 0, 0, Move.DOWN);
        gamePanel.getMapManager().loadMap("/maps/world_map.txt");
        assertFalse(gamePanel.getCollisionDetector().move(player, Move.DOWN));
    }

    @Test
    void testMovePlayerLeft() {
        Player player = new Player(gamePanel, gamePanel.getImageLoader().getPlayerImages(), 0, 0, 0, Move.LEFT);
        gamePanel.getMapManager().loadMap("/maps/world_map.txt");
        assertFalse(gamePanel.getCollisionDetector().move(player, Move.LEFT));
    }

    @Test
    void testMovePlayerRight() {
        Player player = new Player(gamePanel, gamePanel.getImageLoader().getPlayerImages(), 0, 0, 0, Move.RIGHT);
        gamePanel.getMapManager().loadMap("/maps/world_map.txt");
        assertFalse(gamePanel.getCollisionDetector().move(player, Move.RIGHT));
    }

    @Test
    void testNextDirection() {
        Raccoon enemy = new Raccoon(gamePanel, gamePanel.getImageLoader().getEnemyRacoonImages(), 100, 100, 0, Move.RIGHT, 0, 0, 0);
        boolean canMove = gamePanel.getCollisionDetector().nextDirection(enemy) != null;
        assertTrue(canMove);
    }
}

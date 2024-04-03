package RacoonRush.util;

import RacoonRush.entity.Player;
import RacoonRush.entity.enemy.Racoon;
import RacoonRush.game.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.shadow.com.univocity.parsers.conversions.Conversions.oneOf;

public class CollisionTest {
    private GamePanel fakeGamePanel;

    @BeforeEach
    void setUp() {
        fakeGamePanel = new GamePanel();
        fakeGamePanel.getMapManager().loadMap("/maps/world_map.txt");
    }

    @Test
    void testMovePlayerUp() {
        Player player = new Player(fakeGamePanel, fakeGamePanel.getImageLoader().getPlayerImages(), 0, 0, 0, Move.UP);
        fakeGamePanel.getMapManager().loadMap("/maps/world_map.txt");
        assertFalse(fakeGamePanel.getCollisionDetector().move(player, Move.UP));
    }

    @Test
    void testMovePlayerDown() {
        Player player = new Player(fakeGamePanel, fakeGamePanel.getImageLoader().getPlayerImages(), 0, 0, 0, Move.DOWN);
        fakeGamePanel.getMapManager().loadMap("/maps/world_map.txt");
        assertFalse(fakeGamePanel.getCollisionDetector().move(player, Move.DOWN));
    }

    @Test
    void testMovePlayerLeft() {
        Player player = new Player(fakeGamePanel, fakeGamePanel.getImageLoader().getPlayerImages(), 0, 0, 0, Move.LEFT);
        fakeGamePanel.getMapManager().loadMap("/maps/world_map.txt");
        assertFalse(fakeGamePanel.getCollisionDetector().move(player, Move.LEFT));
    }

    @Test
    void testMovePlayerRight() {
        Player player = new Player(fakeGamePanel, fakeGamePanel.getImageLoader().getPlayerImages(), 0, 0, 0, Move.RIGHT);
        fakeGamePanel.getMapManager().loadMap("/maps/world_map.txt");
        assertFalse(fakeGamePanel.getCollisionDetector().move(player, Move.RIGHT));
    }

    @Test
    void testNextDirection() {
        Racoon enemy = new Racoon(fakeGamePanel, fakeGamePanel.getImageLoader().getEnemyRacoonImages(), 100, 100, 0, Move.RIGHT, 0, 0, 0);
        boolean canMove = false;
        if (fakeGamePanel.getCollisionDetector().nextDirection(enemy) != null) {
            canMove = true;
        }
        assertTrue(canMove);
    }

}

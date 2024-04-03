package RaccoonRush.entity;

import RaccoonRush.game.GamePanel;
import RaccoonRush.entity.enemy.Enemy;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private GamePanel gamePanel;
    private Player player;
    private int initialScreenX, initialScreenY;

    @BeforeEach
    public void setup() {
        gamePanel = new GamePanel();
        player = gamePanel.getEntityManager().getPlayer();
        gamePanel.getMapManager().loadMap("/maps/emptyTestMap.txt");
        initialScreenX = player.getScreenX();
        initialScreenY = player.getScreenY();
    }

    @AfterEach
    public void screenPositionUnchanged() {
        assertEquals(initialScreenX, player.getScreenX());
        assertEquals(initialScreenY, player.getScreenY());
    }

    @Test
    public void playerMovesUpWhenUpKeyPressed() {
        KeyEvent keyEvent = new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.nanoTime(), 0, KeyEvent.VK_W, 'W');
        gamePanel.getKeyHandler().keyPressed(keyEvent);
        int initialY = player.getWorldY();
        player.update();
        assertEquals(initialY - player.getSpeed(), player.getWorldY());
    }

    @Test
    public void playerMovesDownWhenDownKeyPressed() {
        KeyEvent keyEvent = new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.nanoTime(), 0, KeyEvent.VK_S, 'S');
        gamePanel.getKeyHandler().keyPressed(keyEvent);
        int initialY = player.getWorldY();
        player.update();
        assertEquals(initialY + player.getSpeed(), player.getWorldY());
    }

    @Test
    public void playerMovesLeftWhenLeftKeyPressed() {
        KeyEvent keyEvent = new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.nanoTime(), 0, KeyEvent.VK_A, 'A');
        gamePanel.getKeyHandler().keyPressed(keyEvent);
        int initialX = player.getWorldX();
        player.update();
        assertEquals(initialX - player.getSpeed(), player.getWorldX());
    }

    @Test
    public void playerMovesRightWhenRightKeyPressed() {
        KeyEvent keyEvent = new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.nanoTime(), 0, KeyEvent.VK_D, 'D');
        gamePanel.getKeyHandler().keyPressed(keyEvent);
        int initialX = player.getWorldX();
        player.update();
        assertEquals(initialX + player.getSpeed(), player.getWorldX());
    }

    @Test
    public void playerDoesNotMoveWhenNoKeyIsPressed() {
        int initialX = player.getWorldX();
        int initialY = player.getWorldY();
        player.update();
        assertEquals(initialX, player.getWorldX());
        assertEquals(initialY, player.getWorldY());
    }

    @Test
    public void playerBecomesInvincibleAfterCollisionWithEnemy() {
        Enemy enemy = gamePanel.getEntityManager().getEnemyList().getFirst();
        player.onCollide(enemy);
        assertTrue(player.isInvincible());
    }
}

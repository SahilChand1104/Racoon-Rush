package RaccoonRush.entity;

import RaccoonRush.entity.enemy.Raccoon;
import RaccoonRush.game.GamePanel;
import RaccoonRush.entity.enemy.Enemy;

import RaccoonRush.util.Config;
import RaccoonRush.util.Move;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
public class EnemyTest {
    private GamePanel gamePanel;
    private ArrayList<Enemy> enemies;

    @BeforeEach
    public void setup() {
        gamePanel = new GamePanel();
        enemies = gamePanel.getEntityManager().getEnemyList();
        gamePanel.getMapManager().loadMap("/maps/emptyTestMap.txt");
    }

    @Test
    public void testGetDamage() {
        assertEquals(1, enemies.getFirst().getDamage());
    }

    @Test
    public void testDraw() {
        BufferedImage enemyImage = enemies.getFirst().getImage(0, Move.UP);
        Graphics2D g2 = enemyImage.createGraphics();
        enemies.getLast().draw(g2);
        gamePanel.getEntityManager().draw(g2);
        Config config = gamePanel.getConfig();
        assertEquals(enemyImage, enemies.getFirst().getImage(0, Move.UP));
        assertEquals(1056, enemies.getFirst().getWorldX());
        assertEquals(1056, enemies.getFirst().getWorldY());
        assertEquals(48, config.tileSize());
    }

    @Test
    public void testAbility() {
        enemies.getFirst().activateAbility();
        assertEquals(4, enemies.getFirst().getSpeed());
        enemies.getFirst().deactivateAbility();
        assertEquals(2, enemies.getFirst().getSpeed());
    }

    @Test
    public void testUpdate() {
        enemies.getFirst().update();
        int worldXBefore = enemies.getFirst().getWorldX();
        gamePanel.getEntityManager().update();
        int worldXAfter= enemies.getFirst().getWorldX();
        assertTrue(worldXBefore > worldXAfter);
    }
}

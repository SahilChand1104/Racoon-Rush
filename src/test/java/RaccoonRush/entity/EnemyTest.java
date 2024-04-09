package RaccoonRush.entity;

import RaccoonRush.game.GamePanel;
import RaccoonRush.entity.enemy.Enemy;
import RaccoonRush.util.Config;
import RaccoonRush.util.Move;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        Enemy enemy = enemies.getFirst();
        assertFalse(enemy.isAbilityActive());
        while (enemy.getAbilityCooldownFrames() > 0) {
            enemy.update();
        }
        assertTrue(enemy.isAbilityActive());
        while (enemy.getAbilityFrames() > 0) {
            enemy.update();
        }
        assertFalse(enemy.isAbilityActive());
    }

    @Test
    public void testUpdate_moveUp() {
        Enemy enemy = enemies.getFirst();
        int initialWorldY = enemy.getWorldY();
        enemy.setDirection(Move.UP);
        enemy.update();
        assertEquals(initialWorldY - enemy.getSpeed(), enemy.getWorldY());
    }

    @Test
    public void testUpdate_moveDown() {
        Enemy enemy = enemies.getFirst();
        int initialWorldY = enemy.getWorldY();
        enemy.setDirection(Move.DOWN);
        enemy.update();
        assertEquals(initialWorldY + enemy.getSpeed(), enemy.getWorldY());
    }

    @Test
    public void testUpdate_moveLeft() {
        Enemy enemy = enemies.getFirst();
        int initialWorldX = enemy.getWorldX();
        enemy.setDirection(Move.LEFT);
        enemy.update();
        assertEquals(initialWorldX - enemy.getSpeed(), enemy.getWorldX());
    }

    @Test
    public void testUpdate_moveRight() {
        Enemy enemy = enemies.getFirst();
        int initialWorldX = enemy.getWorldX();
        enemy.setDirection(Move.RIGHT);
        enemy.update();
        assertEquals(initialWorldX + enemy.getSpeed(), enemy.getWorldX());
    }
}

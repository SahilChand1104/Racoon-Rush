package RaccoonRush.map;

import RaccoonRush.game.GamePanel;
import RaccoonRush.map.tile.Tile;
import RaccoonRush.util.Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;


public class MapManagerTest {
    private GamePanel gamePanel;
    private MapManager mapManager;

    @BeforeEach
    public void setUp() {
        gamePanel = new GamePanel();
        mapManager = gamePanel.getMapManager();
        mapManager.loadMap("/maps/world_map.txt");

    }

    @Test
    public void testGetScreenCoordinate() {
            assertEquals(48, mapManager.getScreenCoordinate(1, 5, 5));
    }

    @Test
    public void testMapLoaded() {
        assertEquals(7, mapManager.getDonutsLeft());
    }

    @Test
    public void testOnCollide() {
        assertTrue(mapManager.onCollide(5, 5, gamePanel.getEntityManager().getPlayer()));
        assertFalse(mapManager.onCollide(-1, 5, gamePanel.getEntityManager().getPlayer()));
        assertFalse(mapManager.onCollide(5, 1000, gamePanel.getEntityManager().getPlayer()));
        assertTrue(mapManager.onCollide(27, 1, gamePanel.getEntityManager().getPlayer()));
        mapManager.onCollide(23, 12, gamePanel.getEntityManager().getPlayer());
        mapManager.onCollide(18, 24, gamePanel.getEntityManager().getPlayer());
        mapManager.onCollide(7, 16, gamePanel.getEntityManager().getPlayer());
        mapManager.onCollide(4, 13, gamePanel.getEntityManager().getPlayer());
        mapManager.onCollide(4, 1, gamePanel.getEntityManager().getPlayer());
        mapManager.onCollide(2, 30, gamePanel.getEntityManager().getPlayer());
        assertTrue(mapManager.onCollide(0, 29, gamePanel.getEntityManager().getPlayer()));
    }

    @Test
    public void testGetMap() {
        MapLoader loader = new MapLoader(gamePanel);
        ItemManager items = new ItemManager(gamePanel);
        Tile[][] tempMap = loader.loadMap(items,"/maps/world_map.txt");
        for (int i = 0; i < tempMap.length; i++) {
            for (int j = 0; j < tempMap[i].length; j++) {
                if (tempMap[i][j] != null) {
                    assertEquals(tempMap[i][j].getImage(0, 0, 0), mapManager.getMap()[i][j].getImage(0, 0, 0));
                }

            }
        }
    }

    @Test
    public void testDraw() {
        BufferedImage[] background = gamePanel.getImageLoader().getBackgroundImages().toArray(new BufferedImage[0]);
        Graphics2D g2 = background[0].createGraphics();
        mapManager.draw(g2);
        Config config = gamePanel.getConfig();
        assertEquals(config.screenWidth(), background[0].getWidth());
    }
}

package RaccoonRush.util;

import RaccoonRush.game.GamePanel;
import RaccoonRush.map.tile.Item;
import RaccoonRush.map.tile.TileType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreboardTest {
    private GamePanel gamePanel;

    @BeforeEach
    void setUp() {
        gamePanel = new GamePanel();
        gamePanel.getScoreboard().setVisible(true);
    }

    @Test
    void testUpdateScore() {
        Item item = new Item(gamePanel.getImageLoader().getDonutImages(), TileType.DONUT);
        gamePanel.updateScore(item);
        gamePanel.getScoreboard().updateScore();
        assertEquals("Score: 10", gamePanel.getScoreboard().getScoreLabelText());
    }

    @Test
    void testUpdateTimer() {
        gamePanel.getScoreboard().updateTimer("00:30");
        assertEquals("Time: 00:30", gamePanel.getScoreboard().getTimerLabelText());
    }

    @Test
    void testShowMessage() {
        gamePanel.getScoreboard().showMessage("THIS IS A MESSAGE");
        assertEquals("THIS IS A MESSAGE", gamePanel.getScoreboard().getMessageLabelText());
    }

    @Test
    void testCollectItemMessage() {
        Item item = new Item(gamePanel.getImageLoader().getDonutImages(), TileType.DONUT);
        gamePanel.getScoreboard().collectItemMessage(item, 3);
        assertEquals("+10 points! 3 donuts remaining.", gamePanel.getScoreboard().getMessageLabelText());
    }

    @Test
    void testOneDonutRemaining() {
        Item item = new Item(gamePanel.getImageLoader().getDonutImages(), TileType.DONUT);
        gamePanel.getScoreboard().collectItemMessage(item, 1);
        assertEquals("+10 points! 1 more donut left.", gamePanel.getScoreboard().getMessageLabelText());
    }

    @Test
    void testNoDonutRemaining() {
        Item item = new Item(gamePanel.getImageLoader().getDonutImages(), TileType.DONUT);
        gamePanel.getScoreboard().collectItemMessage(item, 0);
        assertEquals("+10 points! Hurry to the exit!", gamePanel.getScoreboard().getMessageLabelText());
    }

    @Test
    void testCollectLeftover() {
        Item item = new Item(gamePanel.getImageLoader().getLeftoverImages(), TileType.LEFTOVER);
        gamePanel.getScoreboard().collectItemMessage(item, 3);
        assertEquals("-20 points...", gamePanel.getScoreboard().getMessageLabelText());
    }

    @Test
    void testCollectPizza() {
        Item item = new Item(gamePanel.getImageLoader().getPizzaImages(), TileType.PIZZA);
        gamePanel.getScoreboard().collectItemMessage(item, 3);
        assertEquals("+50 points! You found Uncle Fatih's lost pizza!", gamePanel.getScoreboard().getMessageLabelText());
    }

    @Test
    public void testPaintComponent() {
        Scoreboard scoreboard = new Scoreboard(new GamePanel()); // assuming you have a GamePanel constructor
        BufferedImage image = new BufferedImage(200, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        scoreboard.paintComponent(g2d);

        assertNotNull(scoreboard);

        g2d.dispose();
    }
}

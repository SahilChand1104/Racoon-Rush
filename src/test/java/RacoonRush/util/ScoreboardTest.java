package RacoonRush.util;

import RacoonRush.game.GamePanel;
import RacoonRush.map.tile.Item;
import RacoonRush.map.tile.TileType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreboardTest {
    private GamePanel fakeGamePanel;

    @BeforeEach
    void setUp() {
        fakeGamePanel = new GamePanel();
        fakeGamePanel.getScoreboard().setVisible(true);
    }

    @Test
    void testUpdateScore() {
        Item item = new Item(fakeGamePanel.getImageLoader().getDonutImages(), TileType.DONUT);
        fakeGamePanel.updateScore(item);
        fakeGamePanel.getScoreboard().updateScore();
        assertEquals("Score: 10", fakeGamePanel.getScoreboard().getScoreLabelText());
    }

    @Test
    void testUpdateTimer() {
        fakeGamePanel.getScoreboard().updateTimer("00:30");
        assertEquals("Time: 00:30", fakeGamePanel.getScoreboard().getTimerLabelText());
    }

    @Test
    void testShowMessage() {
        fakeGamePanel.getScoreboard().showMessage("THIS IS A MESSAGE");
        assertEquals("THIS IS A MESSAGE", fakeGamePanel.getScoreboard().getMessageLabelText());
    }

    @Test
    void testCollectItemMessage() {
        Item item = new Item(fakeGamePanel.getImageLoader().getDonutImages(), TileType.DONUT);
        fakeGamePanel.getScoreboard().collectItemMessage(item, 3);
        assertEquals("+10 points! 3 donuts remaining.", fakeGamePanel.getScoreboard().getMessageLabelText());
    }

    @Test
    void testOneDonutRemaining() {
        Item item = new Item(fakeGamePanel.getImageLoader().getDonutImages(), TileType.DONUT);
        fakeGamePanel.getScoreboard().collectItemMessage(item, 1);
        assertEquals("+10 points! 1 more donut left.", fakeGamePanel.getScoreboard().getMessageLabelText());
    }

    @Test
    void testNoDonutRemaining() {
        Item item = new Item(fakeGamePanel.getImageLoader().getDonutImages(), TileType.DONUT);
        fakeGamePanel.getScoreboard().collectItemMessage(item, 0);
        assertEquals("+10 points! Hurry to the exit!", fakeGamePanel.getScoreboard().getMessageLabelText());
    }

    @Test
    void testCollectLeftover() {
        Item item = new Item(fakeGamePanel.getImageLoader().getLeftoverImages(), TileType.LEFTOVER);
        fakeGamePanel.getScoreboard().collectItemMessage(item, 3);
        assertEquals("-20 points...", fakeGamePanel.getScoreboard().getMessageLabelText());
    }

    @Test
    void testCollectPizza() {
        Item item = new Item(fakeGamePanel.getImageLoader().getPizzaImages(), TileType.PIZZA);
        fakeGamePanel.getScoreboard().collectItemMessage(item, 3);
        assertEquals("+50 points! You found Uncle Fatih's lost pizza!", fakeGamePanel.getScoreboard().getMessageLabelText());
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

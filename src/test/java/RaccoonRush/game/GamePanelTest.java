package RaccoonRush.game;

import static org.junit.jupiter.api.Assertions.*;

import RaccoonRush.map.tile.Item;
import RaccoonRush.map.tile.TileType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class GamePanelTest {
    private GamePanel gamePanel;

    @BeforeEach
    public void setup() {
        gamePanel = new GamePanel();
    }

    @Test
    public void startGame_startsNewThread() {
        gamePanel.startGame();
        assertNotNull(gamePanel.getGameThread());
    }

    @Test
    public void playGame_loadsMapStartsTimerAndSetsScoreToZero_whenGameStateIsMenu() {
        gamePanel.setGameState(GameState.MENU);
        gamePanel.playGame();
        assertNotNull(gamePanel.getMapManager().getMap());
        assertTrue(gamePanel.getGameTime().isRunning());
        assertEquals(0, gamePanel.getScore());
    }

    @Test
    public void playGame_resumesTimer_whenGameStateIsPause() {
        gamePanel.setGameState(GameState.PAUSE);
        gamePanel.playGame();
        assertTrue(gamePanel.getGameTime().isRunning());
    }

    @Test
    public void pauseGame_pausesTimerAndHidesScoreboard() {
        gamePanel.pauseGame();
        assertFalse(gamePanel.getGameTime().isRunning());
        assertFalse(gamePanel.getScoreboard().isVisible());
        assertEquals(GameState.PAUSE, gamePanel.getGameState());
    }

    @Test
    public void stopGame_stopsTimerHidesScoreboardAndShowsGameOverScreen() {
        gamePanel.stopGame(true);
        assertFalse(gamePanel.getGameTime().isRunning());
        assertFalse(gamePanel.getScoreboard().isVisible());
        assertEquals(GameState.MENU, gamePanel.getGameState());
    }

    @Test
    public void updateScore_increasesScoreBy10_whenItemIsDonut() {
        Item item = new Item(new ArrayList<>(), TileType.DONUT);
        gamePanel.updateScore(item);
        assertEquals(10, gamePanel.getScore());
    }

    @Test
    public void updateScore_decreasesScoreBy20_whenItemIsLeftover() {
        Item item = new Item(new ArrayList<>(), TileType.LEFTOVER);
        gamePanel.updateScore(item);
        assertEquals(-20, gamePanel.getScore());
    }

    @Test
    public void updateScore_increasesScoreBy50_whenItemIsPizza() {
        Item item = new Item(new ArrayList<>(), TileType.PIZZA);
        gamePanel.updateScore(item);
        assertEquals(50, gamePanel.getScore());
    }

    @Test
    public void updateScore_doesNotChangeScore_whenItemIsOther() {
        Item item = new Item(new ArrayList<>(), TileType.END);
        gamePanel.updateScore(item);
        assertEquals(0, gamePanel.getScore());
    }
}

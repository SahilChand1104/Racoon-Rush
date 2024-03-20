package RacoonRush.map;

import RacoonRush.game.GamePanel;
import RacoonRush.map.tile.Item;
import RacoonRush.map.tile.TileType;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class ItemManager {
    private final GamePanel gamePanel;
    private final ArrayList<Item> pizzaList;
    private int donutsLeft, currentPizza, pizzaFrames, nextPizzaFrames;

    public ItemManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        pizzaList = new ArrayList<>();
        donutsLeft = 0;
        currentPizza = 0;
        pizzaFrames = 0;
        nextPizzaFrames = gamePanel.getConfig().FPS() * 3;
    }
    
    public void update() {
        if (++pizzaFrames == nextPizzaFrames) {
            pizzaList.get(currentPizza).setCollected(true);
            currentPizza = ThreadLocalRandom.current().nextInt(pizzaList.size());
            pizzaList.get(currentPizza).setCollected(false);
            nextPizzaFrames = ThreadLocalRandom.current().nextInt(gamePanel.getConfig().FPS() / 2, gamePanel.getConfig().FPS() * 3);
            pizzaFrames = 0;
        }
    }

    public void collectItem(Item item) {
        if (item.getType() == TileType.END && donutsLeft == 0) {
            gamePanel.winGame();
        }
        gamePanel.updateScore(item);
        donutsLeft -= (item.getType() == TileType.DONUT) ? 1 : 0;
        gamePanel.getSoundManager().collectItemSound(item);
        gamePanel.getScoreboard().collectItemMessage(item, donutsLeft);
        item.setCollected(true);
    }

    public void addDonut() {
        donutsLeft++;
    }

    public void addPizza(Item pizza) {
        pizza.setCollected(true);
        pizzaList.add(pizza);
    }
}

package RaccoonRush.map;

import RaccoonRush.game.GamePanel;
import RaccoonRush.map.tile.Item;
import RaccoonRush.map.tile.TileType;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * ItemManager class for the game to manage the items
 * This class is intended to be exclusively used by the MapManager class
 */
public class ItemManager {
    private final GamePanel gamePanel;
    private final ArrayList<Item> pizzaList;
    private int donutsLeft, currentPizza, pizzaFrames, nextPizzaFrames;

    /**
     * @param gamePanel the GamePanel object
     */
    public ItemManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        pizzaList = new ArrayList<>();
        donutsLeft = 0;
        currentPizza = 0;
        pizzaFrames = 0;
        nextPizzaFrames = gamePanel.getConfig().FPS() * 3;
    }

    /**
     * Updates the item manager.
     */
    public void update() {
        if (++pizzaFrames == nextPizzaFrames) {
            pizzaList.get(currentPizza).setCollected(true);
            currentPizza = ThreadLocalRandom.current().nextInt(pizzaList.size());
            pizzaList.get(currentPizza).setCollected(false);
            nextPizzaFrames = ThreadLocalRandom.current().nextInt(gamePanel.getConfig().FPS() / 2, gamePanel.getConfig().FPS() * 3);
            pizzaFrames = 0;
        }
    }

    /**
     * Collects an item. Upon collecting an item, the score is updated, a sound effect is played, and a message is displayed.
     * @param item the item to collect
     */
    public void collectItem(Item item) {
        if (item.getType() == TileType.END && donutsLeft == 0) {
            gamePanel.stopGame(true);
        }
        gamePanel.updateScore(item);
        donutsLeft -= (item.getType() == TileType.DONUT) ? 1 : 0;
        gamePanel.getSoundManager().collectItemSound(item);
        gamePanel.getScoreboard().collectItemMessage(item, donutsLeft);
        item.setCollected(true);
    }

    /**
     * Increments the number of donuts left.
     */
    public void addDonut() {
        donutsLeft++;
    }

    /**
     * Adds a pizza to the pizza list.
     * @param pizza the pizza to add
     */
    public void addPizza(Item pizza) {
        pizza.setCollected(true);
        pizzaList.add(pizza);
    }

    /**
     * Gets the number of donuts left.
     * @return the number of donuts left
     */
    public int getDonutsLeft() {
        return donutsLeft;
    }
}

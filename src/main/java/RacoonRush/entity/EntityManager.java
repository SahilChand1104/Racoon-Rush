package RacoonRush.entity;

import RacoonRush.entity.enemy.Caffeine;
import RacoonRush.entity.enemy.EnemyType;
import RacoonRush.entity.enemy.Racoon;
import RacoonRush.game.Config;
import RacoonRush.game.GamePanel;
import RacoonRush.game.ImageLoader;
import RacoonRush.game.Move;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class EntityManager {
    private final GamePanel gamePanel;
    private final Player player;
    private final ArrayList<Entity> enemyList;

    public EntityManager (GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        player = createPlayer();
        enemyList = new ArrayList<>();
        createEnemyList();
    }

    private Player createPlayer() {
        Config config = gamePanel.getConfig();
        return new Player(
                gamePanel,
                config.tileSize() * (config.maxWorldCol() - 26),
                config.tileSize() * (config.maxWorldRow() - 2),
                4,
                Move.DOWN,
                gamePanel.getImageLoader().getPlayerImages());
    }

    private void createEnemyList() {
        Config config = gamePanel.getConfig();
        createEnemy(EnemyType.RACOON, config.maxWorldCol() - 28, config.maxWorldRow() - 2);
    }

    private void createEnemy(EnemyType type, int startX, int startY) {
        Config config = gamePanel.getConfig();
        ImageLoader imageLoader = gamePanel.getImageLoader();
        enemyList.add(switch (type) {
            case RACOON -> new Racoon(
                    gamePanel, startX * config.tileSize(), startY * config.tileSize(), 2, Move.LEFT, imageLoader.getEnemyRacoonImages(),
                    1, 30, 480
            );
            case CAFFEINE -> null;
        });
    }

    public void update() {
        player.update();
        for (Entity enemy : enemyList) {
            enemy.update();
        }
    }

    public void draw(Graphics2D g2) {
        ArrayList<Entity> entityList = new ArrayList<>(enemyList);
        entityList.add(player);
        entityList.sort(Comparator.comparingInt(Entity::getWorldY));
        for (Entity entity : entityList) {
            entity.draw(g2);
        }
    }

    public Player getPlayer() {
        return player;
    }
}

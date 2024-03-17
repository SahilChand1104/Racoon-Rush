package RacoonRush.entity.enemy;

import RacoonRush.game.*;

import java.awt.*;
import java.util.ArrayList;

public class EnemyManager implements Manager {
    private final GamePanel gamePanel;
    private final ArrayList<Enemy> enemies;
    public EnemyManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        enemies = new ArrayList<>();
        createEnemy(EnemyType.RACOON);
    }

    private void createEnemy(EnemyType type) {
        Config config = gamePanel.getConfig();
        ImageLoader imageLoader = gamePanel.getImageLoader();
        enemies.add(switch (type) {
            case RACOON -> new Racoon(gamePanel, (config.maxWorldCol() - 2) * config.tileSize(), (config.maxWorldRow() - 3) * config.tileSize(),
                    1, 1, 30, new Rectangle(0, 0, config.tileSize(), config.tileSize()), imageLoader.getEnemyRacoonImages());
            case CAFFEINE -> null;
            case LURKER -> null;
        });
    }

    public void update() {
        for (Enemy enemy : enemies) {
            enemy.update();
        }
    }

    public void draw(Graphics2D g2) {
        for (Enemy enemy : enemies) {
            enemy.draw(g2);
        }
    }
}

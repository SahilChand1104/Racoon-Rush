package RacoonRush.map.tile;

import RacoonRush.entity.Entity;
import RacoonRush.entity.Player;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Item extends Tile {
    private final int score;
    private boolean collected;

    public Item(ArrayList<BufferedImage> images, int score) {
        super(images);
        this.score = score;
        collected = false;
    }

    @Override
    public boolean onCollide(Entity entity) {
        if (entity instanceof Player player && !collected) {
            collected = true;
            player.updateScore(score);
            return true;
        }
        return false;
    }

    @Override
    public BufferedImage getImage(int x, int y, int animationFrame) {
        return collected ? null : images.get(animationFrame % images.size());
    }
}

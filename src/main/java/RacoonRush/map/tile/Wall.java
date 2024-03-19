package RacoonRush.map.tile;

import RacoonRush.entity.Entity;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Wall extends Tile {
    public Wall(ArrayList<BufferedImage> images) {
        super(images);
    }

    @Override
    public boolean onCollide(Entity entity) {
        return false;
    }

    @Override
    public BufferedImage getImage(int x, int y, int animationFrame) {
        return images.get((x * y + 3) % images.size());
    }
}

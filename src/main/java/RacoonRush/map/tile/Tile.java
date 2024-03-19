package RacoonRush.map.tile;

import RacoonRush.entity.Entity;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Tile {
    protected ArrayList<BufferedImage> images;

    public Tile(ArrayList<BufferedImage> images) {
        this.images = images;
    }

    public abstract BufferedImage getImage(int x, int y, int animationFrame);

    public boolean onCollide(Entity entity) {
        return true;
    }
}

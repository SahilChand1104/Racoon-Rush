package RacoonRush.map.tile;

import RacoonRush.entity.Player;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Tile {
    protected ArrayList<BufferedImage> images;

    public Tile(ArrayList<BufferedImage> images) {
        this.images = images;
    }

    public abstract BufferedImage getImage(int x, int y, int animationFrame);

    public boolean onCollide(Player player) {
        return true;
    }
}

package RacoonRush.game;

import RacoonRush.map.TileType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.EnumMap;

public class ImageLoader {
    private final GamePanel gamePanel;
    private final EnumMap<Move, BufferedImage> playerImage0;
    private final EnumMap<Move, BufferedImage> playerImage1;
    private final EnumMap<TileType, BufferedImage> tileImages;
    private final BufferedImage[] donutImages;

    private final BufferedImage[] background;
    //private BufferedImage background;

    private final BufferedImage[] wallImages;

    public ImageLoader(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        playerImage0 = new EnumMap<>(Move.class);
        playerImage1 = new EnumMap<>(Move.class);
        tileImages = new EnumMap<>(TileType.class);
        donutImages = new BufferedImage[12];
        background = new BufferedImage[4];
        wallImages = new BufferedImage[4];
        loadAllImages();
    }

    public BufferedImage resizeImage(BufferedImage image, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        resizedImage.getGraphics().drawImage(image, 0, 0, width, height, null);
        return resizedImage;
    }

    private BufferedImage loadImage(String path, boolean isTileSize) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(path));
            if (isTileSize) {
                Config config = gamePanel.getConfig();
                image = resizeImage(image, config.tileSize(), config.tileSize());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private void loadAllImages() {
        for (Move move : Move.values()) {
            playerImage0.put(move, loadImage("/entity/player/player_" + move.name().toLowerCase() + "_0.png", true));
            playerImage1.put(move, loadImage("/entity/player/player_" + move.name().toLowerCase() + "_1.png", true));
        }
        for (int i = 0; i < 12; i++) {
            donutImages[i] = loadImage("/entity/collectible/donut_" + (i+1) + ".png", true);
        }
        tileImages.put(TileType.EMPTY, loadImage("/tile/floor_v1.png", true));
        tileImages.put(TileType.WALL, loadImage("/tile/wall_v3_1.png", true));
        tileImages.put(TileType.DONUT, loadImage("/entity/collectible/donut_1.png", true));
        tileImages.put(TileType.TREE, loadImage("/tile/tree_v1.png", true));
        tileImages.put(TileType.LEFTOVER, loadImage("/entity/collectible/leftovers_v4.png", true));

        for (int i=0; i<4; i++) {
            background[i] = loadImage("/maps/map_bg_0"+(i+1)+".png", false);
        }

        for (int i=0; i<4; i++) {
            wallImages[i] = loadImage("/tile/wall_v3_"+(i+1)+".png", true);
        }

    }

    public BufferedImage getPlayerImage(Move move, int index) {
        return index == 0 ? playerImage0.get(move) : playerImage1.get(move);
    }

    public BufferedImage getTileImage(TileType type) {
        return tileImages.get(type);
    }

    public BufferedImage getBackground(int index) {
        return background[index];
    }

    public BufferedImage getDonutImage(int index) {
        return donutImages[index];
    }

    public BufferedImage getWallImage(int index) {
        return wallImages[index];
    }

    public BufferedImage getLeftoverImage() {
        return loadImage("/entity/collectible/leftovers_v5.png", true);
    }
}

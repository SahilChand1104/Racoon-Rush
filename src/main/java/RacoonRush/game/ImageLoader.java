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
    private BufferedImage background;

    public ImageLoader(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        playerImage0 = new EnumMap<>(Move.class);
        playerImage1 = new EnumMap<>(Move.class);
        tileImages = new EnumMap<>(TileType.class);
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
        tileImages.put(TileType.EMPTY, loadImage("/tile/floor_v1.png", true));
        tileImages.put(TileType.WALL, loadImage("/tile/wall_v1.png", true));
        background = loadImage("/maps/background_img.png", false);
    }

    public BufferedImage getPlayerImage(Move move, int index) {
        return index == 0 ? playerImage0.get(move) : playerImage1.get(move);
    }

    public BufferedImage getTileImage(TileType type) {
        return tileImages.get(type);
    }

    public BufferedImage getBackground() {
        return background;
    }
}

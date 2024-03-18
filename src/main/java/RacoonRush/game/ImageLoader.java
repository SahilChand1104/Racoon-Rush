package RacoonRush.game;

import RacoonRush.game.menu.ComponentType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;

public class ImageLoader {
    private final GamePanel gamePanel;
    private final EnumMap<Move, BufferedImage> playerImage0;
    private final EnumMap<Move, BufferedImage> playerImage1;
    private final ArrayList<BufferedImage> backgroundImages;
    private final ArrayList<BufferedImage> wallImages;

    private final EnumMap<ComponentType, BufferedImage> menuImages;
    private final EnumMap<ComponentType, BufferedImage> menuSelectedImages;
    private final ArrayList<BufferedImage> treeImages;
    private final ArrayList<BufferedImage> donutImages;
    private final ArrayList<BufferedImage> leftoverImages;
    private final ArrayList<BufferedImage> pizzaImages;

    public ImageLoader(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        playerImage0 = new EnumMap<>(Move.class);
        playerImage1 = new EnumMap<>(Move.class);
        backgroundImages = new ArrayList<>();
        wallImages = new ArrayList<>();
        treeImages = new ArrayList<>();
        donutImages = new ArrayList<>();
        leftoverImages = new ArrayList<>();
        pizzaImages = new ArrayList<>();

        menuImages = new EnumMap<>(ComponentType.class);
        menuSelectedImages = new EnumMap<>(ComponentType.class);
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

        for (int i = 0; i < 4; i++) {
            backgroundImages.add(loadImage("/maps/map_bg_0" + (i + 1) + ".png", false));
        }

        for (int i = 0; i < 4; i++) {
            wallImages.add(loadImage("/tile/wall_v3_" + (i + 1) + ".png", true));
        }

        treeImages.add(loadImage("/tile/tree_v4.png", true));

        for (int i = 0; i < 12; i++) {
            donutImages.add(loadImage("/entity/collectible/donut_" + (i + 1) + ".png", true));
        }

        leftoverImages.add(loadImage("/entity/collectible/leftovers_v4.png", true));

        pizzaImages.add(loadImage("/entity/collectible/golden_pizza_v1.png", true));

        menuImages.put(ComponentType.BG, loadImage("/menu/menu_bg.png", false));
        menuImages.put(ComponentType.BANNER, loadImage("/menu/menu_title_v2.png", false));
        menuImages.put(ComponentType.PLAY, loadImage("/menu/menu_label_play_0.png", false));
        menuImages.put(ComponentType.SETTINGS, loadImage("/menu/menu_label_settings_0.png", false));
        menuSelectedImages.put(ComponentType.PLAY, loadImage("/menu/menu_label_play_1.png", false));
        menuSelectedImages.put(ComponentType.SETTINGS, loadImage("/menu/menu_label_settings_1.png", false));
    }

    public BufferedImage getPlayerImage(Move move, int index) {
        return index == 0 ? playerImage0.get(move) : playerImage1.get(move);
    }

    public BufferedImage getBackground(int index) {
        return backgroundImages.get(index);
    }

    public ArrayList<BufferedImage> getWallImages() {
        return wallImages;
    }

    public ArrayList<BufferedImage> getTreeImages() {
        return treeImages;
    }

    public ArrayList<BufferedImage> getDonutImages() {
        return donutImages;
    }

    public ArrayList<BufferedImage> getLeftoverImages() {
        return leftoverImages;
    }

    public ArrayList<BufferedImage> getPizzaImages() {
        return pizzaImages;
    }

    public BufferedImage getMenuImage(ComponentType type) {
        return menuImages.get(type);
    }

    public BufferedImage getMenuSelectedImage(ComponentType type) {
        return menuSelectedImages.get(type);
    }
}

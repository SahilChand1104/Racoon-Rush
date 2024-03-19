package RacoonRush.game;

import RacoonRush.game.menu.ComponentType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;

/**
 * This class is responsible for loading all the images used in the game.
 * It loads the images from the resources folder and resizes them to the appropriate size.
 * It also stores the images in EnumMaps and ArrayLists for easy access.
 */
public class ImageLoader {
    private final GamePanel gamePanel;
    private final ArrayList<EnumMap<Move, BufferedImage>> playerImages;
    private final ArrayList<EnumMap<Move, BufferedImage>> enemyRacoonImages;
    private final ArrayList<BufferedImage> backgroundImages;
    private final ArrayList<BufferedImage> wallImages;

    private final EnumMap<ComponentType, BufferedImage> menuImages;
    private final EnumMap<ComponentType, BufferedImage> menuSelectedImages;
    private final ArrayList<BufferedImage> treeImages;
    private final ArrayList<BufferedImage> donutImages;
    private final ArrayList<BufferedImage> leftoverImages;
    private final ArrayList<BufferedImage> pizzaImages;

    /**
     * Constructor for the ImageLoader class.
     * @param gamePanel The GamePanel object that the images are being loaded for.
     */
    public ImageLoader(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        playerImages = new ArrayList<>();
        enemyRacoonImages = new ArrayList<>();
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

    /**
     * This method resizes the image to the specified width and height.
     * @param image The image to be resized.
     * @param width The width of the resized image.
     * @param height The height of the resized image.
     * @return The resized image.
     */
    public BufferedImage resizeImage(BufferedImage image, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        resizedImage.getGraphics().drawImage(image, 0, 0, width, height, null);
        return resizedImage;
    }

    /**
     * This method loads the image from the specified path and resizes it to the appropriate size.
     * @param path The path of the image to be loaded.
     * @param isTileSize A boolean value to indicate if the image should be resized to the tile size.
     * @return The loaded and resized image.
     */
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

    /**
     * This method loads all the images used in the game and stores them in EnumMaps and ArrayLists.
     */
    private void loadAllImages() {
        // Load player and racoon enemy images
        EnumMap<Move, BufferedImage> playerImages0 = new EnumMap<>(Move.class);
        EnumMap<Move, BufferedImage> playerImages1 = new EnumMap<>(Move.class);
        EnumMap<Move, BufferedImage> enemyRacoonImages0 = new EnumMap<>(Move.class);
        EnumMap<Move, BufferedImage> enemyRacoonImages1 = new EnumMap<>(Move.class);
        for (Move move : Move.values()) {
            playerImages0.put(move, loadImage("/entity/player/player_" + move.name().toLowerCase() + "_0.png", true));
            playerImages1.put(move, loadImage("/entity/player/player_" + move.name().toLowerCase() + "_1.png", true));
            enemyRacoonImages0.put(move, loadImage("/entity/enemy/RacoonEnemy/enemy_racoon_" + move.name().toLowerCase() + "_0.png", true));
            enemyRacoonImages1.put(move, loadImage("/entity/enemy/RacoonEnemy/enemy_racoon_" + move.name().toLowerCase() + "_1.png", true));
        }

        playerImages.add(playerImages0);
        playerImages.add(playerImages1);
        enemyRacoonImages.add(enemyRacoonImages0);
        enemyRacoonImages.add(enemyRacoonImages1);

        // Load background images
        for (int i = 0; i < 4; i++) {
            backgroundImages.add(loadImage("/maps/map_bg_0" + (i + 1) + ".png", false));
        }
        // Load wall images
        for (int i = 0; i < 4; i++) {
            wallImages.add(loadImage("/tile/wall_v3_" + (i + 1) + ".png", true));
        }
        // Load tree image
        treeImages.add(loadImage("/tile/tree_v4.png", true));
        // Load donut images for the animation
        for (int i = 0; i < 12; i++) {
            donutImages.add(loadImage("/entity/collectible/donut_" + (i + 1) + ".png", true));
        }
        // Load leftover image
        leftoverImages.add(loadImage("/entity/collectible/leftovers_v4.png", true));
        // Load pizza image
        pizzaImages.add(loadImage("/entity/collectible/golden_pizza_v1.png", true));

        // Load menu images and store them in EnumMaps corresponding to their type
        menuImages.put(ComponentType.BG, loadImage("/menu/menu_bg.png", false));
        menuImages.put(ComponentType.BANNER, loadImage("/menu/menu_title_v2.png", false));
        menuImages.put(ComponentType.PLAY, loadImage("/menu/menu_label_play_0.png", false));
        menuImages.put(ComponentType.SETTINGS, loadImage("/menu/menu_label_instructions_0.png", false));
        menuSelectedImages.put(ComponentType.PLAY, loadImage("/menu/menu_label_play_1.png", false));
        menuSelectedImages.put(ComponentType.SETTINGS, loadImage("/menu/menu_label_instructions_1.png", false));
    }

    /**
     *  This method returns all images of the player.
     * @return Array containing all images of the player.
     */
    public ArrayList<EnumMap<Move, BufferedImage>> getPlayerImages() {
        return playerImages;
    }

    /**
     *  This method returns all images of the racoon enemy.
     * @return Array containing all images of the racoon enemy.
     */
    public ArrayList<EnumMap<Move, BufferedImage>> getEnemyRacoonImages() {
        return enemyRacoonImages;
    }

    /**
     * This method returns the background image based on its position.
     * @param index The index of the background image (top left, top right, bottom left, bottom right).
     * @return The background image.
     */
    public BufferedImage getBackground(int index) {
        return backgroundImages.get(index);
    }

    /**
     * This method returns the wall images.
     * @return the list of wall images
     */
    public ArrayList<BufferedImage> getWallImages() {
        return wallImages;
    }

    /**
     * This method returns the tree images.
     * @return the list of tree images
     */
    public ArrayList<BufferedImage> getTreeImages() {
        return treeImages;
    }

    /**
     * This method returns the donut images.
     * @return the list of donut images
     */
    public ArrayList<BufferedImage> getDonutImages() {
        return donutImages;
    }

    /**
     * This method returns the leftover images.
     * @return the list of leftover images
     */
    public ArrayList<BufferedImage> getLeftoverImages() {
        return leftoverImages;
    }

    /**
     * This method returns the pizza images.
     * @return the list of pizza images
     */
    public ArrayList<BufferedImage> getPizzaImages() {
        return pizzaImages;
    }

    /**
     * This method returns the menu image based on its type.
     * @param type The type of the menu component.
     * @return The menu image.
     */
    public BufferedImage getMenuImage(ComponentType type) {
        return menuImages.get(type);
    }

    /**
     * This method returns the selected menu image based on its type.
     * @param type The type of the selected menu component.
     * @return The selected menu image.
     */
    public BufferedImage getMenuSelectedImage(ComponentType type) {
        return menuSelectedImages.get(type);
    }
}

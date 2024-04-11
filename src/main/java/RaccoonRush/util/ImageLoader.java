package RaccoonRush.util;

import RaccoonRush.game.GamePanel;
import RaccoonRush.game.menu.component.ComponentType;

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
     * This method loads the image from the specified path.
     * @param path The path of the image to be loaded.
     * @return The loaded and resized image.
     */
    private BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(getClass().getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method loads the image from the specified path and resizes it in the horizontal direction based on the provided width.
     * @param path The path of the image to be loaded.
     * @param width The width of the resized image.
     * @return The loaded and resized image.
     */
    private BufferedImage loadImage(String path, int width) {
        BufferedImage image = loadImage(path);
        image = resizeImage(image, width, image.getHeight());
        return image;
    }

    /**
     * This method loads the image from the specified path and resizes it to the appropriate size.
     * @param path The path of the image to be loaded.
     * @param width The width of the resized image.
     * @param height The height of the resized image.
     * @return The loaded and resized image.
     */
    private BufferedImage loadImage(String path, int width, int height) {
        BufferedImage image = loadImage(path);
        image = resizeImage(image, width, height);
        return image;
    }

    /**
     * This method loads the image from the specified path and resizes it as a tile.
     * @param path The path of the image to be loaded.
     * @return The loaded and resized image.
     */
    private BufferedImage loadImageTile(String path) {
        Config config = gamePanel.getConfig();
        return resizeImage(loadImage(path), config.tileSize(), config.tileSize());
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
            playerImages0.put(move, loadImageTile("/entity/player/player_" + move.name().toLowerCase() + "_0.png"));
            playerImages1.put(move, loadImageTile("/entity/player/player_" + move.name().toLowerCase() + "_1.png"));
            enemyRacoonImages0.put(move, loadImageTile("/entity/enemy/RacoonEnemy/enemy_racoon_" + move.name().toLowerCase() + "_0.png"));
            enemyRacoonImages1.put(move, loadImageTile("/entity/enemy/RacoonEnemy/enemy_racoon_" + move.name().toLowerCase() + "_1.png"));
        }

        playerImages.add(playerImages0);
        playerImages.add(playerImages1);
        enemyRacoonImages.add(enemyRacoonImages0);
        enemyRacoonImages.add(enemyRacoonImages1);

        // Load background images
        for (int i = 0; i < 4; i++) {
            backgroundImages.add(loadImage("/maps/map_bg_0" + (i + 1) + ".png"));
        }
        // Load wall images
        for (int i = 0; i < 4; i++) {
            wallImages.add(loadImageTile("/tile/wall_v3_" + (i + 1) + ".png"));
        }
        // Load tree image
        treeImages.add(loadImageTile("/tile/tree_v4.png"));
        // Load donut images for the animation
        for (int i = 0; i < 12; i++) {
            donutImages.add(loadImageTile("/item/donut_" + (i + 1) + ".png"));
        }
        // Load leftover image
        leftoverImages.add(loadImageTile("/item/leftovers_v4.png"));
        // Load pizza image
        pizzaImages.add(loadImageTile("/item/golden_pizza_v1.png"));

    }

    /**
     *  This method returns all images of the player.
     * @return ArrayList containing all images of the player.
     */
    public ArrayList<EnumMap<Move, BufferedImage>> getPlayerImages() {
        return playerImages;
    }

    /**
     *  This method returns all images of the racoon enemy.
     * @return ArrayList containing all images of the racoon enemy.
     */
    public ArrayList<EnumMap<Move, BufferedImage>> getEnemyRacoonImages() {
        return enemyRacoonImages;
    }

    /**
     * This method returns an array list containing the background images.
     * @return The array list containing the background images.
     */
    public ArrayList<BufferedImage> getBackgroundImages() {
        return backgroundImages;
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
}
